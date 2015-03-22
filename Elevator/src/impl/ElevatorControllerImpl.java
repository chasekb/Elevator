package impl;

import domain.ElevatorCallContext;
import exception.InvalidDataException;
import factory.ElevatorImplFactory;
import factory.TimableImplFactory;
import interfaces.*;

import java.lang.Long;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

/**
 * The ElevatorControllerImpl implements the data structures and methods of an elevator controller in the Elevator
 * system. ElevatorControllerImpl creates the elevators, implements a context switcher to determine the elevator to
 * which a request is assigned, and assigns requests to elevators. ElevatorControllerImpl generates rider experience
 * statistics upon determining the simulation has terminated.
 *
 * @author Bernard Chase
 * @see interfaces.Elevator
 * @see domain.ElevatorCallContext
 * @see impl.ElevatorCallImpl
 * @see impl.ElevatorCallAdvancedImpl
 */
public class ElevatorControllerImpl implements ElevatorController{

    private CountDownLatch cdl;
    private ElevatorCallContext ecCtx;
    private ArrayList<Elevator> elevatorList;
    private ArrayList<Person> finished;
    private ArrayList<Floor> floorList;
    private ArrayList<Request> requests;
    private ArrayList<Integer> pending;
    private Timable myTime;
    private boolean running = true;
    private long start;
    private long end;

    public ElevatorControllerImpl(long strt, long fin, int floors, int elevators, int maxP, long spd, long drOpTm,
                                  ArrayList flrLst, ArrayList peeps, ArrayList fnshd, ArrayList floorRequest,
                                  ArrayList riderRequest) throws InvalidDataException {
        start = strt;
        setStart();
        end = fin;
        cdl = new CountDownLatch(3);

        elevatorList = new ArrayList<>();
        finished = fnshd;
        floorList = flrLst;
        requests = new ArrayList<>();

        for (int e = 1; e <= elevators; e++) {
            Elevator myElevator = ElevatorImplFactory.create(cdl, end, floors, spd, drOpTm, e, getStart(), peeps, maxP,
                    floorList, finished, floorRequest, riderRequest);
            elevatorList.add(myElevator);
            new Thread(myElevator).start();
        }

        pending = new ArrayList<>();
        ecCtx = new ElevatorCallContext();
        chooseDelegate();

    }

    public void addFloorRequest(int floorIn, Movable.Direction dirIn) {
        int pick = ecCtx.createElCall(elevatorList, pending);
        synchronized (elevatorList) {
            for (Elevator e : elevatorList) {
                if (e.getId() == pick) {
                    e.setElevatorTasks(floorIn, dirIn);
                    //System.out.println(" floor request " + floorIn + " assigned to Elevator " + pick + ".");
                }
            }
        }
    }

    public void aveData() {
        long ave;
        int count;
        long temp;
        String buf = " | ";
        String form = "%2d";
        sortPeeps();

        System.out.println("\n\nAverage Ride Time from Floor to Floor by Person (in seconds)");
        System.out.print("Floor");
        for (Floor f : floorList) {
            System.out.print(buf);
            System.out.printf(form, f.getId());
        }

        for (Floor f : floorList) {
            System.out.println();
            System.out.print(buf);
            System.out.printf(form, f.getId());
            for (Floor g : floorList) {
                ave = 0;
                count = 0;
                if (f.getId() == g.getId()) {
                    System.out.print(buf);
                    System.out.print(" X");
                }
                else {
                    for (Person p : finished) {
                        if (p.getOrigin() == f.getId() && p.getDestination() == g.getId()) {
                            temp = p.getRideOut();
                            ave += temp;
                            count++;
                        }
                    }
                    if (ave == 0) {
                        System.out.print(buf);
                        System.out.print("--");
                    }
                    else {
                        System.out.print(buf);
                        System.out.printf(form, ave/count);
                    }
                }
            }
        }
        System.out.println();
    }

    private void aveMinMax() {
        System.out.println("\n\nAverage/Min/Max Wait Time by Floor (in seconds)");
        System.out.println("Floor     | Average Wait Time |   Min Wait Time   |   Max Wait Time");
        for (Floor f : floorList) {
            long ave = 0;
            long min = Long.MAX_VALUE;
            long max = 0;
            long sum = 0;
            int count = 0;

            for (Person p : finished) {
                if (p.getOrigin() == f.getId()) {
                    sum += p.getWaitOut();
                    count++;
                    if (p.getWaitOut() > max) {
                        max = p.getWaitOut();
                    }
                    if (p.getWaitOut() < min) {
                        min = p.getWaitOut();
                    }
                }
            }

            if (count == 0) {
                String format = "Floor %2d  |         --        |         --        |         --        |";
                System.out.printf(format, f.getId());
            }
            else {
                ave = sum / count;
                String format = "Floor %2d  |    %3d seconds    |    %3d seconds    |    %3d seconds    |";
                System.out.printf(format, f.getId(), ave, min, max);

            }

            System.out.println();
        }
    }

    private void calcTotal() {
        for (Person p : finished) {
            p.setWaitTotal();
            p.setRideTotal();
        }
    }

    public void chooseDelegate() {
        boolean flag = false;
        // choose delegate
        try {
            for (Elevator e : elevatorList) {
                if (e.getRiders().size() == 0) {
                    flag = false;
                } else if (e.getRiders().size() == e.getMaxPersons()) {
                    flag = false;
                } else {
                    flag = true;
                }
            }

            if (!flag) {
                ecCtx.setEcContext(new ElevatorCallImpl(elevatorList, pending));
            }
            else {
                ecCtx.setEcContext(new ElevatorCallAdvancedImpl(elevatorList, pending));
            }

        } catch (InvalidDataException ide) { ide.printStackTrace(); }
    }

    public void maxData() {
        long max;
        long temp;
        String buf = " | ";
        String form = "%2d";
        sortPeeps();

        System.out.println("\n\nMax Ride Time from Floor to Floor by Person (in seconds)");
        System.out.print("Floor");
        for (Floor f : floorList) {
            System.out.print(buf);
            System.out.printf(form, f.getId());
        }

        for (Floor f : floorList) {
            System.out.println();
            System.out.print(buf);
            System.out.printf(form, f.getId());
            for (Floor g : floorList) {
                max = 0;
                if (f.getId() == g.getId()) {
                    System.out.print(buf);
                    System.out.print(" X");
                }
                else {
                    for (Person p : finished) {
                        if (p.getOrigin() == f.getId() && p.getDestination() == g.getId()) {
                            temp = p.getRideOut();
                            if (temp > max) {
                                max = temp;
                            }
                        }
                    }
                    if (max == 0) {
                        System.out.print(buf);
                        System.out.print("--");
                    }
                    else {
                        System.out.print(buf);
                        System.out.printf(form, max);
                    }
                }
            }
        }
        System.out.println();
    }

    public void minData() {
        long min;
        long temp;
        String buf = " | ";
        String form = "%2d";
        sortPeeps();

        System.out.println("\n\nMin Ride Time from Floor to Floor by Person (in seconds)");
        System.out.print("Floor");
        for (Floor f : floorList) {
            System.out.print(buf);
            System.out.printf(form, f.getId());
        }

        for (Floor f : floorList) {
            System.out.println();
            System.out.print(buf);
            System.out.printf(form, f.getId());
            for (Floor g : floorList) {
                min = Long.MAX_VALUE;
                if (f.getId() == g.getId()) {
                    System.out.print(buf);
                    System.out.print(" X");
                }
                else {
                    for (Person p : finished) {
                        if (p.getOrigin() == f.getId() && p.getDestination() == g.getId()) {
                            temp = p.getRideOut();
                            if (temp < min) {
                                min = temp;
                            }
                        }
                    }
                    if (min == Long.MAX_VALUE) {
                        System.out.print(buf);
                        System.out.print("--");
                    }
                    else {
                        System.out.print(buf);
                        System.out.printf(form, min);
                    }
                }
            }
        }
        System.out.println();
    }

    public void personData() {
        sortPeeps();
        System.out.println("\n\nWait/Ride/Total Time by Person");
        System.out.println("Person       |   Start  |   Dest   |     Wait Time    |    Ride Time     |    Total Time");
        for (Person p : finished) {
            System.out.println(p.getData());
        }
    }

    public void run() {
        try {
            cdl.await();
            shutdown();
        } catch (InterruptedException ie) { ie.printStackTrace(); }
    }

    public void shutdown() {
        calcTotal();
        aveMinMax();
        aveData();
        maxData();
        minData();
        personData();
    }

    public void sortPeeps() {
        Collections.sort(finished);
    }

    public long getStart() {
        return myTime.getStart();
    }

    public void setStart() {
        try {
            myTime = TimableImplFactory.create(start);
        } catch (InvalidDataException ide) { ide.printStackTrace(); }
    }

}

package impl;

import factory.*;
import interfaces.*;
import exception.InvalidDataException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * The BuildingImpl class implements the data structures and methods of a building in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Building
 * @see factory.BuildingImplFactory
 * Created by kahlil on 2/7/15.
 */
public final class BuildingImpl implements Building {

    private int duration;
    private ArrayList<Elevator> elevatorsList;
    private long end;
    private Floor myFloor;
    private ArrayList<Floor> floorsList;
    private volatile ArrayList<Integer> floorRequest;
    private volatile ArrayList<Person> finished;
    private volatile ArrayList<Person> peeps;
    private volatile ArrayList<Integer> riderRequest;
    private ElevatorController myEC;
    private Timable myTimable;
    private int maxPersons;
    private int numElevators;
    private int numFloors;
    private int personPM;
    long start;

    public BuildingImpl(int floors, int elevators, int maxP, long spd, long drOpTm, int ppm, int drtn) throws
            InvalidDataException {
        duration = drtn;
        elevatorsList = new ArrayList<>();
        floorsList = new ArrayList<>();
        floorRequest = new ArrayList<>();
        riderRequest = new ArrayList<>();
        maxPersons = maxP;
        numElevators = elevators;
        numFloors = floors;
        personPM = ppm;
        peeps = new ArrayList<>();
        finished = new ArrayList<>();

        start = getNow();
        setStart();
        end = getEnd();

        System.out.println(timeFormat() + " Created Building. Building has " + getNumFloors() +
                " floors and " + getNumElevators() + " elevators.");
        for (int f = 1; f <= getNumFloors(); f++) {
            floorsList.add(FloorImplFactory.create(f));
        }

        myEC = ElevatorControllerImplFactory.create(getStart(), getEnd(), floors, elevators, maxP, spd, drOpTm, floorsList,
                peeps, finished, floorRequest, riderRequest);
        new Thread(myEC).start();

        int id = 1;
        long slp = TimeUnit.MINUTES.toNanos(1)/personPM;
        // while simulation has not expired

        while (getNow() < getEnd()) {
            // for n people this minute
            Person temp = PersonImplFactory.create(id++, getNow(), getNumFloors());
            for (Floor f : floorsList) {
                if (f.getId() == temp.getOrigin()) {
                    f.addVisitor(temp);
                }
            }
            // add person to start floor
            System.out.println(timeFormat() + " Person P"  + temp.getId() + " created on Floor " + temp.getLocation()
                    + ", wants to go " + temp.getDirection() + " to " + temp.getDestination());

            // press appropriate button
            System.out.println(timeFormat() + " Person P" + temp.getId() + " presses " + temp.getDirection()
                    + " button on Floor " + temp.getLocation());
            addFloorRequest(temp.getLocation(), temp.getDirection());
            temp.setWaitStart();
            try {
                Thread.sleep(TimeUnit.NANOSECONDS.toMillis(slp));
            } catch (InterruptedException ie) { ie.printStackTrace(); }
        }

        terminate();
    }

    public void addFloorRequest(int floorIn, Movable.Direction dirIn) { myEC.addFloorRequest(floorIn, dirIn); }

    private void terminate() {
        System.out.println("SHUTTING DOWN SIMULATION");
    }

    public long timeDelta() { return myTimable.timeDelta(); }

    public String timeFormat() { return myTimable.timeFormat(); }

    public long getNow() { return System.nanoTime(); }

    public long getEnd() { return TimeUnit.MINUTES.toNanos(duration) + getStart(); }

    public int getId() { return myFloor.getId(); }

    public int getNumElevators() { return numElevators; }

    public int getNumFloors() { return numFloors; }

    public long getStart() { return myTimable.getStart(); }

    public void setStart() {
        try {
            myTimable = TimableImplFactory.create(start);
        } catch (InvalidDataException ide) { ide.printStackTrace(); }
    }

}

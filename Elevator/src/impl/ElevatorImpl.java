package impl;

import exception.InvalidDataException;
import factory.*;
import interfaces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * The ElevatorImpl class implements the data structures and methods of an elevator object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Elevator
 * @see factory.ElevatorImplFactory
 * Created by kahlil on 2/7/15.
 */
public class ElevatorImpl implements Elevator {

    private CountDownLatch cdl;
    private int defaultFloor;
    private long doorOperationTime;
    private boolean doorsOpen;
    private long end;
    private Identifiable myIdentifiable;
    private Locatable myLocation;
    private Movable myMovable;
    private CopyOnWriteArrayList<Integer> elevatorTasks;
    private ArrayList<Floor> floorList;
    private ArrayList<Person> peeps;
    private ArrayList<Person> finished;
    private ArrayList<Integer> pending;
    private ArrayList<Integer> requestsFloor;
    private ArrayList<Integer> requestsRider;
    private CopyOnWriteArrayList<Person> riders;
    private int maxPersons;
    private Timable myTimable;
    private int numFloors;
    private boolean running = true;
    private long timeout;

    public ElevatorImpl(CountDownLatch c, long fin, int numFlrs, long spd, long drOpTm, int num, long strt,
                        ArrayList pps, int maxP, ArrayList flrLst, ArrayList fnshd, ArrayList rf, ArrayList rr)
            throws InvalidDataException {
        cdl = c;
        defaultFloor = 1;
        doorOperationTime = drOpTm;
        long speed = spd;
        elevatorTasks = new CopyOnWriteArrayList<>();
        end = fin;
        floorList = flrLst;
        maxPersons = maxP;
        numFloors = numFlrs;
        peeps = pps;
        finished = fnshd;
        requestsFloor = rf;
        requestsRider = rr;
        riders = new CopyOnWriteArrayList<>();
        timeout = 5000;

        myIdentifiable = IdentifiableImplFactory.create(num);
        myLocation = LocatableImplFactory.create(defaultFloor);
        myMovable = MovableImplFactory.create(speed);
        myTimable = TimableImplFactory.create(strt);
    }

    public void addFloorRequest(int floorIn, Direction dirIn) {
        // up/down button pressed
        System.out.println(timeFormat() + " New floor request: " + floorIn + "  Floor requests: "
                + requestsFloor);

        // is an elevator on this floor
        if (getLocation() == floorIn) {

            // is there an elevator on this floor idle or going in direction of request
            if (getDirection() == Direction.IDLE || getDirection() == dirIn) {
                // add floor/dir req to that elevator
                requestsFloor.add(floorIn);
                setDirection(dirIn);
                setElevatorTasks(floorIn, dirIn);
                sortReq();
                requestsFloor.notifyAll();
            }
        }

        // is an elevator in motion
        else if (getDirection() != Direction.IDLE) {
            // is the elevator going in desired direction
            if (checkDesiredDir(floorIn, dirIn)) {
                // add floor/dir req to that elevator
                setElevatorTasks(floorIn, dirIn);
                sortReq();
                requestsFloor.notifyAll();
            }
        }

        //is there an idle elevator
        else if (getDirection() == Direction.IDLE) {
            setElevatorTasks(floorIn, dirIn);
            sortReq();
            requestsFloor.notifyAll();
        }

        else {
            pending.add(floorIn);
        }
    }

    public void addRiderRequest(int floorTo) {
        // rider presses floor button
        synchronized (requestsRider) {
            System.out.println(timeFormat() + " New rider request: " + floorTo + "  Rider requests: "
                    + requestsRider);

            // is requested floor the current floor
            if (floorTo == getLocation()) {
                System.out.println(" Requested floor is the same as the current floor");
            }

            // is requested floor already a destination
            else {
                if (!elevatorTasks.contains(floorTo) && !requestsRider.contains(floorTo)) {
                    if (getDirection() == Direction.IDLE) {

                        // set direction toward requested floor
                        setDirection(reqDir(getLocation(), floorTo));
                    }

                    // add requested floor to pending requests
                    setElevatorTasks(floorTo, reqDir(getLocation(), floorTo));
                    sortReq();
                    requestsRider.notifyAll();
                }
                else {
                    System.out.println(" Requested floor already a destination");
                }
            }
        }
    }

    public void anyFloorReqs() {
        synchronized (elevatorTasks) {
            for (int e : elevatorTasks) {
                if (e == getLocation()) {
                    if (!doorsOpen) {
                        System.out.println(timeFormat() + " Elevator " + getId() + " has arrived at Floor "
                                + getLocation() + " for Floor Request Requests: " + getElevatorTasks());

                        // open doors
                        if (!doorsOpen) {
                            openDoors();
                        }
                    }

                    // move requesters from floor to elevator
                    moveReqToEl();
                    if (elevatorTasks.size() == 1 && riders.size() == 0) {
                        int i = elevatorTasks.indexOf(e);
                        elevatorTasks.remove(i);
                    }
                } else {
                    // are doors open
                    if (doorsOpen()) {

                        // close doors
                        closeDoors();
                    }
                }
            }

            for (int e : elevatorTasks) {
                if (e == getLocation()) {
                    int i = elevatorTasks.indexOf(e);
                    elevatorTasks.remove(i);
                }
            }
        }
    }

    public void anyRiderReqs() {
        synchronized (elevatorTasks) {
            for (int e : elevatorTasks) {
                if (e == getLocation()) {
                    if (!riders.isEmpty()) {
                        System.out.println(timeFormat() + " Elevator " + getId() + " has arrived at Floor "
                                + getLocation() + " for Rider Request Requests: " + getElevatorTasks());

                        // open doors
                        if (!doorsOpen) {
                            openDoors();
                        }

                        // move riders requesting this floor from elevator to floor
                        moveReqToFloor();
                    }
                } else {
                    // are doors open
                    if (doorsOpen()) {

                        // close doors
                        closeDoors();
                    }
                }
            }
        }
    }

    public boolean checkDesiredDir(int floorIn, Direction dirIn) {
        if (getDirection() == dirIn) {
            // is elevator going to a rider floor request
            if (requestsRider.contains(elevatorTasks.get(0))) {
                // is elevator moving toward requesting floor
                if (getDirection() == Direction.UP) {
                    if (getLocation() < floorIn) {
                        return true;
                    }
                }
                else if (getDirection() == Direction.DOWN) {
                    if (getLocation() > floorIn) {
                        return true;
                    }
                }
                else {
                    return false;
                }
            }

            // elevator is going to a previous floor req
            else {
                // is elevator moving toward requested floor
                if (getDirection() == Direction.UP) {
                    if (getLocation() < floorIn) {
                        return true;
                    }
                }
                else if (getDirection() == Direction.DOWN) {
                    if (getLocation() > floorIn) {
                        return true;
                    }
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Closes the doors of an Elevator object.
     */
    public void closeDoors() {
        if (doorsOpen) {
            System.out.println(timeFormat() + " Elevator " + getId() + " Doors Close");
            doorsOpen = false;
            if (elevatorTasks.isEmpty()) {
                setDirection(Direction.IDLE);
            }
        }
    }

    public boolean doorsOpen() { return doorsOpen; }

    private void finishPerson(Person fnsh) {
        ArrayList<Integer> out = new ArrayList<>();
        synchronized (finished) {
            finished.add(fnsh);
        }

        for (Floor f : floorList) {
            if (f.getId() == getLocation()) {
                for (Person p : riders) {
                    if (p.getDestination() == getLocation()) {
                        f.addVisitor(p);
                        CopyOnWriteArrayList<Person> local = f.getVisitors();
                        for (Person q : local) {
                            out.add(q.getId());
                        }
                        System.out.println(timeFormat() + " Person P" + fnsh.getId() + " entered Floor " + getLocation()
                                + " People: " + out);

                    }
                }
            }
        }
    }

   /**
     *
     * @param loc
     * @return
     */
    public int moveDown(int loc) {
        if (doorsOpen) {
            closeDoors();
        }

        setDirection(Direction.DOWN);
        setLocation(loc - 1);
        System.out.println(timeFormat() + " Elevator " + getId() + " moving from Floor " + loc + " to Floor "
                + (loc - 1) + "." + " Requests: " + getElevatorTasks());

        return myMovable.moveDown(loc);
    }

    private void moveReqToEl() {
        for (Floor f : floorList) {
            if (f.getId() == getLocation()) {
                CopyOnWriteArrayList<Person> poss;
                poss = f.getVisitors();
                synchronized (poss) {
                    for (Person p : poss) {
                        if (p.getOrigin() == getLocation()) {
                            p.setWaitEnd();
                            synchronized (riders) {
                                riders.add(p);
                            }
                            f.removeVisitor(p);
                            System.out.println(timeFormat() + " Person P" + p.getId() + " entered Elevator "
                                    + getId() + " Riders: " + getRiders());
                            synchronized (elevatorTasks) {
                                if (!elevatorTasks.isEmpty()) {
                                    for (int e : elevatorTasks) {
                                        if (e == getLocation()) {
                                            int i = elevatorTasks.indexOf(e);
                                            elevatorTasks.remove(i);
                                        }
                                    }
                                } else {
                                    setDirection(Direction.IDLE);
                                }
                            }
                            synchronized (requestsRider) {
                                if (!requestsRider.isEmpty()) {
                                    requestsRider.remove(0);
                                } else {
                                    setDirection(Direction.IDLE);
                                }
                            }

                            // add requesters floor number request
                            setElevatorTasks(p.getDestination(), reqDir(getLocation(), p.getDestination()));
                            p.setRideStart();
                            System.out.println(timeFormat() + " Elevator " + getId() + " Rider Request made for Floor "
                                    + p.getDestination() + " Requests: " + getElevatorTasks());

                        }
                    }
                }
            }
        }

        sortReq();
    }

    private void moveReqToFloor() {
        for (Person p : riders) {
            if (p.getDestination() == getLocation()) {
                p.setRideEnd();
                finishPerson(p);
                synchronized (riders) {
                    riders.remove(p);
                }
                System.out.println(timeFormat() + " Person P" + p.getId() + " has left Elevator "
                        + getId() + " Riders: " + getRiders());
                synchronized (elevatorTasks) {
                    if (!elevatorTasks.isEmpty()) {
                        for (int e : elevatorTasks) {
                            if (e == getLocation()) {
                                int i = elevatorTasks.indexOf(e);
                                elevatorTasks.remove(i);
                            }
                        }
                    }
                }
                synchronized (requestsRider) {
                    if (!requestsRider.isEmpty()) {
                        requestsRider.remove(0);
                    }
                }
            }
        }
    }

    /**
     *
     * @param loc
     * @return
     */
    public int moveUp(int loc) {
        if (doorsOpen) {
            closeDoors();
        }
        setDirection(Direction.UP);
        setLocation(getLocation() + 1);
        System.out.println(timeFormat() + " Elevator " + getId() + " moving from Floor " + loc + " to Floor "
                + (loc + 1) + "." + " Requests: " + getElevatorTasks());

        return myMovable.moveUp(loc);
    }

    /**
     *
     */
    public void openDoors() {
        if (!doorsOpen) {
            try {
                synchronized (this) {
                    this.wait(getSpeed());
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            System.out.println(timeFormat() + " Elevator " + getId() + " Doors Open");
            doorsOpen = true;
        }
    }

    /**
     *
     */
    public void processRequest() {
        // any pending elevator requests
        getPendingReqFloor();
        getPendingReqRider();
    }

    public Direction reqDir(int curr, int req) {
        return myMovable.reqDir(curr, req);
    }

    /**
     *
     */
    public void run() {
        while(running) {
            // are there any floor requests or rider requests
            // if yes
            while(!elevatorTasks.isEmpty()) {

                // is the elevator idle
                if (getDirection() == Direction.IDLE) {

                    // set elevator's direction to direction of closest request
                    setDirection(reqDir(getLocation(), elevatorTasks.get(0)));
                }

                // is the elevator moving up
                if (getDirection() == Direction.UP) {

                    // move elevator up
                    if (getLocation() != numFloors) {
                        moveUp(getLocation());
                    }
                }

                else if (getDirection() == Direction.DOWN) {
                    // move elevator down
                    if (getLocation() != 1) {
                        moveDown(getLocation());
                    }
                }

                // are there any rider requests for the current floor
                anyRiderReqs();

                // are there any floor requests for the current floor
                anyFloorReqs();

                synchronized (requestsFloor) {
                    if (!requestsFloor.isEmpty()) {
                        processRequest();
                    }
                }

                synchronized (requestsRider) {
                    if (!requestsRider.isEmpty()) {
                        processRequest();
                    }
                }

                // check direction
                if (!elevatorTasks.isEmpty()) {
                    if (elevatorTasks.get(0) > getLocation()) {
                        setDirection(Direction.UP);
                    }

                    else if (elevatorTasks.get(0) < getLocation()) {
                        setDirection(Direction.DOWN);
                    }
                }

                else if (elevatorTasks.isEmpty()) {
                    setDirection(Direction.IDLE);
                }

            }

            // if no
            shutdown();
            waitForSomething();
        }
    }

    public void shutdown() {
        if (System.nanoTime() >= end) {
            if (getLocation() == defaultFloor && elevatorTasks.isEmpty() && riders.isEmpty()) {
                running = false;
                cdl.countDown();
                System.out.println(timeFormat() + " Elevator " + getId() + " shutting down.");
            }
        }
    }

    public void sortReq() {
        synchronized (elevatorTasks) {
            if (!elevatorTasks.isEmpty()) {
                Collections.sort(elevatorTasks);
                if (getDirection() == Direction.DOWN) {
                    Collections.reverse(elevatorTasks);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public long timeDelta() { return myTimable.timeDelta(); }

    /**
     *
     * @return
     */
    public String timeFormat() { return myTimable.timeFormat(); }

    public void waitForSomething() {
        try {
            if (elevatorTasks.isEmpty()) {
                setDirection(Direction.IDLE);
            }
            synchronized (requestsFloor) {
                if (requestsFloor.isEmpty()) {
                    requestsFloor.wait(timeout);
                }
                if (getLocation() != defaultFloor) {
                    setElevatorTasks(defaultFloor, reqDir(getLocation(), defaultFloor));
                }
            }

            processRequest();

        } catch (InterruptedException ie) { ie.printStackTrace(); }
    }

    /**
     * Gets the default floor of an Elevator object.
     * @return the default floor of an Elevator object.
     */
    public int getDefaultFloor() { return defaultFloor; }

    /**
     * Gets the destination of an Elevator object.
     * @return the destination of an Elevator object.
     */
    public int getDestination() { return myMovable.getDestination(); }

    /**
     * Gets the direction of an Elevator object.
     * @return the direction of an Elevator object.
     */
    public Direction getDirection() {
        return myMovable.getDirection();
    }

    /**
     *
     * @return
     */
    public long getDoorOperationTime() { return doorOperationTime; }


    public CopyOnWriteArrayList getElevatorTasks() { return elevatorTasks; }

    /**
     *
     * @return
     */
    public int getId() {
        return myIdentifiable.getId();
    }

    /**
     *
     * @return
     */
    public int getLocation() { return myLocation.getLocation(); }

    public int getMaxPersons() { return maxPersons; }

    public void getPendingReqFloor() {
        //System.out.println(" getPendingReqFloor");
        boolean loop = true;

        while (loop) {
            synchronized (requestsFloor) {
                // any pending elevator requests?
                if (!requestsFloor.isEmpty()) {

                    // select the first request and add to pending requests
                    setElevatorTasks(requestsFloor.get(0), reqDir(getLocation(), requestsFloor.remove(0)));
                    sortReq();
                    requestsFloor.notifyAll();

                    // any remaining elevator requests
                    if (!requestsFloor.isEmpty()) {

                        // is next request in same direction as initial request
                        if (reqDir(getLocation(), elevatorTasks.get(0)) == reqDir(getLocation(), requestsFloor.get(0))) {

                            // is initial request direction UP
                            if (reqDir(getLocation(), elevatorTasks.get(0)) == Direction.UP) {

                                // is direction from initial request to next request UP
                                if (reqDir(elevatorTasks.get(0), requestsFloor.get(0)) == Direction.UP) {

                                    // add next request to pending requests
                                    setElevatorTasks(requestsFloor.get(0), reqDir(getLocation(), requestsFloor.remove(0)));
                                    sortReq();
                                }
                            }

                            // is initial request direction DOWN
                            else if (reqDir(getLocation(), elevatorTasks.get(0)) == Direction.DOWN) {

                                // is direction from initial request to next request DOWN
                                if (reqDir(elevatorTasks.get(0), requestsFloor.get(0)) == Direction.DOWN) {

                                    // add next request to pending requests
                                    setElevatorTasks(requestsFloor.get(0), reqDir(getLocation(), requestsFloor.remove(0)));
                                    sortReq();
                                }
                            }
                        }
                    }
                    sortReq();
                }

                else {
                    loop = false;
                    moveReqToEl();
                    continue;
                }
            }
        }
    }

    public void getPendingReqRider() {
        //System.out.println(" getPendingReqRider");
        boolean loop = true;

        while (loop) {
            synchronized (requestsRider) {
                // any pending elevator requests?
                if (!requestsRider.isEmpty()) {

                    // select the first request and add to pending requests
                    setElevatorTasks(requestsFloor.get(0), reqDir(getLocation(), requestsFloor.remove(0)));
                    sortReq();
                    requestsRider.notifyAll();

                    // any remaining elevator requests
                    if (!requestsRider.isEmpty()) {

                        // is next request in same direction as initial request
                        if (reqDir(getLocation(), elevatorTasks.get(0)) == reqDir(getLocation(), requestsRider.get(0))) {

                            // is initial request direction UP
                            if (reqDir(getLocation(), elevatorTasks.get(0)) == Direction.UP) {

                                // is direction from initial request to next request UP
                                if (reqDir(elevatorTasks.get(0), requestsRider.get(0)) == Direction.UP) {

                                    // add next request to pending requests
                                    setElevatorTasks(requestsFloor.get(0), reqDir(getLocation(), requestsFloor.remove(0)));
                                    sortReq();
                                }
                            }

                            // is initial request direction DOWN
                            else if (reqDir(getLocation(), elevatorTasks.get(0)) == Direction.DOWN) {

                                // is direction from initial request to next request DOWN
                                if (reqDir(elevatorTasks.get(0), requestsRider.get(0)) == Direction.DOWN) {

                                    // add next request to pending requests
                                    setElevatorTasks(requestsFloor.get(0), reqDir(getLocation(), requestsFloor.remove(0)));
                                    sortReq();
                                }
                            }
                        }
                    }
                    sortReq();
                }
                else {
                    loop = false;
                    continue;
                }
            }
        }
    }

    public ArrayList getRiders() {
        ArrayList<Integer> gr = new ArrayList();
        synchronized (riders) {
            Iterator<Person> ir = riders.iterator();
            if (ir.hasNext()) {
                Person p = ir.next();
                gr.add(p.getId());
            }
        }
        return gr;
    }

    /**
     *
     * @return
     */
    public boolean getRunning() { return running; }

    /**
     *
     * @return
     */
    public long getSpeed() { return myMovable.getSpeed(); }

    /**
     *
     * @return
     */
    public long getStart() { return myTimable.getStart(); }

    /**
     *
     * @param destIn
     */
    public void setDestination(int destIn) {
        myMovable.setDestination(destIn);
    }

    /**
     *
     * @param directionIn
     */
    public void setDirection(Direction directionIn) {
        myMovable.setDirection(directionIn);
    }

    public void setElevatorTasks(int floorIn, Direction dirIn) {
        synchronized (elevatorTasks) {
            elevatorTasks.add(floorIn);
        }
        System.out.println(timeFormat() + " Elevator " + getId() + " going to Floor " + floorIn
                + " for " + dirIn + " request " + getElevatorTasks());
    }

    /**
     *
     * @param at
     */
    public void setLocation(int at) {
        myLocation.setLocation(at);
    }

    /**
     *
     * @param spd
     */
    public void setSpeed(long spd) { myMovable.setSpeed(spd); }
}

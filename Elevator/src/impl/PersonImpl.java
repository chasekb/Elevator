package impl;

import exception.InvalidDataException;
import factory.IdentifiableImplFactory;
import factory.LocatableImplFactory;
import factory.TimableImplFactory;
import interfaces.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * The PersonImpl class implements the data structures and methods required for a Person object in the Elevator
 * system.
 *
 * @author Bernard Chase
 */
public class PersonImpl implements Person {
    private int destination;
    private Movable.Direction direction;
    private Identifiable myId;
    private Locatable myLoc;
    private Timable myTime;
    private int origin;
    private int maxFloor;
    private long rideEnd;
    private long rideStart;
    private long rideTotal;
    private long totalTime;
    private long waitEnd;
    private long waitStart;
    private long waitTotal;

    public PersonImpl(int id, long startIn, int maxFlr) throws InvalidDataException {
        maxFloor = maxFlr;

        myId = IdentifiableImplFactory.create(id++);
        myTime = TimableImplFactory.create(startIn);
        myLoc = LocatableImplFactory.create(getOrigin());

        setOrigin();
        setLocation(getOrigin());
        setDestination();
        checkOrgDest();
        setDirection();
    }

    public void checkOrgDest() {
        if (getOrigin() == getDestination()) {
            while (getOrigin() == getDestination()) {
                setDestination();
            }
        }
    }

    public long timeDelta() {
        return myTime.timeDelta();
    }

    public String timeFormat() {
        return myTime.timeFormat();
    }

    @Override
    public int compareTo(Object o) {
        Person p = (Person) o;
        return (this.getId() < p.getId()) ? -1: (this.getId() > p.getId()) ? 1:0;
    }

    public String getData() {
        String format = "Person %2d    |    %2d    |    %2d    |    %2d seconds    |    %2d seconds    " +
                "|    %2d seconds";
        return String.format(format, getId(), getOrigin(), getDestination(), getWaitOut(), getRideOut(), getTotalOut());
    }

    public int getDestination() { return destination; }

    public Movable.Direction getDirection() {
        return direction;
    }

    public int getId() {
        return myId.getId();
    }

    public int getLocation() {
        return myLoc.getLocation();
    }

    public int getOrigin() { return origin; }

    public long getRideEnd() { return rideEnd;}

    public long getRideOut() {
        long ride = getRideTotal();
        long seconds = TimeUnit.NANOSECONDS.toSeconds(ride);
        return seconds;
    }

    public long getRideStart() { return rideStart; }

    public long getRideTotal() { return rideTotal; }

    public long getStart() {
        return myTime.getStart();
    }

    public long getTotalOut() {
        long tot = getWaitTotal() + getRideTotal();
        long seconds = TimeUnit.NANOSECONDS.toSeconds(tot);
        return seconds;
    }

    public long getTotalTime() { return getRideTotal() + getWaitTotal(); }

    public long getWaitEnd() { return waitEnd; }

    public long getWaitOut() {
        long wait = getWaitTotal();
        long seconds = TimeUnit.NANOSECONDS.toSeconds(wait);
        return seconds;
    }

    public long getWaitStart() { return waitStart; }

    public long getWaitTotal() { return waitTotal; }

    public void setDestination() {
        Random randd = new Random();
        destination = randd.nextInt((maxFloor - 1) + 1) + 1;
    }

    public void setDirection() {
        if (getLocation() > getDestination()) {
            direction = Movable.Direction.DOWN;
        }
        else if (getLocation() < getDestination()) {
            direction = Movable.Direction.UP;
        }
    }

    public void setLocation(int loc) {
        myLoc.setLocation(loc);
    }

    public void setOrigin() {
        Random rando = new Random();
        origin = rando.nextInt((maxFloor - 1) + 1) + 1;
    }

    public void setRideEnd() {
        rideEnd = System.nanoTime();
    }

    public void setRideStart() {
        rideStart = System.nanoTime();
    }

    public void setRideTotal() {
        rideTotal = getRideEnd() - getRideStart();
    }

    public void setWaitEnd() {
        waitEnd = System.nanoTime();
    }

    public void setWaitStart() {
        waitStart = System.nanoTime();
    }

    public void setWaitTotal() {
        waitTotal = getWaitEnd() - getWaitStart();
    }
}

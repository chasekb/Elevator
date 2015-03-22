package interfaces;

/**
 * The Person interface contains the methods required for a person in the Elevator system.
 *
 * @author Bernard Chase
 */
public interface Person extends Comparable, Identifiable, Locatable, Timable {
    long timeDelta();
    String timeFormat();
    int compareTo(Object o);
    int getId();
    String getData();
    int getDestination();
    Movable.Direction getDirection();
    int getLocation();
    int getOrigin();
    long getRideOut();
    long getRideTotal();
    long getTotalTime();
    long getWaitOut();
    long getStart();
    long getTotalOut();
    void setDestination();
    void setDirection();
    void setLocation(int loc);
    void setOrigin();
    void setRideEnd();
    void setRideStart();
    void setRideTotal();
    void setWaitEnd();
    void setWaitStart();
    void setWaitTotal();
}

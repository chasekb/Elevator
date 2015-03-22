package interfaces;

/**
 * The Timable interface has all of the data structures and methods to time objects in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Elevator
 * @see interfaces.Building
 * @see interfaces.Person
 */
public interface Timable {
    long getStart();
    long timeDelta();
    String timeFormat();
}

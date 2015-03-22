package interfaces;

/**
 * The Locatable interface contains the methods to locate an Elevator object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Elevator
 */
public interface Locatable {

    int getLocation();
    void setLocation(int at);
}

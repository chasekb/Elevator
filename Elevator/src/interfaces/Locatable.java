package interfaces;

/**
 * The Locatable interface contains the methods to locate an Elevator object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Elevator
 * Created by kahlil on 2/8/15.
 */
public interface Locatable {

    int getLocation();
    void setLocation(int at);
}

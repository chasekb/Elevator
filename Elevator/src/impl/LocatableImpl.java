package impl;

import exception.InvalidDataException;
import interfaces.Locatable;

/**
 * The LocatableImpl class implements the data structures and methods to locate an Elevator object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Locatable
 * @see factory.LocatableImplFactory
 * Created by kahlil on 2/8/15.
 */
public class LocatableImpl implements Locatable {

    int location;

    public LocatableImpl(int loc) throws InvalidDataException {
        location = loc;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int at) {
        location = at;
    }
}

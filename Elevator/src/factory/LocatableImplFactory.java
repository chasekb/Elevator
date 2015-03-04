package factory;

import exception.InvalidDataException;
import impl.LocatableImpl;
import interfaces.Locatable;

/**
 * The LocatableImplFactory class returns a new Locatable object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Locatable
 * @see impl.LocatableImpl
 * Created by kahlil on 2/8/15.
 */
public class LocatableImplFactory {

    public static Locatable create(int floor) throws InvalidDataException{
        return new LocatableImpl(floor);
    }
}

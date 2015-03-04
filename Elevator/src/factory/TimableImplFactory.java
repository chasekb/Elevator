package factory;

import exception.InvalidDataException;
import impl.TimableImpl;
import interfaces.Timable;

/**
 * The TimableImplFactory class returns a new Timable object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Timable
 * @see impl.TimableImpl
 * Created by kahlil on 2/9/15.
 */
public class TimableImplFactory {

    public static Timable create(long startIn) throws InvalidDataException {
        return new TimableImpl(startIn);
    }
}

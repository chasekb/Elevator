package factory;

import exception.InvalidDataException;
import interfaces.Movable;
import impl.MovableImpl;

/**
 * The MovableImplFactory class returns a new Movable object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Movable
 * @see impl.MovableImpl
 * Created by kahlil on 2/8/15.
 */
public class MovableImplFactory {

    public static Movable create(long spd) throws InvalidDataException {
        return new MovableImpl(spd);
    }
}

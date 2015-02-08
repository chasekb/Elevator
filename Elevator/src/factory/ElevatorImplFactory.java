package factory;

import exception.InvalidDataException;
import impl.ElevatorImpl;
import interfaces.Elevator;

/**
 * Created by kahlil on 2/7/15.
 */
public class ElevatorImplFactory {

    public static Elevator create(int num, long spd, long dot, int cf) throws InvalidDataException{
        return new ElevatorImpl(num, spd, dot, cf);
    }
}

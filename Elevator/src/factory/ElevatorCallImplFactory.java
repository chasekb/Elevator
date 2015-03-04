package factory;

import exception.InvalidDataException;
import impl.ElevatorCallImpl;
import interfaces.ElevatorCall;

import java.util.ArrayList;

/**
 * Created by kahlil on 2/24/15.
 */
public class ElevatorCallImplFactory {

    public static ElevatorCall create(ArrayList elLst, ArrayList pndng)
            throws InvalidDataException {
        return new ElevatorCallImpl(elLst, pndng);
    }
}

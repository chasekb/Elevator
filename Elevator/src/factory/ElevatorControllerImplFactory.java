package factory;

import exception.InvalidDataException;
import impl.ElevatorControllerImpl;
import interfaces.ElevatorController;

import java.util.ArrayList;

/**
 * Created by kahlil on 2/22/15.
 */
public class ElevatorControllerImplFactory {

    public static ElevatorController create(long start, long end, int floors, int elevators, int maxP, long spd, long drOpTm,
                                            ArrayList flrs, ArrayList peeps, ArrayList finished, ArrayList floorRequest,
                                            ArrayList riderRequest) throws InvalidDataException {
        return new ElevatorControllerImpl(start, end, floors, elevators, maxP, spd, drOpTm, flrs, peeps, finished,
                floorRequest, riderRequest);
    }
}

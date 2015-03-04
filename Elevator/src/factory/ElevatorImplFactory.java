package factory;

import exception.InvalidDataException;
import impl.ElevatorImpl;
import interfaces.Elevator;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * The ElevatorImplFactory class returns a new Elevator object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Elevator
 * @see impl.ElevatorImpl
 * Created by kahlil on 2/7/15.
 */
public class ElevatorImplFactory {

    public static Elevator create(CountDownLatch cdl, long end, int numFlrs, long spd, long drOpTm, int num,
                                  long strt, ArrayList peeps, int maxP, ArrayList flrLst, ArrayList fnshd, ArrayList fr,
                                  ArrayList rr) throws InvalidDataException{
        return new ElevatorImpl(cdl, end, numFlrs, spd, drOpTm, num, strt, peeps, maxP, flrLst, fnshd, fr, rr);
    }
}

package impl;

import exception.InvalidDataException;
import interfaces.Elevator;
import interfaces.ElevatorCall;
import interfaces.Person;

import java.util.ArrayList;

/**
 * Created by kahlil on 2/26/15.
 */
public class ElevatorCallAdvancedImpl implements ElevatorCall {
    private ArrayList<Elevator> elevatorList;
    private ArrayList<Person> pending;

    public ElevatorCallAdvancedImpl(ArrayList elLst, ArrayList pndng)
            throws InvalidDataException {
        elevatorList = elLst;
        pending = pndng;
    }

    public int evalElevators() {
        int pick = 0;
        int emptyest = Integer.MAX_VALUE;
        for (Elevator e : elevatorList) {
            if (e.getRiders().size() < emptyest) {
                emptyest = e.getRiders().size();
                pick = e.getId();
            }
        }

        return pick;
    }

}

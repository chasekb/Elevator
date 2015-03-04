package impl;

import exception.InvalidDataException;
import interfaces.*;

import java.util.ArrayList;

/**
 * Created by kahlil on 2/24/15.
 */
public class ElevatorCallImpl implements ElevatorCall {

    private ArrayList<Elevator> elevatorList;
    private ArrayList<Person> pending;

    public ElevatorCallImpl(ArrayList elLst, ArrayList pndng)
            throws InvalidDataException {
        elevatorList = elLst;
        pending = pndng;
    }

    public int evalElevators() {
        int pick = 0;
        int idlest = Integer.MAX_VALUE;
        for (Elevator e : elevatorList) {
            if (e.getElevatorTasks().size() < idlest) {
                idlest = e.getElevatorTasks().size();
                pick = e.getId();
            }
        }

        return pick;
    }
}

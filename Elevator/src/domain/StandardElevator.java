package domain;

import exception.InvalidDataException;
import factory.ElevatorImplFactory;
import interfaces.Elevator;

/**
 * Created by kahlil on 2/7/15.
 */
public class StandardElevator implements Elevator {
    private Elevator myElevator;

    public StandardElevator(int num, long spd, long dot, int cf) throws InvalidDataException {
        myElevator = ElevatorImplFactory.create(num, spd, dot, cf);
    }

    public int getId() {
        return myElevator.getId();
    }
    public void pushFloorRequest(int floorIn) {
        //floorRequests.add(floorIn);
    }

    public void popFloorRequest() {
        //floorRequests.remove();
    }

    public void peekFloorRequest() {
        //floorRequests.peek();
    }

    public void pushRiderRequest(int floorIn) {
        //riderRequests.add(floorIn);
    }

    public void popRiderRequest() {
        //riderRequests.remove();
    }

    public void peekRiderRequest() {
        //riderRequests.peek();
    }

    public void moveUp() {
        //TODO ElevatorImpl-moveUp()
    }

    public void moveDown() {
        //TODO ElevatorImpl-moveDown()
    }

    public void openDoors() {
        //TODO ElevatorImpl-openDoors()
    }

    public void closeDoors() {
        //TODO closeDoors()
    }

}

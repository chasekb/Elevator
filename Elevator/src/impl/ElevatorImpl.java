package impl;

import exception.InvalidDataException;
import interfaces.Elevator;
import java.util.Queue;

/**
 * Created by kahlil on 2/7/15.
 */
public class ElevatorImpl implements Elevator {

    private int id;
    private long speed;
    private long doorOperationTime;
    private int currentFloor;
    public Queue<Integer> floorRequests;
    public Queue<Integer> riderRequests;

    public ElevatorImpl(int num, long spd, long dot, int cf) throws InvalidDataException {
        id = num;
        speed = spd;
        doorOperationTime = dot;
        currentFloor = cf;
    }

    public int getId() {
        return id;
    }

    public void pushFloorRequest(int floorIn) {
        floorRequests.add(floorIn);
    }

    public void popFloorRequest() {
        floorRequests.remove();
    }

    public void peekFloorRequest() {
        floorRequests.peek();
    }

    public void pushRiderRequest(int floorIn) {
        riderRequests.add(floorIn);
    }

    public void popRiderRequest() {
        riderRequests.remove();
    }

    public void peekRiderRequest() {
        riderRequests.peek();
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

package interfaces;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Elevator interface contains the methods of an elevator in the Elevator system.
 *
 * @author Bernard Chase
 * Created by kahlil on 2/7/15.
 */
public interface Elevator extends Locatable, Movable, Identifiable, Timable, Runnable {

    void addFloorRequest(int floorIn, Direction dirIn);
    void addRiderRequest(int floorTo);
    void anyFloorReqs();
    void anyRiderReqs();
    boolean checkDesiredDir(int floorIn, Direction dirIn);
    void closeDoors();
    Direction getDirection();
    CopyOnWriteArrayList getElevatorTasks();
    int getId();
    int getLocation();
    int getMaxPersons();
    void getPendingReqFloor();
    void getPendingReqRider();
    ArrayList getRiders();
    boolean getRunning();
    void openDoors();
    void processRequest();
    Direction reqDir(int curr, int req);
    void run();
    void setElevatorTasks(int flrIn, Direction dirIn);
    void sortReq();
}
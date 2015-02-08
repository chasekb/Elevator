package interfaces;

/**
 * Created by kahlil on 2/7/15.
 */
public interface Elevator {
    int getId();
    void pushFloorRequest(int floorIn);
    void popFloorRequest();
    void peekFloorRequest();
    void pushRiderRequest(int floorIn);
    void popRiderRequest();
    void peekRiderRequest();
    void moveUp();
    void moveDown();
    void openDoors();
    void closeDoors();
}
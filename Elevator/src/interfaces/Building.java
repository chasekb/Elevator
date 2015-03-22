package interfaces;

/**
 * The Building interface contains the methods for a building in the Elevator system.
 *
 * @author Bernard Chase
 */
public interface Building extends Timable {
    void addFloorRequest(int floorIn, Movable.Direction dirIn);
    long getEnd();
    int getNumElevators();
    int getNumFloors();
}

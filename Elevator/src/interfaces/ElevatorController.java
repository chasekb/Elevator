package interfaces;

/**
 * The ElevatorController interface contains the methods to control elevators in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Elevator
 */
public interface ElevatorController extends Runnable{
    void addFloorRequest(int floorIn, Movable.Direction dirIn);
    void run();
}

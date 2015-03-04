package interfaces;

/**
 * Created by kahlil on 2/10/15.
 */
public interface ElevatorController extends Runnable{
    void addFloorRequest(int floorIn, Movable.Direction dirIn);
    void run();
}

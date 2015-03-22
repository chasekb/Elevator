package interfaces;

/**
 * The Movable interface contains the methods to move an Elevator object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Elevator
 */
public interface Movable {

    enum Direction { UP, DOWN, IDLE }
    int getDestination();
    Direction getDirection();
    long getSpeed();
    int moveDown(int loc);
    int moveUp(int loc);
    Direction reqDir(int curr, int req);
    void setDestination(int destinationIn);
    void setDirection(Direction directionIn);
    void setSpeed(long speed);
}

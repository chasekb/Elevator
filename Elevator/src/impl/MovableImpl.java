package impl;

import exception.InvalidDataException;
import interfaces.Movable;

/**
 * The MovableImpl class implements the data structures and methods to move an Elevator object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Movable
 * @see factory.MovableImplFactory
 * Created by kahlil on 2/8/15.
 */
public class MovableImpl implements Movable {

    Direction currentDir;
    int destination;
    long speed;

    public MovableImpl(long spd) throws InvalidDataException {
        currentDir = Direction.IDLE;
        speed = spd;
    }

    public int moveDown(int loc) {
        loc--;
        setDirection(Direction.DOWN);

        try {
            synchronized (this) {
                this.wait(speed);
            }
        } catch (InterruptedException ie) { ie.printStackTrace(); }

        return loc;
    }

    public int moveUp(int loc) {
        loc++;
        setDirection(Direction.UP);

        try {
            synchronized (this) {
                this.wait(speed);
            }
        } catch (InterruptedException ie) { ie.printStackTrace(); }

        return loc;
    }

    public Direction reqDir(int curr, int req) {
        if (curr < req) {
            return Direction.UP;
        }
        else if (curr > req) {
            return Direction.DOWN;
        }
        else {
            return Direction.IDLE;
        }
    }

    public int getDestination() { return destination; }

    public Direction getDirection() {
        return currentDir;
    }

    public long getSpeed() {
        return speed;
    }

    public void setDestination(int dest) {
        destination = dest;

    }

    public void setDirection(Direction directionIn) {
        currentDir = directionIn;
    }

    public void setSpeed(long s) {
        speed = s;
    }

}

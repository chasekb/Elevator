import exceptions.InvalidDataException;

public class MovableImpl implements Movable {
    private int destination;
    private int speed;

    public MovableImpl(int destinationIn, int speedIn) throws InvalidDataException {
        setDestination(destinationIn);
        setSpeed(speedIn);
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int d) throws InvalidDataException {
        if (d <= 0) {
            throw InvalidDataException("Invalid floor entered.");
        }
        destination = d;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int s) throws InvalidDataException {
        if (s <= 0) {
            throw InvalidDataException("Invalid speed entered.");
        }
    }

    public boolean atDestination() {
        //TODO-movableimpl atDestination()
    }

    public void update() {
        //TODO-movableimpl update()
    }
}
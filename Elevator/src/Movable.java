import exceptions.InvalidDataException;

public interface Movable {
    boolean atDestination();
    int getDestination();
    int getSpeed();
    void setDestination() throws InvalidDataException;
    void setSpeed(int s) throws InvalidDataException;
    void update(double millis) throws InvalidDataException;
}

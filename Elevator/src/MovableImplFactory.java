import exceptions.InvalidDataException;

public class MovableImplFactory {
    public static Movable createMovable(int dest, int spd) throws InvalidDataException {
        return new MovableImpl(dest, spd);
    }
}
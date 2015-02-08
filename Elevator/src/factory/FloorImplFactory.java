package factory;

import exception.InvalidDataException;
import interfaces.Floor;
import impl.FloorImpl;

/**
 * Created by kahlil on 2/7/15.
 */
public class FloorImplFactory {

    public static Floor create(int number) throws InvalidDataException {
        return new FloorImpl(number);
    }
}

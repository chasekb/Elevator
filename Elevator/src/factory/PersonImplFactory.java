package factory;

import exception.InvalidDataException;
import impl.PersonImpl;
import interfaces.Person;

/**
 * Created by kahlil on 2/20/15.
 */
public class PersonImplFactory {
    public static Person create(int id, long strtIn, int maxFloor) throws InvalidDataException {
        return new PersonImpl(id, strtIn, maxFloor);
    }
}

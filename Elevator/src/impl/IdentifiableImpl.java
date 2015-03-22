package impl;

import exception.InvalidDataException;
import interfaces.Identifiable;

/**
 * The IdentifiableImpl implements the data structures and methods to identify an object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Identifiable
 * @see factory.IdentifiableImplFactory
 * @see impl.ElevatorImpl
 * @see impl.PersonImpl
 */
public class IdentifiableImpl implements Identifiable {

    int id;

    public IdentifiableImpl(int idIn) throws InvalidDataException {
        id = idIn;
    }

    public int getId() {
        return id;
    }
}

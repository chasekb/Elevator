package impl;

import exception.InvalidDataException;
import interfaces.Identifiable;

/**
 * The IdentifiableImpl implements the data structures and methods to identify an Elevator object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Identifiable
 * @see factory.IdentifiableImplFactory
 * Created by kahlil on 2/9/15.
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

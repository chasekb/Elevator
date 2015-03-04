package factory;

import exception.InvalidDataException;
import impl.IdentifiableImpl;
import interfaces.Identifiable;

/**
 * The IdentifiableImplFactory class returns a new Identifiable object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Identifiable
 * @see impl.IdentifiableImpl
 * Created by kahlil on 2/9/15.
 */
public class IdentifiableImplFactory {

    public static Identifiable create(int idIn) throws InvalidDataException {
        return new IdentifiableImpl(idIn);
    }
}

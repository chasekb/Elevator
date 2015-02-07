import exceptions.InvalidDataException;

public class IdentifiableImpl implements Identifiable {
    private int identifier;

    public IdentifiableImpl(int id) throws InvalidDataException {
        setIdentifier(id);
    }

    private void setIdentifier(int id) throws InvalidDataException{
        if (id == null || id < 0) { throw new InvalidDataException("Invalid data entered for identifier"); }
        identifier = id;
    }
}
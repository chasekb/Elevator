package domain;

import exception.InvalidDataException;
import factory.FloorImplFactory;
import interfaces.Floor;

/**
 * Created by kahlil on 2/7/15.
 */
public class StandardFloor implements Floor{
    private Floor myFloor;

    public StandardFloor(int number) throws InvalidDataException {
        myFloor = FloorImplFactory.create(number);
    }

    public int getNumber() { return myFloor.getNumber(); }
}

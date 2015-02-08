package impl;

import exception.InvalidDataException;
import interfaces.Floor;

/**
 * Created by kahlil on 2/7/15.
 */
public class FloorImpl implements Floor {

    private int number;

    public FloorImpl(int num) throws InvalidDataException {
        number = num;
    }

    public int getNumber() { return number; }
}

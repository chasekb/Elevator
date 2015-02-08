package factory;

import interfaces.Building;
import impl.BuildingImpl;
import exception.InvalidDataException;

/**
 * Created by kahlil on 2/7/15.
 */
public class BuildingImplFactory {

    public static Building create(int numFloors, int numElevators) throws InvalidDataException {
        return new BuildingImpl(numFloors, numElevators);
    }
}

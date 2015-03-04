package factory;

import interfaces.Building;
import impl.BuildingImpl;
import exception.InvalidDataException;

/**
 * The BuildingImplFactory class returns a new Building object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Building
 * @see impl.BuildingImpl
 * Created by kahlil on 2/7/15.
 */
public class BuildingImplFactory {

    public static Building create(int numFloors, int numElevators, int maxPersons, long spd, long drOpTm, int ppm,
                                  int drtn) throws InvalidDataException {
        return new BuildingImpl(numFloors, numElevators, maxPersons, spd, drOpTm, ppm, drtn);
    }
}

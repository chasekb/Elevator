package domain;

import exception.InvalidDataException;
import factory.BuildingImplFactory;
import interfaces.Building;

/**
 * Created by kahlil on 2/7/15.
 */
public class StandardBuilding implements Building {
    private Building myBuilding;

    public StandardBuilding(int numFloors, int numElevators) throws InvalidDataException {
        myBuilding = BuildingImplFactory.create(numFloors, numElevators);
    }

    public int getNumElevators() { return myBuilding.getNumElevators(); }

    public int getNumFloors() { return myBuilding.getNumFloors(); }

    public String toString() {
        return "Created Building. Building has " + getNumFloors() + " floors and " + getNumElevators() + " elevators.";
    }
}

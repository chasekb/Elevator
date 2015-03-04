package domain;

import exception.InvalidDataException;
import factory.BuildingImplFactory;
import factory.ElevatorImplFactory;
import interfaces.Building;
import interfaces.Elevator;
import interfaces.Movable;

import java.util.ArrayList;

/**
 * Created by kahlil on 2/7/15.
 */
public class StandardBuilding implements Building {
    private Building myBuilding;
    private static ArrayList floorRequest;
    private static ArrayList fnshd;
    private static ArrayList peeps;
    private static ArrayList riderRequest;

    public StandardBuilding(int numFloors, int numElevators, int maxP, long spd, long drOpTm, int ppm, int drtn)
            throws InvalidDataException {
        ArrayList<Elevator> elevatorsList = new ArrayList<>();
        floorRequest = new ArrayList();
        riderRequest = new ArrayList();

        myBuilding = BuildingImplFactory.create(numFloors, numElevators, maxP, spd, drOpTm, ppm, drtn);
    }

    public void addFloorRequest(int floorIn, Movable.Direction dirIn) {
        myBuilding.addFloorRequest(floorIn, dirIn);
    }

    public long getEnd() { return myBuilding.getEnd(); }

    public int getNumElevators() { return myBuilding.getNumElevators(); }

    public int getNumFloors() { return myBuilding.getNumFloors(); }

    public long getStart() { return myBuilding.getStart(); }

    public long timeDelta() { return myBuilding.timeDelta(); }

    public String timeFormat() { return myBuilding.timeFormat(); }

}

package impl;

import domain.StandardElevator;
import domain.StandardFloor;
import interfaces.Building;
import exception.InvalidDataException;
import interfaces.Elevator;
import interfaces.Floor;

import java.util.ArrayList;

/**
 * Created by kahlil on 2/7/15.
 */
public final class BuildingImpl implements Building {

    private int numElevators;
    private int numFloors;
    private long spd = 500;
    private long dot = 500;
    private int cf = 1;

    private ArrayList<Floor> floorsList;
    private ArrayList<Elevator> elevatorsList;

    public BuildingImpl(int floors, int elevators) throws InvalidDataException {
        numFloors = floors;
        numElevators = elevators;
        floorsList = new ArrayList<Floor>(numFloors);
        elevatorsList = new ArrayList<Elevator>(numElevators);

        for (int f = 1; f <= numFloors; f++) {
            floorsList.add(new StandardFloor(f));
        }

        for (int e = 1; e <= numElevators; e++) {
            elevatorsList.add(new StandardElevator(e, spd, dot, cf));
        }
    }

    public int getNumElevators() { return numElevators; }

    public int getNumFloors() { return numFloors; }
}

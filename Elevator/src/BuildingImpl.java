import java.util.ArrayList;

/**
 * Created by kahlil on 2/7/15.
 */
public class BuildingImpl implements Building{
    private ArrayList<Floor>[] floorsList;
    private ArrayList<Elevator>[] elevatorsList;

    private BuildingImpl() {
    }

    public static BuildingImpl createBuildingImpl() {
        return new BuildingImpl();
    }
}

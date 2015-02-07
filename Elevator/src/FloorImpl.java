/**
 * Created by kahlil on 2/7/15.
 */
public class FloorImpl implements Floor {
    public int number;

    private FloorImpl() {
    }

    public static FloorImpl createFloorImpl() {
        return new FloorImpl();
    }
}

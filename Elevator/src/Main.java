import domain.StandardBuilding;
import exception.InvalidDataException;
import interfaces.Building;

/**
 * Created by kahlil on 2/7/15.
 */
public class Main {
    public static void main(String[] args){

        try {
            Building theBuilding = new StandardBuilding(16, 4);
            System.out.println(theBuilding.toString());
        } catch (InvalidDataException ide) { ide.printStackTrace(); }

//        elevator(1).goto(11);
//        elevator(2).goto(14);
//        elevator(2).goto(13);
//        elevator(2).goto(15);
//        elevator(3).goto(5);
//        elevator(3).goto(16);
//        elevator(3).goto(1);
//        elevator(3).goto(2);
//        elevator(3).goto(5);
//        elevator(3).goto(3);

    }
}

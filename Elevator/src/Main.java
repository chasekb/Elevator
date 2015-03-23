import domain.StandardBuilding;
import exception.InvalidDataException;
import interfaces.Building;

import java.io.*;

/**
 * Created by kahlil on 2/7/15.
 */
public class Main {

    public static void main(String[] args) {

        // first implementation
//        try {
//            Building theBuilding = new StandardBuilding(16, 2, 10, 500, 500);
//            theBuilding.addRiderRequest(11);
//
//            try {
//                synchronized (theBuilding) {
//                    theBuilding.wait(1700);
//                }
//            } catch (InterruptedException ie) { ie.printStackTrace(); }
//
//            theBuilding.addRiderRequest(14);
//
//            try {
//                synchronized (theBuilding) {
//                    theBuilding.wait(700);
//                }
//            } catch (InterruptedException ie) { ie.printStackTrace(); }
//
//            theBuilding.addRiderRequest(13);
//
//        } catch (InvalidDataException ide) { ide.printStackTrace(); }

        // second implementation
        String inputFile = "input.csv";
        String separator = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String in = br.readLine();

            String[] inSplit = in.split(separator);
            Integer[] inTo = new Integer[inSplit.length];
            for (int i = 0; i < inSplit.length; i++) {
                inTo[i] = Integer.parseInt(inSplit[i]);
                //System.out.println(inTo[i]);
            }

            Building bldg2 = new StandardBuilding(inTo[0], inTo[1], inTo[2], (long) inTo[3], (long) inTo[4],
                    inTo[5], inTo[6]);
        } catch (IOException | InvalidDataException e) {
            e.printStackTrace();
        }
    }
}

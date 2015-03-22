import factory.ObservationImplFactory;
import impl.ObservationImpl;
import interfaces.Observation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by kahlil on 3/21/15.
 */
public class UrlOpener extends Thread {

    private static ConcurrentHashMap<LocalDate, ObservationImpl> chm;
    private static CopyOnWriteArrayList<LocalDate> keys;
    private static String line;
    private static URL url;
    private static HttpURLConnection urlConnection;

    public UrlOpener(URL urlIn) {
        chm = new ConcurrentHashMap();
        keys = new CopyOnWriteArrayList<LocalDate>();
        line = "";
        url = urlIn;
        System.out.println("Received " + url);
    }

    public void run() {
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5 * 1000);
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            while ((line = br.readLine()) != null) {
                getLine(line);
            }

            urlConnection.disconnect();
            br.close();
            //getKeys();

        } catch (IOException ioe) { ioe.printStackTrace(); }
    }

    public static void getKeys() {
        for (LocalDate ld : keys) {
            ObservationImpl o = null;
            System.out.print(ld + " ");
            o = chm.get(ld);
            System.out.println(o.getClose());
        }
    }

    public static void getLine(String lineIn) {
        String[] lineInSplit = lineIn.split(",");
        if ((lineInSplit.length == 6) && (!lineInSplit[0].matches("values:Date"))) {
            ObservationImpl o = new ObservationImpl(lineIn);
            chm.put(o.getLd(), o);
            keys.add(o.getLd());
        }
    }

    public void setObservation(String lineIn) {
        Observation o = ObservationImplFactory.create(lineIn);

    }
}

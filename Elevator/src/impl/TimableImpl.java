package impl;

import exception.InvalidDataException;
import interfaces.Timable;

import java.util.concurrent.TimeUnit;

/**
 * The TimableImpl class implements the data structures and methods to time an object in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Timable
 * @see factory.TimableImplFactory
 * Created by kahlil on 2/9/15.
 */
public class TimableImpl implements Timable {

    long start;

    public TimableImpl(long startIn) throws InvalidDataException {
        start = startIn;
    }

    public long getStart() {
        return start;
    }

    public long timeDelta() {
        return System.nanoTime() - getStart(); }

    public String timeFormat() {
        long ml = timeDelta();
        long hours = TimeUnit.NANOSECONDS.toHours(ml);
        long minutes = TimeUnit.NANOSECONDS.toMinutes(ml) - TimeUnit.HOURS.toMinutes(TimeUnit.NANOSECONDS.toHours(ml));
        long seconds = TimeUnit.NANOSECONDS.toSeconds(ml) - TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(ml));
        long mills = TimeUnit.NANOSECONDS.toMillis(ml) - TimeUnit.SECONDS.toMillis(TimeUnit.NANOSECONDS.toSeconds(ml));

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, mills);
    }
}

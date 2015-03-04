package interfaces;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Floor interface contains the methods for a building floor in the Elevator system.
 *
 * @author Bernard Chase
 * Created by kahlil on 2/7/15.
 */
public interface Floor extends Identifiable {

    void addVisitor(Person p);
    void removeVisitor(Person p);
    int getId();
    CopyOnWriteArrayList getVisitors();
}

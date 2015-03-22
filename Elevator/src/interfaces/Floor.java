package interfaces;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Floor interface contains the methods for a building floor in the Elevator system.
 *
 * @author Bernard Chase
 */
public interface Floor extends Identifiable {

    void addVisitor(Person p);
    void removeVisitor(Person p);
    int getId();
    CopyOnWriteArrayList getVisitors();
}

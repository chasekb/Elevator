package impl;

import exception.InvalidDataException;
import factory.IdentifiableImplFactory;
import interfaces.Floor;
import interfaces.Identifiable;
import interfaces.Person;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The FloorImpl class implements the data structures and methods of a floor in the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.Floor
 * @see factory.FloorImplFactory
 */
public class FloorImpl implements Floor {

    private CopyOnWriteArrayList<Person> visitors;
    private Identifiable myID;

    public FloorImpl(int num) throws InvalidDataException {
        myID = IdentifiableImplFactory.create(num);
        visitors = new CopyOnWriteArrayList<>();
    }

    public void addVisitor(Person p) {
        synchronized (visitors) {
            visitors.add(p);
        }
    }

    public void removeVisitor(Person p) {
        synchronized (visitors) {
            visitors.remove(p);
        }
    }

    public int getId() { return myID.getId(); }

    public CopyOnWriteArrayList getVisitors() { return visitors; }
}

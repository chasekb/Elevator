package impl;

import exception.InvalidDataException;
import factory.IdentifiableImplFactory;
import interfaces.Identifiable;
import interfaces.Movable;
import interfaces.Person;
import interfaces.Request;

/**
 * Created by kahlil on 2/27/15.
 */
public class RequestImpl implements Request {
    private Movable.Direction dirIn;
    private int floorIn;
    private int id;
    private Identifiable myId;
    private Person requester;
    private Type type;


    public RequestImpl(Person rqstr, int flrIn) throws InvalidDataException {
        requester = rqstr;
        floorIn = flrIn;
        myId = IdentifiableImplFactory.create(id++);
        type = Type.RDR;
    }

    public RequestImpl(Person rqstr, int flrIn, Movable.Direction drIn) throws InvalidDataException {
        requester = rqstr;
        floorIn = flrIn;
        myId = IdentifiableImplFactory.create(id++);
        dirIn = drIn;
        type = Type.FLR;
    }

    public int getDestination() {
        return floorIn;
    }

    public int getId() {
        return myId.getId();
    }

    public Person getRequester() {
        return requester;
    }

    public Type getType() {
        return type;
    }
}

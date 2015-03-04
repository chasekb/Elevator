package interfaces;

/**
 * Created by kahlil on 2/27/15.
 */
public interface Request extends Identifiable {
    enum Type { FLR, RDR }
    int getDestination();
    int getId();
    Person getRequester();
    Type getType();
}

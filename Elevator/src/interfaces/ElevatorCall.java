package interfaces;

/**
 * The ElevatorCall interface contains the methods necessary to evaluate which elevator should be assigned a request in
 * the Elevator system.
 *
 * @author Bernard Chase
 * @see interfaces.ElevatorController
 */
public interface ElevatorCall {
    int evalElevators();
}

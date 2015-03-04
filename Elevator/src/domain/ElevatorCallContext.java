package domain;

import interfaces.ElevatorCall;

import java.util.ArrayList;

/**
 * Created by kahlil on 2/26/15.
 */
public class ElevatorCallContext {
    private ElevatorCall ecContext;

    public void setEcContext(ElevatorCall ec) {
        this.ecContext = ec;
    }

    public int createElCall(ArrayList el, ArrayList pndg) {
        return ecContext.evalElevators();
    }
}

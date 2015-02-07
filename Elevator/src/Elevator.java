public interface Elevator extends Movable, Identifiable {
    void pushFloorRequest(int floorIn);
    void popFloorRequest(int floorIn);
    void peekFloorRequest(int floorIn);
    void pushRiderRequest(int floorIn);
    void popRiderRequest(int floorIn);
    void peekRiderRequest(int floorIn);
    void pushDirection(boolean directionIn);
    void popDirection(boolean directionIn);
    void moveUp(int timeIn);
    void moveDown(boolean directionIn);
    void openDoors();
    void closeDoors();
}
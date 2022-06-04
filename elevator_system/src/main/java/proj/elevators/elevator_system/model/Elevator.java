package proj.elevators.elevator_system.model;

import java.util.List;

public interface Elevator {

    ElevatorStatus status();

    void update(Elevator elevator);

    void updatePosition(int floorNumber);

    int id();

    int floorNumber();

    Direction direction();

    int currentDestinationFloor();

    List<Target> targets();

    void addTarget(Target target);

    void dropOff();

    void pickUpIfSameFloor(Pickup pickup);

    boolean moveUsingPickupOrNot(Pickup pickup);

    void move();

    boolean haveToTurnBack(int relativeDistance);

    void increaseWaitTimeScalar();
}
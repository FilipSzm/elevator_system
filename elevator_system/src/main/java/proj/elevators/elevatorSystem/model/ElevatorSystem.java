package proj.elevators.elevatorSystem.model;

import java.util.List;

public interface ElevatorSystem {

    void pickup(int floorNumber, Direction direction);

    void target(int elevatorId, int floorNumber);

    void update(int elevatorId, Elevator elevator);

    void update(int elevatorId, int floorNumber);

    void step();

    List<ElevatorStatus> status();
}

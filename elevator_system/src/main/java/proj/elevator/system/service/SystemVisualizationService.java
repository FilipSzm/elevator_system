package proj.elevator.system.service;

import org.springframework.stereotype.Service;
import proj.elevator.system.model.ElevatorStatus;
import proj.elevator.system.model.Direction;
import proj.elevator.system.model.ElevatorSystem;
import proj.elevator.system.system.ElevatorSystemImpl;

import java.util.List;

@Service
public class SystemVisualizationService {

    ElevatorSystem elevatorSystem;

    public SystemVisualizationService() {
        elevatorSystem = new ElevatorSystemImpl(5);
    }

    public synchronized List<ElevatorStatus> status() {
        return elevatorSystem.status();
    }

    public synchronized void step() {
        elevatorSystem.step();
    }

    public synchronized void update(int elevatorId, int floorNumber) {
        elevatorSystem.update(elevatorId, floorNumber);
    }

    public synchronized void pickup(int floorNumber, Direction direction) {
        elevatorSystem.pickup(floorNumber, direction);
    }

    public synchronized void target(int elevatorId, int floorNumber) {
        elevatorSystem.target(elevatorId, floorNumber);
    }
}

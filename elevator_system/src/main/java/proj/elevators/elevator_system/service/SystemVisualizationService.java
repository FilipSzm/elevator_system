package proj.elevators.elevator_system.service;

import org.springframework.stereotype.Service;
import proj.elevators.elevator_system.model.Direction;
import proj.elevators.elevator_system.model.ElevatorStatus;
import proj.elevators.elevator_system.model.ElevatorSystem;
import proj.elevators.elevator_system.system.ElevatorSystemImpl;

import java.util.List;

@Service
public class SystemVisualizationService {

    ElevatorSystem elevatorSystem;

    public SystemVisualizationService() {
        elevatorSystem = new ElevatorSystemImpl(5);
    }

    synchronized public List<ElevatorStatus> status() {
        return elevatorSystem.status();
    }

    synchronized public void step() {
        elevatorSystem.step();
    }

    synchronized public void update(int elevatorId, int floorNumber) {
        elevatorSystem.update(elevatorId, floorNumber);
    }

    synchronized public void pickup(int floorNumber, Direction direction) {
        elevatorSystem.pickup(floorNumber, direction);
    }

    synchronized public void target(int elevatorId, int floorNumber) {
        elevatorSystem.target(elevatorId, floorNumber);
    }
}

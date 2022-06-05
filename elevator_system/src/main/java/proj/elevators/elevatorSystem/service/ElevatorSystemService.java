package proj.elevators.elevatorSystem.service;

import org.springframework.stereotype.Service;
import proj.elevators.elevatorSystem.model.Direction;
import proj.elevators.elevatorSystem.model.Elevator;
import proj.elevators.elevatorSystem.model.ElevatorSystem;
import proj.elevators.elevatorSystem.model.param.StatusParam;
import proj.elevators.elevatorSystem.system.ElevatorSystemImpl;

import java.util.Collection;

@Service
public class ElevatorSystemService {

    ElevatorSystem elevatorSystem;

    public ElevatorSystemService() {
        elevatorSystem = initSystem(10, 8F);
    }

    private ElevatorSystem initSystem(int numberOfElevators, float targetScalar) {
        return new ElevatorSystemImpl(numberOfElevators, targetScalar);
    }

    public void init(int numberOfElevators, float targetScalar) {
        elevatorSystem = initSystem(numberOfElevators, targetScalar);
    }

    public void pickup(int floorNumber, Direction direction) {
        elevatorSystem.pickup(floorNumber, direction);
    }

    public void target(int elevatorId, int floorNumber) {
        elevatorSystem.target(elevatorId, floorNumber);
    }

    public void update(int elevatorId, Elevator elevator) {
        elevatorSystem.update(elevatorId, elevator);
    }

    public void step() {
        elevatorSystem.step();
    }

    public StatusParam status() {
        var elevators = elevatorSystem.status();
        var pickups = elevatorSystem.pickupList();
        var targets = elevatorSystem.elevators().stream()
                .map(Elevator::targets)
                .flatMap(Collection::stream).toList();

        return new StatusParam(elevators, pickups, targets);
    }
}

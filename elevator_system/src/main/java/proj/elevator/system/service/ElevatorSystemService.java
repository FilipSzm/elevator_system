package proj.elevator.system.service;

import org.springframework.stereotype.Service;
import proj.elevator.system.model.Direction;
import proj.elevator.system.model.Elevator;
import proj.elevator.system.model.ElevatorSystem;
import proj.elevator.system.model.Pickup;
import proj.elevator.system.model.param.StatusResponse;
import proj.elevator.system.system.ElevatorSystemImpl;

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

    public StatusResponse status() {
        var elevators = elevatorSystem.status();
        var pickups = elevatorSystem.pickupList().stream()
                .map(Pickup::toPickupResponse)
                .toList();
        var targets = elevatorSystem.elevators().stream()
                .map(
                        e -> e.targets().stream()
                                .map(t -> t.toTargetResponse(e.id()))
                                .toList()
                )
                .flatMap(Collection::stream)
                .toList();

        return new StatusResponse(elevators, pickups, targets);
    }
}

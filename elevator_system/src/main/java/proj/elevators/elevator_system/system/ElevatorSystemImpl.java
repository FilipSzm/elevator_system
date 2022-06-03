package proj.elevators.elevator_system.system;

import proj.elevators.elevator_system.exception.DuplicateElevatorIdException;
import proj.elevators.elevator_system.exception.IncorrectNumberOfElevatorsException;
import proj.elevators.elevator_system.exception.IncorrectPickupDirectionException;
import proj.elevators.elevator_system.exception.NoElevatorWithIdException;
import proj.elevators.elevator_system.model.*;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ElevatorSystemImpl implements ElevatorSystem {

    final private List<Elevator> elevators;
    private List<Pickup> pickupList;

    public ElevatorSystemImpl(int numberOfElevators) {
        elevators = initElevators(numberOfElevators);
        pickupList = new ArrayList<>();
    }

    private List<Elevator> initElevators(int numberOfElevators) {
        if (!ValueRange.of(1, 16).isValidIntValue(numberOfElevators))
            throw new IncorrectNumberOfElevatorsException();

        var elevators = new ArrayList<Elevator>(numberOfElevators);
        IntStream.range(0, numberOfElevators)
                .forEachOrdered(i -> elevators.add(new ElevatorImpl(i)));
        return elevators;
    }

    @Override
    public void pickup(int floorNumber, Direction direction) {
        if (direction == Direction.NO_DIRECTION)
            throw new IncorrectPickupDirectionException();

        var pickupWithSameFloorNumber = pickupList.stream()
                .filter(p -> p.floorNumber() == floorNumber)
                .findFirst();

        pickupWithSameFloorNumber.ifPresentOrElse(
                pickup -> pickup.direction(direction),
                () -> pickupList.add(new Pickup(floorNumber, direction))
        );
    }

    @Override
    public void target(int elevatorId, int floorNumber) {
        elevators.stream()
                .filter(e -> e.id() == elevatorId)
                .findFirst()
                .orElseThrow(NoElevatorWithIdException::new)
                .addTarget(new Target(floorNumber));
    }

    @Override
    public void update(int elevatorId, Elevator elevator) {
        if (elevators.stream().anyMatch(e -> e.id() == elevator.id()))
            throw new DuplicateElevatorIdException();

        elevators.stream()
                .filter(e -> e.id() == elevatorId)
                .findFirst()
                .orElseThrow(NoElevatorWithIdException::new)
                .update(elevator);
    }

    @Override
    public void update(int elevatorId, int floorNumber) {
        elevators.stream()
                .filter(e -> e.id() == elevatorId)
                .findFirst()
                .orElseThrow(NoElevatorWithIdException::new)
                .updatePosition(floorNumber);
    }

    @Override
    public void step() {
        pickupList = updatePickupList();

        var notMovedToPickups = new ArrayList<>(pickupList);
        elevators.forEach(e -> {
            e.dropOff();
            if (notMovedToPickups.isEmpty()) {
                e.move();
                return;
            }
            var pickup =
                    pickupWithHighestPriorityForElevator(notMovedToPickups, e);
            if (e.moveUsingPickupOrNot(pickup))
                notMovedToPickups.remove(pickup);
        });

        pickupList.forEach(Pickup::increaseWaitTimeScalar);
        elevators.forEach(Elevator::makeStep);
    }

    private List<Pickup> updatePickupList() {
        var pickupList = new ArrayList<Pickup>();
        for (var pickup : this.pickupList) {
            elevators.stream()
                    .filter(e -> e.floorNumber() == pickup.floorNumber())
                    .findFirst()
                    .ifPresentOrElse(
                            e -> e.pickUpIfSameFloor(pickup),
                            () -> pickupList.add(pickup)
                    );
        }
        return pickupList;
    }

    private Pickup pickupWithHighestPriorityForElevator(List<Pickup> pickupList, Elevator elevator) {
        float bestPriority = -1F;
        Pickup bestPickup = null;
        for (var p : pickupList) {
            var priority = p.calculatePriority(elevator);
            if (priority > bestPriority) {
                bestPriority = priority;
                bestPickup = p;
            }
        }
        return bestPickup;
    }

    @Override
    public List<ElevatorStatus> status() {
        return elevators.stream()
                .map(Elevator::status)
                .toList();
    }
}

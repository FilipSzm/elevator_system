package proj.elevators.elevatorSystem.system;

import proj.elevators.elevatorSystem.exception.*;
import proj.elevators.elevatorSystem.model.*;
import proj.elevators.elevatorSystem.model.util.Pair;
import proj.elevators.elevatorSystem.model.util.Triple;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
        if (elevators.stream().anyMatch(e -> e.id() == elevator.id() && e.id() != elevatorId))
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
        elevators.forEach(Elevator::dropOff);
        pickupList = updatePickupList();

        var notMovedToPickups = new ArrayList<>(pickupList);
        var notMovedElevators = new ArrayList<>(elevators);

        while (!notMovedToPickups.isEmpty() && !notMovedElevators.isEmpty()) {
            var pair = pickupElevatorPairWithHighestPriority(notMovedToPickups, notMovedElevators);

            if (pair.second().moveUsingPickupOrNot(pair.first()))
                notMovedToPickups.remove(pair.first());

            notMovedElevators.remove(pair.second());
        }
        notMovedElevators.forEach(Elevator::move);

        elevators.forEach(Elevator::dropOff);
        pickupList = updatePickupList();

        elevators.forEach(Elevator::increaseWaitTimeScalar);
        pickupList.forEach(Pickup::increaseWaitTimeScalar);
    }

    private Pair<Pickup, Elevator> pickupElevatorPairWithHighestPriority(List<Pickup> pickupList, List<Elevator> elevators) {
        Triple<Pickup, Elevator, Float> bestTriple = pickupList.stream()
                .map(p -> tripleWithBestElevator(p, elevators))
                .max(Comparator.comparing(Triple::third))
                .orElseThrow(EmptyCollectionException::new);

        return new Pair<>(bestTriple.first(), bestTriple.second());
    }

    private Triple<Pickup, Elevator, Float> tripleWithBestElevator(Pickup p, List<Elevator> elevators) {
        var tripleList = elevators.stream()
                .map(e -> new Triple<>(p, e, p.calculatePriority(e))).toList();

        var highestPriority = tripleList.stream()
                .map(Triple::third)
                .max(Float::compareTo)
                .orElseThrow(EmptyCollectionException::new);

        var elementsWithHighestPriority = tripleList.stream()
                .filter(e -> Objects.equals(e.third(), highestPriority)).toList();

        for (var triple : elementsWithHighestPriority) {
            if (Direction.areDirectionsSame(triple.first().direction(), triple.second().direction()))
                return triple;
        }
        return elementsWithHighestPriority.stream().findFirst().orElseThrow(EmptyCollectionException::new);
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

    @Override
    public List<ElevatorStatus> status() {
        return elevators.stream()
                .map(Elevator::status)
                .toList();
    }

    @Override
    public List<Pickup> pickupList() {
        return pickupList;
    }

    @Override
    public List<Elevator> statusFull() {
        return elevators;
    }
}

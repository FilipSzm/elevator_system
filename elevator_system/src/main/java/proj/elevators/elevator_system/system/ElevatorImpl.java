package proj.elevators.elevator_system.system;

import proj.elevators.elevator_system.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static proj.elevators.elevator_system.model.Direction.*;

public class ElevatorImpl implements Elevator {

    private int id;
    private int floorNumber;
    private Direction direction;
    private int currentDestinationFloor;
    private List<Target> targets;

    public ElevatorImpl(int id) {
        this.id = id;
        floorNumber = 0;
        direction = NO_DIRECTION;
        currentDestinationFloor = 0;
        targets = new ArrayList<>();
    }

    @Override
    public ElevatorStatus status() {
        return new ElevatorStatus(id, floorNumber, currentDestinationFloor);
    }

    @Override
    public void update(Elevator elevator) {
        id = elevator.id();
        floorNumber = elevator.floorNumber();
        direction = elevator.direction();
        currentDestinationFloor = elevator.currentDestinationFloor();
        targets = elevator.targets();
    }

    @Override
    public void updatePosition(int floorNumber) {
        this.floorNumber = floorNumber;
        direction = NO_DIRECTION;
        currentDestinationFloor = floorNumber;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public int floorNumber() {
        return floorNumber;
    }

    @Override
    public Direction direction() {
        return direction;
    }

    @Override
    public int currentDestinationFloor() {
        return currentDestinationFloor;
    }

    @Override
    public List<Target> targets() {
        return targets;
    }

    @Override
    public void addTarget(Target target) {
        if (targets.stream().noneMatch(t -> t.floorNumber() == target.floorNumber()))
            targets.add(target);
    }

    @Override
    public void dropOff() {
        if (targets.stream().anyMatch(t -> t.floorNumber() == floorNumber)) {
            direction = NO_DIRECTION;
            targets = targets.stream()
                    .filter(t -> t.floorNumber() != floorNumber)
                    .toList();
        }
    }

    @Override
    public void pickUpIfSameFloor(Pickup pickup) {
        if (floorNumber == pickup.floorNumber())
            direction = NO_DIRECTION;
    }

    @Override
    public boolean moveUsingPickupOrNot(Pickup pickup) {
        var bestTarget = findBestTarget();

        if (bestTarget.isPresent() && isTargetsPriorityGreaterThanPickups(bestTarget.get(), pickup)) {
            makeMove(bestTarget.get().floorNumber());
            return false;
        }

        makeMove(pickup.floorNumber());
        return true;
    }

    @Override
    public void move() {
        makeMove(findBestTarget().orElse(new Target(floorNumber)).floorNumber());
    }

    private Optional<Target> findBestTarget() {
        float bestPriority = -1F;
        Optional<Target> bestTarget = Optional.empty();
        for (var t : targets) {
            var priority = t.calculatePriority(this);
            if (priority > bestPriority) {
                bestPriority = priority;
                bestTarget = Optional.of(t);
            }
        }
        return bestTarget;
    }

    private void makeMove(int destinationFloor) {
        if (destinationFloor == floorNumber) {
            direction = NO_DIRECTION;
            currentDestinationFloor = destinationFloor;
            return;
        }

        if (destinationFloor > floorNumber) {
            floorNumber++;
            direction = UP;
            currentDestinationFloor = destinationFloor;
            return;
        }

        floorNumber--;
        direction = DOWN;
        currentDestinationFloor = destinationFloor;
    }

    @Override
    public boolean haveToTurnBack(int relativeDistance) {
        if (this.direction == NO_DIRECTION)
            return false;

        if (this.direction == UP && relativeDistance >= 0)
            return false;

        return this.direction != DOWN || relativeDistance > 0;
    }

    @Override
    public void makeStep() {
        targets.forEach(Target::increaseWaitTimeScalar);
    }

    private boolean isTargetsPriorityGreaterThanPickups(Target target, Pickup pickup) {
        return target.calculatePriority(this) > pickup.calculatePriority(this);
    }
}
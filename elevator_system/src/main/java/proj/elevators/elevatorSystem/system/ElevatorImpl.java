package proj.elevators.elevatorSystem.system;

import proj.elevators.elevatorSystem.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static proj.elevators.elevatorSystem.model.Direction.*;

/**
 * Default implementation of {@code Elevator} interface
 */
public class ElevatorImpl implements Elevator {

    /**
     * {@code id} of an {@code Elevator}, unique among all elevators in {@code ElevatorSystem}
     */
    private int id;

    /**
     * number of floor at which {@code Elevator} currently is.
     */
    private int floorNumber;

    /**
     * direction in which {@code Elevator} is moving.
     */
    private Direction direction;

    /**
     * number of floor to which {@code Elevator} is currently moving.
     */
    private int currentDestinationFloor;

    /**
     * list of all targets that are currently assigned to this {@code Elevator}
     */
    private List<Target> targets;

    /**
     * Constructor that takes {@code id} as parameter and initializes
     * all fields ({@code floorNumber} initializes as 0,
     * {@code direction} initializes as NO_DIRECTION, {@code currentDestinationFloor} initializes as 0,
     * and {@code targets} initializes as empty list).
     * @param id id of crated {@code Elevator}
     */
    public ElevatorImpl(int id) {
        this.id = id;
        floorNumber = 0;
        direction = NO_DIRECTION;
        currentDestinationFloor = 0;
        targets = new ArrayList<>();
    }

    /**
     * @return {@code ElevatorStatus} of this {@code Elevator}
     */
    @Override
    public ElevatorStatus status() {
        return new ElevatorStatus(id, floorNumber, currentDestinationFloor);
    }

    /**
     * updates all fields of this {@code Elevator} to values from {@code elevator} from parameter
     * @param elevator {@code Elevator} to copy parameters from
     */
    @Override
    public void update(Elevator elevator) {
        id = elevator.id();
        floorNumber = elevator.floorNumber();
        direction = elevator.direction();
        currentDestinationFloor = elevator.currentDestinationFloor();
        targets = elevator.targets();
    }

    /**
     * function that updates {@code floorNumber} of this {@code Elevator} to floor with number given as parameter.
     * After this operation {@code direction} field is set to {@code NO_DIRECTION} and {@code currentDestinationFloor}
     * to the same value as {@code floorNumber}.
     * @param floorNumber number of floor to change position to
     */
    @Override
    public void updatePosition(int floorNumber) {
        this.floorNumber = floorNumber;
        direction = NO_DIRECTION;
        currentDestinationFloor = floorNumber;
    }

    /**
     * {@code id} getter.
     * @return {@code id}
     */
    @Override
    public int id() {
        return id;
    }

    /**
     * {@code floorNumber} getter.
     * @return {@code floorNumber}
     */
    @Override
    public int floorNumber() {
        return floorNumber;
    }

    /**
     * {@code direction} getter.
     * @return {@code direction}
     */
    @Override
    public Direction direction() {
        return direction;
    }

    /**
     * {@code currentDestinationFloor} getter.
     * @return {@code currentDestinationFloor}
     */
    @Override
    public int currentDestinationFloor() {
        return currentDestinationFloor;
    }

    /**
     * {@code targets} getter.
     * @return list of all {@code targets} that this {@code Elevator} is assigned to
     */
    @Override
    public List<Target> targets() {
        return targets;
    }

    /**
     * function that adds {@code Target} specified in param to list
     * if there is no {@code Target} with same {@code floorNumber} already in the list.
     * of all {@code targets} of this {@code Elevator}.
     * @param target new {@code Target} to add
     */
    @Override
    public void addTarget(Target target) {
        if (targets.stream().noneMatch(t -> t.floorNumber() == target.floorNumber()))
            targets.add(target);
    }

    /**
     * function that deletes all {@code Targets} from this {@code Elevator's}
     * {@code targets} list that are on the same floor this {@code Elevator},
     * if any {@code Target} get deleted direction of this {@code Elevator}
     * is set to {@code NO_DIRECTION} and {@code currentDestinationFloor} to {@code floorNumber}
     */
    @Override
    public void dropOff() {
        if (targets.stream().anyMatch(t -> t.floorNumber() == floorNumber)) {
            direction = NO_DIRECTION;
            currentDestinationFloor = floorNumber;
            targets = targets.stream()
                    .filter(t -> t.floorNumber() != floorNumber)
                    .collect(Collectors.toList());
        }
    }

    /**
     * sets {@code direction} of {@code Elevator} to {@code NO_DIRECTION}
     * and {@code currentDestinationFloor} to {@code floorNumber} if {@code Pickup} given
     * in parameter is on the same floor as this {@code Elevator}.
     * @param pickup {@code Pickup} that gets checked if is on the same floor
     */
    @Override
    public void pickUpIfSameFloor(Pickup pickup) {
        if (floorNumber == pickup.floorNumber()) {
            direction = NO_DIRECTION;
            currentDestinationFloor = floorNumber;
        }
    }

    /**
     * function that tries to move this {@code Elevator}, if {@code Pickup} given as
     * parameter have higher priority than all {@code targets} of this {@code Elevator}
     * it moves in direction of this {@code pickup}, otherwise it moves to the direction
     * of {@code Target} with the highest priority. Move consist of changing destination
     * floor to {@code floorNumber} of {@code Pickup} or {@code Target} and changing this {@code Elevator's}
     * {@code floorNumber} one value closer to {@code Pickup} or {@code Target}
     * (incrementation, decrementation or not changing if {@code Pickup} or {@code Target} is on the same floor),
     * also {@code direction} of this {@code Elevator} changes symmetrically to {@code floorNumber} change.
     * @param pickup {@code Pickup to move to if have higher priority than all {@code targets}}
     * @param targetScalar scalar for {@code Target} priority
     * @return whether {@code Elevator} is moving to {@code Pickup} given as parameter or not
     */
    @Override
    public boolean moveUsingPickupOrNot(Pickup pickup, float targetScalar) {
        var bestTarget = findBestTarget(targetScalar);

        if (bestTarget.isPresent() && isTargetsPriorityHigherThanPickups(bestTarget.get(), pickup, targetScalar)) {
            makeMove(bestTarget.get().floorNumber());
            return false;
        }

        makeMove(pickup.floorNumber());
        return true;
    }

    /**
     * function that tries to move this {@code Elevator} to the direction of {@code Target} with the highest priority.
     * if target list is empty it stays in place and changes its {@code direction} to {@code NO_DIRECTION}.
     * Move consist of changing destination floor to {@code floorNumber} of {@code Target} and changing this
     * {@code Elevator's} {@code floorNumber} one value closer to {@code Target}
     * (incrementation, decrementation or not changing if {@code Target} is on the same floor),
     * also {@code direction} of this {@code Elevator} changes symmetrically to {@code floorNumber} change.
     * @param targetScalar scalar for {@code Target} priority
     */
    @Override
    public void move(float targetScalar) {
        makeMove(findBestTarget(targetScalar).orElse(new Target(floorNumber)).floorNumber());
    }

    /**
     * function that finds {@code Target} with the highest priority for this {@code Elevator}.
     * @param targetScalar scalar for {@code Target} priority
     * @return {@code Target} with highest priority
     */
    private Optional<Target> findBestTarget(float targetScalar) {
        float bestPriority = -1F;
        Optional<Target> bestTarget = Optional.empty();
        for (var t : targets) {
            var priority = t.calculatePriority(this, targetScalar);
            if (priority > bestPriority) {
                bestPriority = priority;
                bestTarget = Optional.of(t);
            }
        }
        return bestTarget;
    }

    /**
     * function that performs {@code Elevator} move in direction of {@code destinationFloor}.
     * @param destinationFloor floor number to move to
     */
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

    /**
     * @param relativeDistance distance from this {@code Elevator}
     *                         (positive if higher, or negative if lower)
     * @return whether this elevator is moving opposingly to {@code relativeDistance}
     */
    @Override
    public boolean haveToTurnBack(int relativeDistance) {
        if (this.direction == NO_DIRECTION || this.floorNumber == this.currentDestinationFloor)
            return false;

        if (this.direction == UP && relativeDistance >= 0)
            return false;

        return this.direction != DOWN || relativeDistance > 0;
    }

    /**
     * function that call {@code increaseWaitTimeScalar()} on all {@code Targets} on the list.
     */
    @Override
    public void increaseWaitTimeScalar() {
        targets.forEach(Target::increaseWaitTimeScalar);
    }

    /**
     * utility function that compares priorities.
     * @param target {@code Target} whose priority is to compare
     * @param pickup {@code Pickup} whose priority is to compare
     * @param targetScalar scalar for {@code Target} priority
     * @return whether {@code Targets} priority is higher than {@code Pickups},
     * if both are equal it returns {@code true}
     */
    private boolean isTargetsPriorityHigherThanPickups(Target target, Pickup pickup, float targetScalar) {
        return target.calculatePriority(this, targetScalar) >= pickup.calculatePriority(this);
    }
}
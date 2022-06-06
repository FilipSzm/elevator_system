package proj.elevator.system.model;

import java.util.List;

/**
 * interface for {@code Elevator} that can be used in {@code ElevatorSystem}.
 */
public interface Elevator {

    /**
     * @return status of this {@code Elevator}
     */
    ElevatorStatus status();

    /**
     * function that updates all fields of this {@code Elevator}
     * with field of {@code Elevator} given in param.
     * @param elevator {@code Elevator} to copy parameters from
     */
    void update(Elevator elevator);

    /**
     * function that updates position of this {@code Elevator} to floor with number given as parameter.
     * @param floorNumber number of floor to change position to
     */
    void updatePosition(int floorNumber);

    /**
     * {@code id} getter.
     * @return {@code id}
     */
    int id();

    /**
     * {@code floorNumber} getter.
     * @return {@code floorNumber}
     */
    int floorNumber();

    /**
     * {@code direction} getter.
     * @return {@code direction}
     */
    Direction direction();

    /**
     * {@code currentDestinationFloor} getter.
     * @return {@code currentDestinationFloor}
     */
    int currentDestinationFloor();

    /**
     * {@code targets} getter.
     * @return list of all {@code targets} that this {@code Elevator} is assigned to
     */
    List<Target> targets();

    /**
     * function that adds {@code Target} specified in param to list
     * of all {@code targets} of this {@code Elevator}.
     * @param target new {@code Target} to add
     */
    void addTarget(Target target);

    /**
     * function that deletes all {@code Targets} from this {@code Elevator's}
     * {@code targets} list that are on the same floor this {@code Elevator},
     * if any {@code Target} get deleted direction of this {@code Elevator}
     * is set to {@code NO_DIRECTION} and {@code currentDestinationFloor} to {@code floorNumber}
     */
    void dropOff();

    /**
     * sets {@code direction} of {@code Elevator} to {@code NO_DIRECTION}
     * and {@code currentDestinationFloor} to {@code floorNumber} if {@code Pickup} given
     * in parameter is on the same floor as this {@code Elevator}.
     * @param pickup {@code Pickup} that gets checked if is on the same floor
     */
    void pickUpIfSameFloor(Pickup pickup);

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
    boolean moveUsingPickupOrNot(Pickup pickup, float targetScalar);

    /**
     * function that tries to move this {@code Elevator} to the direction of {@code Target} with the highest priority.
     * if target list is empty it stays in place and changes its {@code direction} to {@code NO_DIRECTION}.
     * Move consist of changing destination floor to {@code floorNumber} of {@code Target} and changing this
     * {@code Elevator's} {@code floorNumber} one value closer to {@code Target}
     * (incrementation, decrementation or not changing if {@code Target} is on the same floor),
     * also {@code direction} of this {@code Elevator} changes symmetrically to {@code floorNumber} change.
     * @param targetScalar scalar for {@code Target} priority
     */
    void move(float targetScalar);

    /**
     * @param relativeDistance distance from this {@code Elevator}
     *                         (positive if higher, or negative if lower)
     * @return whether this elevator is moving opposingly to {@code relativeDistance}
     */
    boolean haveToTurnBack(int relativeDistance);

    /**
     * function that call {@code increaseWaitTimeScalar()} on all {@code Targets} on the list.
     */
    void increaseWaitTimeScalar();
}
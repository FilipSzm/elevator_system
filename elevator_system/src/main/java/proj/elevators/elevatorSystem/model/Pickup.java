package proj.elevators.elevatorSystem.model;

/**
 * {@code Pickup} is an object that represents single request for pickup in {@code ElevatorSystem}.
 */
public class Pickup {

    /**
     * number of floor pickup request comes from.
     */
    final private int floorNumber;
    /**
     * {@code Direction} in which pickup request sender declared wanting to move.
     */
    private Direction direction;
    /**
     * Scalar that scales with amount of steps request is waiting for pickup.
     */
    private int waitTimeScalar;

    /**
     * Constructor that takes {@code floorNumber} and {@code direction}
     * as parameters and initializes all fields ({@code waitTimeScalar} initializes as 1).
     * @param floorNumber param that defines {@code floorNumber} of {@code Pickup}
     * @param direction param that defines {@code direction} of {@code Pickup}
     */
    public Pickup(int floorNumber, Direction direction) {
        this.floorNumber = floorNumber;
        this.direction = direction;
        waitTimeScalar = 1;
    }

    /**
     * {@code floorNumber} getter.
     * @return {@code floorNumber}
     */
    public int floorNumber() {
        return floorNumber;
    }

    /**
     * {@code direction} setter.
     * @param direction new direction
     */
    public void direction(Direction direction) {
        this.direction = direction;
    }

    /**
     * {@code direction} getter.
     * @return {@code direction}
     */
    public Direction direction() {
        return direction;
    }

    /**
     * function that evaluates priority of this {@code Pickup} in relation to {@code Elevator}.
     * @param elevator {@code Elevator} to which priority is evaluated
     * @return priority (non negative)
     */
    public float calculatePriority(Elevator elevator) {
        int distance = floorNumber - elevator.floorNumber();

        if (elevator.haveToTurnBack(distance))
            return 0;

        distance = Math.abs(distance);

        var priority = (float) waitTimeScalar / (distance + 1F);
        return priority >= 0 ? priority : Float.MAX_VALUE;
    }

    /**
     * function that increments {@code waitTimeScalar} field, to the maximum value of {@code 1000}.
     */
    public void increaseWaitTimeScalar() {
        if (waitTimeScalar < 1000) waitTimeScalar++;
    }
}

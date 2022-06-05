package proj.elevators.elevatorSystem.model;

/**
 * {@code Target} is an object that represents single target request
 * for {@code Elevator} in {@code ElevatorSystem}.
 */
public class Target {

    /**
     * static scalar that defines correlation of {@code Target} priorities to {@code Pickup},
     * default value is equal to {@code 8.0}.
     */
    public static float targetScalar = 8F;

    /**
     * number of floor target request comes from.
     */
    final private int floorNumber;

    /**
     * Scalar that scales with amount of steps target request is waiting.
     */
    private int waitTimeScalar;

    /**
     * Constructor that takes {@code floorNumber} as parameter and initializes
     * all fields ({@code waitTimeScalar} initializes as 1).
     * @param floorNumber param that defines {@code floorNumber} of {@code Target}
     */
    public Target(int floorNumber) {
        this.floorNumber = floorNumber;
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
     * function that evaluates priority of this {@code Target} in relation to {@code Elevator}.
     * @param elevator {@code Elevator} to which priority is evaluated
     * @return priority (non negative)
     */
    public float calculatePriority(Elevator elevator) {
        int distance = floorNumber - elevator.floorNumber();

        if (elevator.haveToTurnBack(distance))
            return 0;

        distance = Math.abs(distance);

        var priority = (float) waitTimeScalar * targetScalar / (distance + 1F);
        return priority >= 0F ? priority : Float.MAX_VALUE;
    }

    /**
     * function that increments {@code waitTimeScalar} field, to the maximum value of {@code 1000}.
     */
    public void increaseWaitTimeScalar() {
        if (waitTimeScalar < 1000) waitTimeScalar++;
    }
}

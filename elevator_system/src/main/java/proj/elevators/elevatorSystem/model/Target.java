package proj.elevators.elevatorSystem.model;

import proj.elevators.elevatorSystem.model.param.TargetResponse;

/**
 * {@code Target} is an object that represents single target request
 * for {@code Elevator} in {@code ElevatorSystem}.
 */
public class Target {

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
     * all fields constructor.
     * @param floorNumber floorNumber of {@code Target}
     * @param waitTimeScalar waitTimeScalar of {@code Target}
     */
    private Target(int floorNumber, int waitTimeScalar) {
        this.floorNumber = floorNumber;
        this.waitTimeScalar = waitTimeScalar;
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
     * @param targetScalar scalar for {@code Target} priority
     * @return priority (non negative)
     */
    public float calculatePriority(Elevator elevator, float targetScalar) {
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

    /**
     * @return new {@code TargetResponse} with parameters same as this {@code Target}.
     */
    public TargetResponse toTargetResponse(int elevatorId) {
        return new TargetResponse(elevatorId, floorNumber, waitTimeScalar);
    }

    /**
     * constructs new {@code Target} from {@code TargetResponse}.
     * @param targetResponse {@code TargetResponse} from which to construct
     * @return new {@code Target}
     */
    public static Target fromTargetResponse(TargetResponse targetResponse) {
        return new Target(targetResponse.floorNumber(), targetResponse.waitTimeScalar());
    }
}

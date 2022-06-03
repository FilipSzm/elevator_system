package proj.elevators.elevator_system.model;

public class Target {

    final public static float targetScalar = 4F;

    final private int floorNumber;
    private int waitTimeScalar;

    public Target(int floorNumber) {
        this.floorNumber = floorNumber;
        waitTimeScalar = 1;
    }

    public int floorNumber() {
        return floorNumber;
    }

    public float calculatePriority(Elevator elevator) {
        int distance = floorNumber - elevator.floorNumber();

        if (elevator.haveToTurnBack(distance))
            return 0;

        var priority = (float) waitTimeScalar * targetScalar / distance;
        return priority >= 0F ? priority : Float.MAX_VALUE;
    }

    public void increaseWaitTimeScalar() {
        if (waitTimeScalar < 1000) waitTimeScalar++;
    }
}

package proj.elevators.elevatorSystem.model;

public class Target {

    final public static float targetScalar = 8F;

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

        distance = Math.abs(distance);

        var priority = (float) waitTimeScalar * targetScalar / (distance + 1F);
        return priority >= 0F ? priority : Float.MAX_VALUE;
    }

    public void increaseWaitTimeScalar() {
        if (waitTimeScalar < 1000) waitTimeScalar++;
    }
}

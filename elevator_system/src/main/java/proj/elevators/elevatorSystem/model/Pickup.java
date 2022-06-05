package proj.elevators.elevatorSystem.model;

public class Pickup {

    final private int floorNumber;
    private Direction direction;
    private int waitTimeScalar;

    public Pickup(int floorNumber, Direction direction) {
        this.floorNumber = floorNumber;
        this.direction = direction;
        waitTimeScalar = 1;
    }

    public int floorNumber() {
        return floorNumber;
    }

    public void direction(Direction direction) {
        this.direction = direction;
    }

    public Direction direction() {
        return direction;
    }

    public float calculatePriority(Elevator elevator) {
        int distance = floorNumber - elevator.floorNumber();

        if (elevator.haveToTurnBack(distance))
            return 0;

        distance = Math.abs(distance);

        var priority = (float) waitTimeScalar / (distance + 1F);
        return priority >= 0 ? priority : Float.MAX_VALUE;
    }

    public void increaseWaitTimeScalar() {
        if (waitTimeScalar < 1000) waitTimeScalar++;
    }
}

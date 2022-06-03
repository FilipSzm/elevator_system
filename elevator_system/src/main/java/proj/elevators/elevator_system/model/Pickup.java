package proj.elevators.elevator_system.model;

import static proj.elevators.elevator_system.model.Direction.areDirectionsSame;

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

    public float calculatePriority(Elevator elevator) {
        int distance = floorNumber - elevator.floorNumber();

        if (elevator.haveToTurnBack(distance))
            return 0;

        float sameDirectionScalar = areDirectionsSame(this.direction, elevator.direction()) ? 2F : 1F;

        var priority = (float) waitTimeScalar * sameDirectionScalar / distance;
        return priority >= 0 ? priority : Float.MAX_VALUE;
    }

    public void increaseWaitTimeScalar() {
        if (waitTimeScalar < 1000) waitTimeScalar++;
    }
}

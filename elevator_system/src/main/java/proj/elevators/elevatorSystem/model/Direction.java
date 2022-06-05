package proj.elevators.elevatorSystem.model;

/**
 * {@code Direction} is enum that represents both,
 * direction in which {@code Elevator} is heading and direction of
 * {@code Pickup} that is assigned in {@code ElevatorSystem}
 * (with condition what it can not be set to {@code NO_DIRECTION}).
 */
public enum Direction {
    UP,
    DOWN,
    /**
     * represents direction of {@code Elevator} that is not moving.
     */
    NO_DIRECTION;

    /**
     * checks if directions given as parameters are, with condition
     * that if at least one of them is equal to {@code NO_DIRECTION}
     * function returns {@code true}.
     * @param dirL {@code Direction}
     * @param dirR {@code Direction}
     * @return true if direction are evaluated as the same,
     * false otherwise
     */
    public static boolean areDirectionsSame(Direction dirL, Direction dirR) {
        if (dirL == NO_DIRECTION || dirR == NO_DIRECTION)
            return true;

        if (dirL == UP && dirR == UP)
            return true;

        return dirL == DOWN && dirR == DOWN;
    }
}

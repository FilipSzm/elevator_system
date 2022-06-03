package proj.elevators.elevator_system.model;

public enum Direction {
    UP,
    DOWN,
    NO_DIRECTION;

    public static boolean areDirectionsSame(Direction dirL, Direction dirR) {
        if (dirL == NO_DIRECTION || dirR == NO_DIRECTION)
            return true;

        if (dirL == UP && dirR == UP)
            return true;

        return dirL == DOWN && dirR == DOWN;
    }
}

package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

/**
 * exception that is thrown when there is attempt to create or modify
 * {@code Elevator} to have the same {@code id} as other already existing in {@code ElevatorSystem}.
 */
public class DuplicateElevatorIdException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "4",
                "Elevator with this id already exists."
        );
    }
}
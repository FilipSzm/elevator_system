package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

/**
 * {@code Exception} that is thrown when trying to create {@code ElevatorSystem} with incorrect amount of {@code Elevators}.
 */
public class IncorrectNumberOfElevatorsException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "1",
                "Number of elevators must be between 1 to 16."
        );
    }
}


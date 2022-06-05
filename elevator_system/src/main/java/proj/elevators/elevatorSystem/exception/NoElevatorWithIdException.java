package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

/**
 * {@code Exception} that is thrown when there is no {@code Elevator}
 * with corresponding {@code id} in {@code ElevatorSystem}.
 */
public class NoElevatorWithIdException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "3",
                "No elevator found with given id."
        );
    }
}


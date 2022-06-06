package proj.elevator.system.exception;

import proj.elevator.system.model.ExceptionInfo;

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


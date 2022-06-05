package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

public class NoElevatorWithIdException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "3",
                "No elevator found with given id."
        );
    }
}


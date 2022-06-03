package proj.elevators.elevator_system.exception;

import proj.elevators.elevator_system.model.ExceptionInfo;

public class NoElevatorWithIdException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "3",
                "No elevator found with given id."
        );
    }
}


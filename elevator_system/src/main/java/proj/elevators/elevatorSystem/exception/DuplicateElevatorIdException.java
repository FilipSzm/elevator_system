package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

public class DuplicateElevatorIdException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "4",
                "Elevator with this id already exists."
        );
    }
}
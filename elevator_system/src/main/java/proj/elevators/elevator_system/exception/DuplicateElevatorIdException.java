package proj.elevators.elevator_system.exception;

import proj.elevators.elevator_system.model.ExceptionInfo;

public class DuplicateElevatorIdException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "4",
                "Elevator with this id already exists."
        );
    }
}
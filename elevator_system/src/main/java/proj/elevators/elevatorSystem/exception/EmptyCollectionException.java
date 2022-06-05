package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

public class EmptyCollectionException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "5",
                "Got empty collection when expecting it to have elements."
        );
    }
}

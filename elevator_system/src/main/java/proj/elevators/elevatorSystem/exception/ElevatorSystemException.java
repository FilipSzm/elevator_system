package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

public abstract class ElevatorSystemException extends RuntimeException {
    public abstract ExceptionInfo errorInfo();
}

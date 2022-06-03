package proj.elevators.elevator_system.exception;

import proj.elevators.elevator_system.model.ExceptionInfo;

public abstract class ElevatorSystemException extends RuntimeException {
    public abstract ExceptionInfo errorInfo();
}

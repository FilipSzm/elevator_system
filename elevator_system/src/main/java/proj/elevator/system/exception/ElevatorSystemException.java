package proj.elevator.system.exception;

import proj.elevator.system.model.ExceptionInfo;

/**
 * {@code ElevatorSystemException} is a superclass for all exceptions in {@code elevatorSystem} app.
 */
public abstract class ElevatorSystemException extends RuntimeException {
    /**
     * function that returns {@code ExceptionInfo} describing this exception
     * @return {@code ExceptionInfo} of this exception
     */
    public abstract ExceptionInfo errorInfo();
}

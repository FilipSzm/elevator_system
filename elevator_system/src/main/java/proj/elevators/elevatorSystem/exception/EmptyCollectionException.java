package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

/**
 * {@code Exception} that is thrown when program encounters empty
 * {@code Collection} when expecting it to have at least on element.
 */
public class EmptyCollectionException extends ElevatorSystemException {
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "5",
                "Got empty collection when expecting it to have elements."
        );
    }
}

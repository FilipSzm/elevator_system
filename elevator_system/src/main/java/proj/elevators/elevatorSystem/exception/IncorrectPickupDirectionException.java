package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

/**
 * {@code Exception} that is thrown when there is attempt to create or modify
 * {@code Pickup} to have incorrect {@code Direction} parameter.
 */
public class IncorrectPickupDirectionException extends ElevatorSystemException{
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "2",
                "Pickup direction must be set to either 'UP' or 'DOWN'."
        );
    }
}

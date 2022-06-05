package proj.elevators.elevatorSystem.exception;

import proj.elevators.elevatorSystem.model.ExceptionInfo;

public class IncorrectPickupDirectionException extends ElevatorSystemException{
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "2",
                "Pickup direction must be set to either 'UP' or 'DOWN'."
        );
    }
}

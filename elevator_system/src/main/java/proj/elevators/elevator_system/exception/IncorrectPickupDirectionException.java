package proj.elevators.elevator_system.exception;

import proj.elevators.elevator_system.model.ExceptionInfo;

public class IncorrectPickupDirectionException extends ElevatorSystemException{
    @Override
    public ExceptionInfo errorInfo() {
        return ExceptionInfo.of(
                "2",
                "Pickup direction must be set to either 'UP' or 'DOWN'."
        );
    }
}

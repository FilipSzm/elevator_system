package proj.elevators.elevatorSystem.model;

/**
 * {@code ExceptionInfo} is a record that provide information about exceptions from {@code ElevatorSystem}.
 * @param code code that exception throws, unique for every type of exception
 * @param message message providing information about cause of exception
 */
public record ExceptionInfo(String code, String message) {

    /**
     * function that returns {@code ExceptionInfo} such as described in params.
     * @param code code of {@code ExceptionInfo} to return
     * @param message message of {@code ExceptionInfo} to return
     * @return {@code ExceptionInfo} with parameters as described
     */
    public static ExceptionInfo of(String code, String message) {
        return new ExceptionInfo(code, message);
    }
}


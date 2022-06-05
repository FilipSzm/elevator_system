package proj.elevators.elevatorSystem.model;

public record ExceptionInfo(String code, String message) {

    public static ExceptionInfo of(String code, String message) {
        return new ExceptionInfo(code, message);
    }
}


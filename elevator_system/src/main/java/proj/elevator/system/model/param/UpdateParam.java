package proj.elevator.system.model.param;

/**
 * wrapper object for json conversion purposes.
 * @param elevatorId
 * @param elevatorParam
 */
public record UpdateParam(int elevatorId, ElevatorParam elevatorParam) { }

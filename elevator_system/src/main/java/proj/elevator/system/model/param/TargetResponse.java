package proj.elevator.system.model.param;

/**
 * wrapper object for json conversion purposes.
 * @param floorNumber
 * @param waitTimeScalar
 */
public record TargetResponse(int elevatorId, int floorNumber, int waitTimeScalar) { }

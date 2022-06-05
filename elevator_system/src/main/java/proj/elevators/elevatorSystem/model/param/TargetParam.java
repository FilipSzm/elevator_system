package proj.elevators.elevatorSystem.model.param;

/**
 * wrapper object for json conversion purposes.
 * @param elevatorId
 * @param floorNumber
 */
public record TargetParam(int elevatorId, int floorNumber) { }

package proj.elevators.elevatorSystem.model.param;

import proj.elevators.elevatorSystem.model.Elevator;

/**
 * wrapper object for json conversion purposes.
 * @param elevatorId
 * @param elevator
 */
public record UpdateParam(int elevatorId, Elevator elevator) { }

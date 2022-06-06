package proj.elevators.elevatorSystem.model.param;

import proj.elevators.elevatorSystem.model.Direction;

/**
 * wrapper object for json conversion purposes.
 * @param floorNumber
 * @param direction
 * @param waitTimeScalar
 */
public record PickupResponse(int floorNumber, Direction direction, int waitTimeScalar) { }

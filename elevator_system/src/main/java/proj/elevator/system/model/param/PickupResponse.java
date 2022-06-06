package proj.elevator.system.model.param;

import proj.elevator.system.model.Direction;

/**
 * wrapper object for json conversion purposes.
 * @param floorNumber
 * @param direction
 * @param waitTimeScalar
 */
public record PickupResponse(int floorNumber, Direction direction, int waitTimeScalar) { }

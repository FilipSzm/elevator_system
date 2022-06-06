package proj.elevator.system.model.param;

import proj.elevator.system.model.Direction;

/**
 * wrapper object for json conversion purposes.
 * @param floorNumber
 * @param direction
 */
public record PickupParam(int floorNumber, Direction direction) {
}

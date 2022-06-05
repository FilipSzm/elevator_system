package proj.elevators.elevatorSystem.model.param;

import proj.elevators.elevatorSystem.model.Direction;

/**
 * wrapper object for json conversion purposes.
 * @param floorNumber
 * @param direction
 */
public record PickupParam(int floorNumber, Direction direction) {
}

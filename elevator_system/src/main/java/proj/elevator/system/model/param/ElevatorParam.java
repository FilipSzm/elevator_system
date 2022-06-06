package proj.elevator.system.model.param;

import proj.elevator.system.model.Direction;

import java.util.List;

/**
 * wrapper object for json conversion purposes.
 * @param id
 * @param floorNumber
 * @param direction
 * @param currentDestinationFloor
 * @param targets
 */
public record ElevatorParam(
        int id, int floorNumber,
        Direction direction,
        int currentDestinationFloor,
        List<TargetResponse> targets
) { }

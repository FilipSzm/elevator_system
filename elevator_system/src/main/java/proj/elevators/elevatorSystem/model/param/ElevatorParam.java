package proj.elevators.elevatorSystem.model.param;

import proj.elevators.elevatorSystem.model.Direction;
import proj.elevators.elevatorSystem.model.Target;

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

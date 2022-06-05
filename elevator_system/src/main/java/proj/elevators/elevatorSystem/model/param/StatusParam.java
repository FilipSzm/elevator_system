package proj.elevators.elevatorSystem.model.param;

import proj.elevators.elevatorSystem.model.ElevatorStatus;
import proj.elevators.elevatorSystem.model.Pickup;
import proj.elevators.elevatorSystem.model.Target;

import java.util.List;

/**
 * wrapper object for json conversion purposes.
 * @param elevators
 * @param pickups
 * @param targets
 */
public record StatusParam(List<ElevatorStatus> elevators, List<Pickup> pickups, List<Target> targets) { }

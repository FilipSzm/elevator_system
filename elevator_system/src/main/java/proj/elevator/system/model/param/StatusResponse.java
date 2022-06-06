package proj.elevator.system.model.param;

import proj.elevator.system.model.ElevatorStatus;

import java.util.List;

/**
 * wrapper object for json conversion purposes.
 * @param elevators
 * @param pickups
 * @param targets
 */
public record StatusResponse(List<ElevatorStatus> elevators, List<PickupResponse> pickups, List<TargetResponse> targets) { }

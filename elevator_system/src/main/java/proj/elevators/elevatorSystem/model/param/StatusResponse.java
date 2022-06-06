package proj.elevators.elevatorSystem.model.param;

import proj.elevators.elevatorSystem.model.ElevatorStatus;

import java.util.List;

/**
 * wrapper object for json conversion purposes.
 * @param elevators
 * @param pickups
 * @param targets
 */
public record StatusResponse(List<ElevatorStatus> elevators, List<PickupResponse> pickups, List<TargetResponse> targets) { }

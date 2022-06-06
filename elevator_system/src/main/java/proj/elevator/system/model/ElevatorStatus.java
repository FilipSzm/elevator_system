package proj.elevator.system.model;

/**
 * most essential parameters of elevator.
 * @param id id of elevator
 * @param currentFloorNumber number of floor on which elevator is currently located
 * @param destinationFloorNumber floor number to which the elevator is currently headed
 *                               (same as {@code currentFloorNumber} if elevator is not in motion)
 */
public record ElevatorStatus(
        int id,
        int currentFloorNumber,
        int destinationFloorNumber
) { }

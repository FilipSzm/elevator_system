package proj.elevators.elevator_system.model;

public record ElevatorStatus(
        int id,
        int currentFloorNumber,
        int destinationFloorNumber
) { }

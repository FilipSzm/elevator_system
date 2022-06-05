package proj.elevators.elevatorSystem.service;

import org.springframework.stereotype.Service;
import proj.elevators.elevatorSystem.model.ElevatorSystem;
import proj.elevators.elevatorSystem.system.ElevatorSystemImpl;

@Service
public class ElevatorSystemService {

    ElevatorSystem elevatorSystem;

    public ElevatorSystemService() {
        elevatorSystem = init(10);
    }

    private ElevatorSystem init(int numberOfElevators) {
        return new ElevatorSystemImpl(numberOfElevators);
    }


}

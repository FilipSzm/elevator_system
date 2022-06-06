package proj.elevator.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.elevator.system.exception.ElevatorSystemException;
import proj.elevator.system.model.param.*;
import proj.elevator.system.service.ElevatorSystemService;
import proj.elevator.system.system.ElevatorImpl;

@RestController
@RequestMapping("/api/system")
public class ElevatorSystemController {

    private static final ResponseString SUCCESS = new ResponseString("success");
    private final Logger logger;
    private final ElevatorSystemService service;

    @Autowired
    public ElevatorSystemController(ElevatorSystemService service) {
        this.service = service;
        logger = LoggerFactory.getLogger(ElevatorSystemController.class);
    }

    @PatchMapping("/init")
    public ResponseEntity<?> init(@RequestBody InitParam initParam) {
        try {
            service.init(initParam.numberOfElevators(), initParam.targetScalar());

            logger.info(
                    "Initialized elevator system with {} elevators and target scalar equal to: {}",
                    initParam.numberOfElevators(),
                    initParam.targetScalar()
            );
        } catch (ElevatorSystemException e) {
            logger.info(
                    "Could not initialize elevator system with {} elevators and target scalar equal to: {}",
                    initParam.numberOfElevators(),
                    initParam.targetScalar()
            );
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PutMapping("/pickup")
    public ResponseEntity<?> pickup(@RequestBody PickupParam pickupParam) {
        try {
            service.pickup(pickupParam.floorNumber(), pickupParam.direction());
            logger.info(
                    "Added pickup request to floor number {} and direction: {}",
                    pickupParam.floorNumber(),
                    pickupParam.direction()
            );
        } catch (ElevatorSystemException e) {
            logger.info(
                    "Could not add pickup request to floor number {} and direction: {}",
                    pickupParam.floorNumber(),
                    pickupParam.direction()
            );
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PutMapping("/target")
    public ResponseEntity<?> target(@RequestBody TargetParam targetParam) {
        try {
            service.target(targetParam.elevatorId(), targetParam.floorNumber());
            logger.info(
                    "Added target request for elevator with id {} to floor number {}",
                    targetParam.elevatorId(),
                    targetParam.floorNumber()
            );
        } catch (ElevatorSystemException e) {
            logger.info(
                    "Could not add target request for elevator with id {} to floor number {}",
                    targetParam.elevatorId(),
                    targetParam.floorNumber()
            );
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateParam updateParam) {
        try {
            service.update(
                    updateParam.elevatorId(),
                    ElevatorImpl.fromElevatorParam(updateParam.elevatorParam())
            );
            logger.info(
                    "Updated elevator with id {} with parameters: {}",
                    updateParam.elevatorId(),
                    updateParam.elevatorParam()
            );
        } catch (ElevatorSystemException e) {
            logger.info(
                    "Could not update elevator with id {} with parameters: {}",
                    updateParam.elevatorId(),
                    updateParam.elevatorParam()
            );
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PatchMapping("/step")
    public ResponseEntity<?> step() {
        try {
            service.step();
            logger.info("Successfully performed step");
        } catch (ElevatorSystemException e) {
            logger.info("Could not perform step");
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        StatusResponse statusResponse;
        try {
            statusResponse = service.status();
            logger.info("Successfully returned status");
        } catch (ElevatorSystemException e) {
            logger.info("Could not return status");
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(statusResponse);
    }
}

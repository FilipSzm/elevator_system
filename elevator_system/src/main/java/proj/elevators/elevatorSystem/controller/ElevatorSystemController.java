package proj.elevators.elevatorSystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.elevators.elevatorSystem.exception.ElevatorSystemException;
import proj.elevators.elevatorSystem.model.param.*;
import proj.elevators.elevatorSystem.service.ElevatorSystemService;

@RestController
@RequestMapping("/api/system")
public class ElevatorSystemController {

    final private Logger logger;
    final private ElevatorSystemService service;

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
                    "Initialized elevator system with " +
                            initParam.numberOfElevators() +
                            " elevators and target scalar equal to: " +
                            initParam.targetScalar()
            );
        } catch (ElevatorSystemException e) {
            logger.info(
                    "Could not initialize elevator system with " +
                            initParam.numberOfElevators() +
                            " elevators and target scalar equal to: " +
                            initParam.targetScalar()
            );
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body("success");
    }

    @PutMapping("/pickup")
    public ResponseEntity<?> pickup(@RequestBody PickupParam pickupParam) {
        try {
            service.pickup(pickupParam.floorNumber(), pickupParam.direction());
            logger.info(
                    "Added pickup request to floor number " +
                            pickupParam.floorNumber() +
                            " and direction: " +
                            pickupParam.direction()
            );
        } catch (ElevatorSystemException e) {
            logger.info(
                    "Could not add pickup request to floor number " +
                            pickupParam.floorNumber() +
                            " and direction: " +
                            pickupParam.direction()
            );
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body("success");
    }

    @PutMapping("/target")
    public ResponseEntity<?> target(@RequestBody TargetParam targetParam) {
        try {
            service.target(targetParam.elevatorId(), targetParam.floorNumber());
            logger.info(
                    "Added target request for elevator with id " +
                            targetParam.elevatorId() +
                            " to floor number " +
                            targetParam.floorNumber()
            );
        } catch (ElevatorSystemException e) {
            logger.info(
                    "Could not add target request for elevator with id " +
                            targetParam.elevatorId() +
                            " to floor number " +
                            targetParam.floorNumber()
            );
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body("success");
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateParam updateParam) {
        try {
            service.update(updateParam.elevatorId(), updateParam.elevator());
            logger.info(
                    "Updated elevator with id " +
                            updateParam.elevatorId() +
                            " with parameters: " +
                            updateParam.elevator()
            );
        } catch (ElevatorSystemException e) {
            logger.info(
                    "Could not update elevator with id " +
                            updateParam.elevatorId() +
                            " with parameters: " +
                            updateParam.elevator()
            );
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body("success");
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
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        StatusParam statusParam;
        try {
            statusParam = service.status();
            logger.info("Successfully returned status");
        } catch (ElevatorSystemException e) {
            logger.info("Could not return status");
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(statusParam);
    }
}

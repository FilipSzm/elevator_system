package proj.elevators.elevator_system.controller;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.elevators.elevator_system.exception.ElevatorSystemException;
import proj.elevators.elevator_system.model.Direction;
import proj.elevators.elevator_system.model.ElevatorStatus;
import proj.elevators.elevator_system.model.templates.DirectionParam;
import proj.elevators.elevator_system.model.templates.FloorNumberParam;
import proj.elevators.elevator_system.service.SystemVisualizationService;

import java.util.List;

@RestController
@RequestMapping("/api/vis")
@CrossOrigin(origins = "http://localhost:3000")
public class SystemVisualizationController {

    final private Logger logger;
    final private SystemVisualizationService service;

    @Autowired
    public SystemVisualizationController(SystemVisualizationService service) {
        this.service = service;
        logger = LoggerFactory.getLogger(SystemVisualizationController.class);
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        List<ElevatorStatus> statusList;
        try {
            statusList = service.status();
            logger.info("Returned status");
        } catch (ElevatorSystemException e) {
            logger.info("Could not return status.", e);
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(statusList);
    }

    @PatchMapping("/update/{elevatorId}")
    public ResponseEntity<?> update(@PathVariable int elevatorId, @RequestBody FloorNumberParam floorNumberParam) {
        try {
            service.update(elevatorId, Integer.parseInt(floorNumberParam.floorNumber()));
            logger.info("Updated position of elevator with id:" + elevatorId + ", to: " + floorNumberParam.floorNumber());
        } catch (ElevatorSystemException e) {
            logger.info("Could not update position of elevator with id: " + elevatorId, e);
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body("success");
    }

    @PutMapping("/pickup/{floorNumber}")
    public ResponseEntity<?> pickup(@PathVariable int floorNumber, @RequestBody DirectionParam directionParam) {
        try {
            service.pickup(floorNumber, directionParam.direction());
            logger.info(
                    "Added pickup request for floor number: " + floorNumber + ", with direction: " +
                            directionParam.direction().name());
        } catch (ElevatorSystemException e) {
            logger.info("Could not add pickup request for floor number: " + floorNumber, e);
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body("success");
    }

    @PutMapping("/target/{elevatorId}")
    public ResponseEntity<?> target(@PathVariable int elevatorId, @RequestBody FloorNumberParam floorNumberParam) {
        try {
            service.target(elevatorId, Integer.parseInt(floorNumberParam.floorNumber()));
            logger.info("Added target request for elevator with id: " + elevatorId + ", to floor number: " + floorNumberParam.floorNumber());
        } catch (ElevatorSystemException e) {
            logger.info("Could not add target request for elevator with id: " + elevatorId, e);
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body("success");
    }

    @PutMapping("/step")
    public ResponseEntity<?> step() {
        try {
            service.step();
            logger.info("Successfully performed step");
        } catch (ElevatorSystemException e) {
            logger.info("Could not perform step", e);
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body("success");
    }
}

package proj.elevator.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.elevator.system.model.ElevatorStatus;
import proj.elevator.system.model.param.FloorNumberParam;
import proj.elevator.system.model.param.ResponseString;
import proj.elevator.system.service.SystemVisualizationService;
import proj.elevator.system.exception.ElevatorSystemException;
import proj.elevator.system.model.param.DirectionParam;

import java.util.List;

@RestController
@RequestMapping("/api/vis")
@CrossOrigin(origins = "http://localhost:3000")
public class SystemVisualizationController {

    private static final ResponseString SUCCESS = new ResponseString("success");
    private final Logger logger;
    private final SystemVisualizationService service;

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
            logger.info("Could not return status.");
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(statusList);
    }

    @PatchMapping("/update/{elevatorId}")
    public ResponseEntity<?> update(@PathVariable int elevatorId, @RequestBody FloorNumberParam floorNumberParam) {
        try {
            var floorNumber = Integer.parseInt(floorNumberParam.floorNumber());
            service.update(elevatorId, floorNumber);
            logger.info("Updated position of elevator with id: {}, to: {}", elevatorId, floorNumber);
        } catch (ElevatorSystemException e) {
            logger.info("Could not update position of elevator with id: {}", elevatorId);
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PutMapping("/pickup/{floorNumber}")
    public ResponseEntity<?> pickup(@PathVariable int floorNumber, @RequestBody DirectionParam directionParam) {
        try {
            var direction = directionParam.direction();
            service.pickup(floorNumber, direction);
            logger.info(
                    "Added pickup request for floor number: {}, with direction: {}",
                    floorNumber,
                    direction
            );
        } catch (ElevatorSystemException e) {
            logger.info("Could not add pickup request for floor number: {}", floorNumber);
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

    @PutMapping("/target/{elevatorId}")
    public ResponseEntity<?> target(@PathVariable int elevatorId, @RequestBody FloorNumberParam floorNumberParam) {
        try {
            var floorNumber = Integer.parseInt(floorNumberParam.floorNumber());
            service.target(elevatorId, floorNumber);
            logger.info(
                    "Added target request for elevator with id: {}, to floor number: {}",
                    elevatorId,
                    floorNumber
            );
        } catch (ElevatorSystemException e) {
            logger.info("Could not add target request for elevator with id: {}", elevatorId);
            return ResponseEntity.badRequest().body(e.errorInfo());
        }
        return ResponseEntity.ok().body(SUCCESS);
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
        return ResponseEntity.ok().body(SUCCESS);
    }
}

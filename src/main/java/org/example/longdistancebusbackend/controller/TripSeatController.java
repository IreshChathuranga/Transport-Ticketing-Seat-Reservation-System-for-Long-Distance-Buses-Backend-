package org.example.longdistancebusbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.longdistancebusbackend.Util.APIResponse;
import org.example.longdistancebusbackend.dto.TripSeatDTO;
import org.example.longdistancebusbackend.service.TripSeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v1/tripseat")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class TripSeatController {
    private final TripSeatService tripSeatService;

    @PostMapping("save")
    public ResponseEntity<APIResponse> saveTripSeat(@Valid @RequestBody TripSeatDTO tripSeatDTO){
        log.info("INFO - TripSeat Created");
        log.warn("WARN -  TripSeat Created");
        log.debug("DEBUG -  TripSeat Created");
        log.error("ERROR - TripSeat Created");
        log.trace("TRACE - TripSeat Created");

        tripSeatService.saveTripSeat(tripSeatDTO);
        return new ResponseEntity(
                new APIResponse(
                        200,
                        "TripSeat Created Successfully",
                        null
                ), HttpStatus.CREATED
        );
    }

    @PutMapping("modify")
    public ResponseEntity<APIResponse> modifyTripSeat(@RequestBody TripSeatDTO tripSeatDTO){
        tripSeatService.updateTripSeat(tripSeatDTO);
        return new ResponseEntity(
                new APIResponse(
                        200,
                        "TripSeat Update Successfully",
                        null
                ),HttpStatus.CREATED
        );
    }

    @GetMapping("get")
    public ResponseEntity<APIResponse> getTripSeats(){

        List<TripSeatDTO> tripSeatDTOS = tripSeatService.getAll();
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "Success",
                        tripSeatDTOS
                )
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponse> deleteTripSeat(@PathVariable Integer id) {
        tripSeatService.deleteTripSeat(id);
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "TripSeat deleted successfully",
                        null
                )
        );
    }

    @GetMapping("/available/{tripId}")
    public Map<String, Integer> getAvailableSeats(@PathVariable Integer tripId) {
        int availableSeats = tripSeatService.getAvailableSeatsByTripId(tripId);
        Map<String, Integer> response = new HashMap<>();
        response.put("availableSeats", availableSeats);
        return response;
    }
}

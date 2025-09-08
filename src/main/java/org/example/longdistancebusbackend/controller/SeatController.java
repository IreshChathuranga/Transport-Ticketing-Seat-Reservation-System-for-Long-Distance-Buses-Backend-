package org.example.longdistancebusbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.longdistancebusbackend.Util.APIResponse;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.SeatDTO;
import org.example.longdistancebusbackend.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/seat")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class SeatController {
    private final SeatService seatService;

    @PostMapping("save")
    public ResponseEntity<APIResponse> saveSeat(@Valid @RequestBody SeatDTO seatDTO){
        log.info("INFO - Seat Created");
        log.warn("WARN -  Seat Created");
        log.debug("DEBUG -  Seat Created");
        log.error("ERROR - Seat Created");
        log.trace("TRACE - Seat Created");

        seatService.saveSeat(seatDTO);
        return new ResponseEntity(
                new APIResponse(
                        200,
                        "Seat Created Successfully",
                        null
                ), HttpStatus.CREATED
        );
    }

    @PutMapping("modify")
    public ResponseEntity<APIResponse> modifySeat(@RequestBody SeatDTO seatDTO){
        seatService.updateSeat(seatDTO);
        return new ResponseEntity(
                new APIResponse(
                        200,
                        "Seat Update Successfully",
                        null
                ),HttpStatus.CREATED
        );
    }

    @GetMapping("get")
    public ResponseEntity<APIResponse> getSeats(){

        List<SeatDTO> seatDTOS = seatService.getAll();
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "Success",
                        seatDTOS
                )
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponse> deleteSeat(@PathVariable Integer id) {
        seatService.deleteSeat(id);
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "Seat deleted successfully",
                        null
                )
        );
    }
}

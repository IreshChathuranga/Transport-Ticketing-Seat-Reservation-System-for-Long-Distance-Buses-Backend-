package org.example.longdistancebusbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.longdistancebusbackend.Util.APIResponse;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.ScheduleDTO;
import org.example.longdistancebusbackend.service.BusService;
import org.example.longdistancebusbackend.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/schedule")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("save")
    public ResponseEntity<APIResponse> saveSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO){
        log.info("INFO - Schedule Created");
        log.warn("WARN -  Schedule Created");
        log.debug("DEBUG -  Schedule Created");
        log.error("ERROR - Schedule Created");
        log.trace("TRACE - Schedule Created");

        scheduleService.saveSchedule(scheduleDTO);
        return new ResponseEntity(
                new APIResponse(
                        200,
                        "Schedule Created Successfully",
                        null
                ), HttpStatus.CREATED
        );
    }

    @PutMapping("modify")
    public ResponseEntity<APIResponse> modifySchedule(@RequestBody ScheduleDTO scheduleDTO){
        scheduleService.updateSchedule(scheduleDTO);
        return new ResponseEntity(
                new APIResponse(
                        200,
                        "Schedule Update Successfully",
                        null
                ),HttpStatus.CREATED
        );
    }

    @GetMapping("get")
    public ResponseEntity<APIResponse> getSchedules(){

        List<ScheduleDTO> scheduleDTOS = scheduleService.getAll();
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "Success",
                        scheduleDTOS
                )
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponse> deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "Schedule deleted successfully",
                        null
                )
        );
    }
}

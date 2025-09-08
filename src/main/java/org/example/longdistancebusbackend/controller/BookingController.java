package org.example.longdistancebusbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.longdistancebusbackend.Util.APIResponse;
import org.example.longdistancebusbackend.dto.BookingDTO;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.service.BookingService;
import org.example.longdistancebusbackend.service.BusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/booking")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("save")
    public ResponseEntity<APIResponse> saveBooking(@Valid @RequestBody BookingDTO bookingDTO){
        log.info("INFO - Booking Created");
        log.warn("WARN -  Booking Created");
        log.debug("DEBUG -  Booking Created");
        log.error("ERROR - Booking Created");
        log.trace("TRACE - Booking Created");

        bookingService.saveBooking(bookingDTO);
        return new ResponseEntity(
                new APIResponse(
                        200,
                        "Booking Created Successfully",
                        null
                ), HttpStatus.CREATED
        );
    }

    @PutMapping("modify")
    public ResponseEntity<APIResponse> modifyBooking(@RequestBody BookingDTO bookingDTO){
        bookingService.updateBooking(bookingDTO);
        return new ResponseEntity(
                new APIResponse(
                        200,
                        "Booking Update Successfully",
                        null
                ),HttpStatus.CREATED
        );
    }

    @GetMapping("get")
    public ResponseEntity<APIResponse> getBookings(){

        List<BookingDTO> bookingDTOS = bookingService.getAll();
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "Success",
                        bookingDTOS
                )
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponse> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(
                new APIResponse(
                        200,
                        "Booking deleted successfully",
                        null
                )
        );
    }
}

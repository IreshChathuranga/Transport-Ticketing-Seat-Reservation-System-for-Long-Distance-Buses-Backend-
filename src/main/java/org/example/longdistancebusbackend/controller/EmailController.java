package org.example.longdistancebusbackend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.service.impl.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send-ticket")
    public ResponseEntity<String> sendETicket(@RequestBody EmailTicketRequest request) {
        String subject = "Your E-Ticket - Booking Ref: " + request.getBookingRef();
        String text = String.format("Booking Ref: %s\nRoute: %s\nSeat: %s\nPassenger: %s\nDate: %s",
                request.getBookingRef(), request.getRoute(), request.getSeat(), request.getPassenger(), request.getDate());

        emailService.sendETicketWithQR(request.getEmail(), request.getQrCodeBase64(), subject, text);

        return ResponseEntity.ok("Email sent");
    }

    @Data
    static class EmailTicketRequest {
        private String email;
        private String qrCodeBase64;
        private String bookingRef;
        private String passenger;
        private String route;
        private String seat;
        private String date;
    }
}

package org.example.longdistancebusbackend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.service.impl.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send-ticket")
    public ResponseEntity<String> sendETicket(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String qrCodeBase64 = (String) payload.get("qrCodeBase64");

        String bookingRef = (String) payload.get("bookingRef");
        String passengerName = (String) payload.get("passengerName");
        String nic = (String) payload.get("nic");
        String route = (String) payload.get("route");
        String seat = (String) payload.get("seat");
        String date = (String) payload.get("date");
        String amount = String.valueOf(payload.get("amount"));
        String company = (String) payload.get("company");

        emailService.sendETicketWithQR(email, qrCodeBase64, bookingRef, passengerName, nic, route, seat, date, amount, company);

        return ResponseEntity.ok("E-ticket sent successfully");
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

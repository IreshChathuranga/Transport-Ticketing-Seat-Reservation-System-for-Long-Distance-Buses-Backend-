package org.example.longdistancebusbackend.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.Ticket;
import org.example.longdistancebusbackend.entity.TripSeat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingItemDTO {
    private Integer bookingItemId;

    private Integer bookingId;

    private Integer tripSeatId;

    private String seatNumber;

    private BigDecimal fareAmount;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Integer ticket;
}

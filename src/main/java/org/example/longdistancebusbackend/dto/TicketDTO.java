package org.example.longdistancebusbackend.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.longdistancebusbackend.entity.BookingItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketDTO {
    private Integer ticketId;

    private Integer bookingItemId;

    private String ticketNumber;

    private String qrHash;

    private LocalDateTime issuedAt = LocalDateTime.now();
}

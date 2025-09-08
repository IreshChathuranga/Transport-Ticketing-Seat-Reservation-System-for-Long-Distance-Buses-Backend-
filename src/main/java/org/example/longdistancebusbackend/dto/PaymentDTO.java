package org.example.longdistancebusbackend.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.longdistancebusbackend.entity.Booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    private Integer paymentId;

    private Integer bookingId;

    private BigDecimal amount;

    private String currency;

    private String method;

    private String provider;

    private String providerTxnId;

    private String status;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt = LocalDateTime.now();
}

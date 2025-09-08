package org.example.longdistancebusbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDTO {
    private Integer bookingId;

    private Integer userId;

    private Integer tripId;

    private String bookingRef;

    private String status;

    private BigDecimal totalAmount;

    private String currency;

    private String qrCodeData;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiresAt;
}

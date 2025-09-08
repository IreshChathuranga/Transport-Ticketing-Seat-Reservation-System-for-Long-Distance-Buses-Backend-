package org.example.longdistancebusbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false, foreignKey = @ForeignKey(name = "fk_bookings_user"))
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false, foreignKey = @ForeignKey(name = "fk_bookings_trip"))
    @JsonIgnore
    private Trip trip;

    @Column(name = "booking_ref", nullable = false, length = 20, unique = true)
    private String bookingRef;

    @Column(name = "status", nullable = false, length = 20)
    private String status;
//    PENDING,
//    AWAITING_PAYMENT,
//    CONFIRMED,
//    CANCELLED,
//    EXPIRED,
//    REFUND_PENDING,
//    REFUNDED

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "qr_code_data", length = 255)
    private String qrCodeData;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
}

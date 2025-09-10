package org.example.longdistancebusbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trip_seats")
public class TripSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_seat_id")
    private Integer tripSeatId;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tripseats_trip"))
    @JsonIgnore
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tripseats_seat"))
    @JsonIgnore
    private Seat seat;

    @Column(name = "status", nullable = false, length = 20)
    private String status; //        AVAILABLE, HELD, BOOKED, BLOCKED

    @Column(name = "hold_expires_at")
    private LocalDateTime holdExpiresAt;
}

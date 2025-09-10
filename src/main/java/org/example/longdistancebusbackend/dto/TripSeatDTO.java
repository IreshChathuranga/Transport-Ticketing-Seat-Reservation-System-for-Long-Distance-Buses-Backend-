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
import org.example.longdistancebusbackend.entity.Seat;
import org.example.longdistancebusbackend.entity.Trip;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TripSeatDTO {
    private Integer tripSeatId;

    private Integer tripId;

    private Integer seatId;

    private String status;

    private LocalDateTime holdExpiresAt;
}

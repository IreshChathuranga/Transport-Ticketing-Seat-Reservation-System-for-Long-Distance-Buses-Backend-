package org.example.longdistancebusbackend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.longdistancebusbackend.entity.Operator;
import org.example.longdistancebusbackend.entity.SeatMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatDTO {
    private Integer seatId;
    private Integer seatMap;

    private String seatNumber;

    private Integer rowNo;

    private Integer colNo;

    private String seatType;
}

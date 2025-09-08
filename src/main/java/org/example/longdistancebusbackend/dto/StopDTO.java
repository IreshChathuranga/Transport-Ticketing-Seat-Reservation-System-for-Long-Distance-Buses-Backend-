package org.example.longdistancebusbackend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.longdistancebusbackend.entity.Operator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StopDTO {
    private Integer stopId;

    private String name;

    private Double latitude;

    private Double longitude;
}

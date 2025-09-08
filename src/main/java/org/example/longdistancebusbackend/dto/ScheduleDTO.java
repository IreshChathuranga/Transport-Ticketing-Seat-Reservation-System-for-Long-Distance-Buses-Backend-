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
import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.Route;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDTO {
    private Integer scheduleId;

    private Integer busId;

    private Integer routeId;

    private LocalDate departTime;

    private String daysOfWeek;

    private String active;
}

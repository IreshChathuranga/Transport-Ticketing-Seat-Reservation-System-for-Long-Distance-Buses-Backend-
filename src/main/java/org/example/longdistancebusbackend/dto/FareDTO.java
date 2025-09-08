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
import org.example.longdistancebusbackend.entity.Route;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FareDTO {
    private Integer fareId;

    private Integer routeId;

    private String busType;

    private BigDecimal baseFare;

    private BigDecimal perKmRate;

    private String currency;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;
}

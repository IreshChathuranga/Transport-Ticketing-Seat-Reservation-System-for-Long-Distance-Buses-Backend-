package org.example.longdistancebusbackend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperatorDTO {
    private Integer operatorId;
    private String name;
    private String hotline;
    private String email;
    private String address;
    private String status;
}

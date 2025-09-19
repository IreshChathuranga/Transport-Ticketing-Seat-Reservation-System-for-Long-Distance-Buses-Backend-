package org.example.longdistancebusbackend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Integer userId;
    private String name;
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private String phone;
    private String passwordHash;
    private String role = "USER"; // Optional
}

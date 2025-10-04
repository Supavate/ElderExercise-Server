package com.example.elderexserver.data.staff.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StaffLoginResponse {
    private String token;
    private Integer staffId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String picture;
    private String gender;
    private Integer supervisorId;
    private String message;
}

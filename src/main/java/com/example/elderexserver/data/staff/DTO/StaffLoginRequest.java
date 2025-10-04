package com.example.elderexserver.data.staff.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StaffLoginRequest {
    private String email;
    private String password;
}

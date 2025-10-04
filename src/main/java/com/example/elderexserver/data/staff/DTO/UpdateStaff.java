package com.example.elderexserver.data.staff.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateStaff {
    private Integer id;
    private String first_name;
    private String last_name;
    private Integer gender_id;
    private String date_of_birth;
    private String email;
    private String password;
    private Integer role_id;
    private String picture;
    private String telephone;
}

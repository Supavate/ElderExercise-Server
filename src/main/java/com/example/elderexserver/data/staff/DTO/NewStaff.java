package com.example.elderexserver.data.staff.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewStaff {
    private String firstName;
    private String lastName;
    private Integer gender_id;
    private String birthday;
    private String email;
    private String password;
    private Integer role_id;
    private String picture;
    private String telephone;
}

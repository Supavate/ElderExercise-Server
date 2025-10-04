package com.example.elderexserver.data.staff.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StaffList {
    private Integer id;
    private String picture;
    private String first_name;
    private String last_name;
    private Integer patientCount;
}

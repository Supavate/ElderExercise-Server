package com.example.elderexserver.data.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Amphoe {
    @Id
    private Integer id;
    private String name;
    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "province_id")
    @JsonBackReference
    private Province province;


    @OneToMany(mappedBy = "amphoe")
    @JsonManagedReference
    private Set<District> districts = new LinkedHashSet<>();

}
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
public class District {
    @Id
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "amphoe_id")
    @JsonBackReference
    private Amphoe amphoe;

    @OneToMany
    private Set<Address> addresses = new LinkedHashSet<>();

}
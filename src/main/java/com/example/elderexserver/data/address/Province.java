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
@Table(name = "province")
public class Province {
    @Id
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "province")
    @JsonManagedReference
    private Set<Amphoe> amphoes = new LinkedHashSet<>();

}
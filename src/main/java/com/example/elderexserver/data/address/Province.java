package com.example.elderexserver.data.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "province")
public class Province {
    @Id
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "province")
    @JsonManagedReference
    private Set<Amphoe> amphoes = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Amphoe> getAmphoes() {
        return amphoes;
    }

    public void setAmphoes(Set<Amphoe> amphoes) {
        this.amphoes = amphoes;
    }

}
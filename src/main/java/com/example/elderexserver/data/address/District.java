package com.example.elderexserver.data.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

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

    public Amphoe getAmphoe() {
        return amphoe;
    }

    public void setAmphoe(Amphoe amphoe) {
        this.amphoe = amphoe;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
}
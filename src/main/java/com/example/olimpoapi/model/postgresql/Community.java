package com.example.olimpoapi.model.postgresql;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "Community name cannot be null")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Start date cannot be null")
    @Column(name = "start_date")
    private Date startDate;

    @NotNull(message = "Neighborhood cannot be null")
    @Column(name = "neighborhood")
    private String neighborhood;

    @NotNull(message = "Image cannot be null")
    @Column(name = "image")
    private String imageUrl;

    public Community() {
    }

    public Community(UUID id, String name, Date startDate, String neighborhood, String imageUrl) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.neighborhood = neighborhood;
        this.imageUrl = imageUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

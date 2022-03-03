package com.faraf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_food")
public class FoodPost {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "origin_country")
    private String originCountry;

    @Column(name = "time_required")
    private int timeRequired;

    @Column(name = "is_vegan_food")
    private boolean isVeganFood;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "fp_fid", insertable = false, updatable = false)
    private User user;

    public FoodPost() {
    }

    public FoodPost(Long id, String name, String description, String originCountry, int timeRequired, boolean isVeganFood, LocalDateTime createdAt, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.originCountry = originCountry;
        this.timeRequired = timeRequired;
        this.isVeganFood = isVeganFood;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public int getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(int timeRequired) {
        this.timeRequired = timeRequired;
    }

    public boolean isVeganFood() {
        return isVeganFood;
    }

    public void setVeganFood(boolean veganFood) {
        isVeganFood = veganFood;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FoodPost{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", originCountry='" + originCountry + '\'' +
                ", timeRequired=" + timeRequired +
                ", isVeganFood=" + isVeganFood +
                ", createdAt=" + createdAt +
                '}';
    }
}

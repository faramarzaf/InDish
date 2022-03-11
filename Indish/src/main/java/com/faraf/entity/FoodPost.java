package com.faraf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime created_date;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modified_date;

    @ManyToOne
    @JoinColumn(name = "fp_fid",referencedColumnName = "id")
    private User user;

    public FoodPost() {
    }

    public FoodPost(Long id, String name, String description, String originCountry, int timeRequired, boolean isVeganFood, LocalDateTime created_date,LocalDateTime modified_date, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.originCountry = originCountry;
        this.timeRequired = timeRequired;
        this.isVeganFood = isVeganFood;
        this.created_date = created_date;
        this.modified_date = modified_date;
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

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDateTime modified_date) {
        this.modified_date = modified_date;
    }

   // @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodPost foodPost = (FoodPost) o;

        if (timeRequired != foodPost.timeRequired) return false;
        if (isVeganFood != foodPost.isVeganFood) return false;
        if (id != null ? !id.equals(foodPost.id) : foodPost.id != null) return false;
        if (name != null ? !name.equals(foodPost.name) : foodPost.name != null) return false;
        if (description != null ? !description.equals(foodPost.description) : foodPost.description != null)
            return false;
        if (originCountry != null ? !originCountry.equals(foodPost.originCountry) : foodPost.originCountry != null)
            return false;
        if (created_date != null ? !created_date.equals(foodPost.created_date) : foodPost.created_date != null)
            return false;
        if (modified_date != null ? !modified_date.equals(foodPost.modified_date) : foodPost.modified_date != null)
            return false;
        return user != null ? user.equals(foodPost.user) : foodPost.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (originCountry != null ? originCountry.hashCode() : 0);
        result = 31 * result + timeRequired;
        result = 31 * result + (isVeganFood ? 1 : 0);
        result = 31 * result + (created_date != null ? created_date.hashCode() : 0);
        result = 31 * result + (modified_date != null ? modified_date.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
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
                ", created_date=" + created_date +
                ", modified_date=" + modified_date +
                ", user=" + user +
                '}';
    }
}

package com.faraf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "t_user_profile")
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserProfile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "userProfile")
    private User user;

    public UserProfile(String bio, String city, String country) {
        this.bio = bio;
        this.city = city;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}

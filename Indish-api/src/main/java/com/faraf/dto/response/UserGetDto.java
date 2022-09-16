package com.faraf.dto.response;


import com.faraf.dto.RoleDto;

import java.time.LocalDateTime;
import java.util.Set;


public class UserGetDto {

    private Long id;
    private String userName;
    private String email;
    private String country;
    private String city;
    private String userPassword;
    private String bio;
    private String avatar;
    private Set<RoleDto> roles;
    private LocalDateTime create_date;
    private LocalDateTime modified_date;
    private Boolean enabled;

    public UserGetDto() {
    }

    public UserGetDto(Long id, String userName, String email, String country, String city, String userPassword, String bio, String avatar, Set<RoleDto> roles, LocalDateTime create_date, LocalDateTime modified_date, Boolean enabled) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.country = country;
        this.city = city;
        this.userPassword = userPassword;
        this.bio = bio;
        this.avatar = avatar;
        this.roles = roles;
        this.create_date = create_date;
        this.modified_date = modified_date;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDateTime modified_date) {
        this.modified_date = modified_date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UserGetDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", bio='" + bio + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                ", create_date=" + create_date +
                ", modified_date=" + modified_date +
                ", enabled=" + enabled +
                '}';
    }
}

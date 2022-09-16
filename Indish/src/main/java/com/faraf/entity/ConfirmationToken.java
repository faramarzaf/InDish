package com.faraf.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_confirmation_token")
public class ConfirmationToken {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;


    public ConfirmationToken() {
    }

    public ConfirmationToken(Long id, String confirmationToken, LocalDateTime createdDate, User user) {
        this.id = id;
        this.confirmationToken = confirmationToken;
        this.createdDate = createdDate;
        this.user = user;
    }


    public ConfirmationToken(User user) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.confirmationToken = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

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

        ConfirmationToken that = (ConfirmationToken) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (confirmationToken != null ? !confirmationToken.equals(that.confirmationToken) : that.confirmationToken != null)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (confirmationToken != null ? confirmationToken.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConfirmationToken{" +
                "id=" + id +
                ", confirmationToken='" + confirmationToken + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                '}';
    }
}

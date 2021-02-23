package com.Assignment.Restaurant.entity;


import com.Assignment.Restaurant.validation.ValidEgMobile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "admin", "reservations"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotNull(message = "is required")
    @Size(min = 4, message = "is required")
    @Column(name = "name")
    private String name;

    @NotNull(message = "is required")
    @Size(min = 4, message = "is required")
    @Email
    @Column(name = "email")
    private String email;

    @NotNull(message = "is required")
    @Size(min = 4, message = "is required")
    @Column(name = "password")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 11, message = "is required")
    @ValidEgMobile
    @Column(name = "mobile")
    private String mobile;

    @Column(name = "is_admin")
    private boolean admin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    public User() {
    }

    public User(String name, String email, String password, String mobile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.admin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof User))
            return false;

        User anotherUser = (User) obj;

        return this.id == anotherUser.id && this.email.equals(anotherUser.email) && this.mobile.equals(anotherUser.mobile);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", admin=" + admin +
                '}';
    }
}

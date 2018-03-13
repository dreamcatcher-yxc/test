package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name="T_USER")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonView(Views.Detail.class)
    private Long id;

    @Column(nullable = false, unique = true)
    @JsonView(Views.Summary.class)
    private String username;

    @Column(nullable = false)
    private String password = "123456";

    @Column(nullable = false)
    @JsonView(Views.Detail.class)
    private String gender = "m";

    @Column(unique = true)
    @JsonView(Views.Summary.class)
    private String email;

    @Column(unique = true)
    @JsonView(Views.Detail.class)
    private String phone;

    @OneToOne(optional=false,cascade=CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
    @JsonView(Views.Detail.class)
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }
}

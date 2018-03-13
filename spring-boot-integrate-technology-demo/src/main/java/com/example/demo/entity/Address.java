package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "T_ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String country;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

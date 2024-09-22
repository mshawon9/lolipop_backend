package com.lolipop.pos.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String address1;
    private String address2;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;

}

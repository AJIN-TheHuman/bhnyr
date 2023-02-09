package com.example.myapp.ds;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter@Setter
@ToString
public class Address implements Serializable {
    private String zipCode;
    private String streetName;
    private String phoneNumber;
    private String email;
}

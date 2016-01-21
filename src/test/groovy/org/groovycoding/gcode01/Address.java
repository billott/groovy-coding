package org.groovycoding.gcode01;

/*
This code looks just like Java and it is...
It is the same for both languages.
*/

public class Address {

    private String street;
    private String city;
    private String state;
    private String zipCode;

    public Address(String street, String city, String state, String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() { return state; }

    public String getZipCode() {
        return zipCode;
    }
}

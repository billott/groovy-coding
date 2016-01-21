package org.groovycoding.gcode01;

/*
This code looks just like Java and it is...
It is the same for both languages.
*/

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private Address address;

    public User(String firstName, String lastName, String username, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public Address getAddress() {
        return address;
    }
}

package org.groovycoding.gcode03;
import java.io.Serializable;

public class JavaDuke implements Serializable {

    private String firstName;
    private String lastName;
    private final String ssn;

    public JavaDuke(String firstName, String lastName, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }

    // ------------ START EDITING HERE ----------------------
    public String getFirstName() {
        return firstName;
    }
    // ------------ STOP EDITING HERE  ----------------------

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Read only
    public String getSsn() {
        return ssn;
    }
}

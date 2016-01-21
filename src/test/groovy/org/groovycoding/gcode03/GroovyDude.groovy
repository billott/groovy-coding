package org.groovycoding.gcode03

class GroovyDude {
    String firstName
    String lastName
    final String ssn // read only

    GroovyDude(String firstName, String lastName, String ssn) {
        this.firstName = firstName
        this.lastName = lastName
        this.ssn = ssn
    }
}

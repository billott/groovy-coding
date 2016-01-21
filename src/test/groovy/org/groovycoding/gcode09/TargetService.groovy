package org.groovycoding.gcode09

class TargetService {

    int nukeCount = 0

    String targetCity(String username, String city) {
        nukeCount++
        return "$username has ordered to nuke $city. Nuking..."
    }

}

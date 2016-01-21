package org.groovycoding.gcode13

import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class gcode13 extends Specification {

    def "introduction to Spock"() {
        // Spock uses Groovy to improve unit tests
        // Spock requires statement block called expect.
        expect:
        def circum = { r -> 2 * Math.PI * r }
        def r = 1.div(Math.PI)
        circum(r) == 2
    }

    def "testing magic formula"(a, b, expectedResult) {
        // where block executes the test 5 times with 5 different parameters.
        expect:
        magicFormula(a, b) == expectedResult

        where:
        a   | b | expectedResult
        1   | 1 | 3
        2   | 2 | 6
        3   | 3 | 11
        3   | 0 | 2
        'a' | 2 | 'aa2'

    }

    def magicFormula(a, b) {
        a * b + 2
    }

    @Unroll
    def "check if #a times #b equals #c"() {
        // test should pass for all ints a,b
        // such that 0<= a,b <=10 yields 121 permutations
        expect:
        advancedMultiplication(a, b) == c

        where:
        a << (0..10).collect { [it].multiply(11) }.flatten()
        b << (0..10).multiply(11)
        c = a * b
    }

    def advancedMultiplication(a, b) {
        (2 * a * 2 * b) / 4
    }
}

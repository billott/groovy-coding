package org.groovycoding.gcode02

class gcode02 extends GroovyTestCase {

    void test010_RegularBooleanExpressions() {
        def predicate1 = false
        def predicate2 = true

        predicate1 = true
        predicate2 = false

        assert predicate1
        assert predicate2 == false
    }

    void test020_CollectionHelpers() {
        // Lists and maps evaluate to false if they're empty.  Otherwise, they evaluate to true.
        Map<String, String> map = [:]
        List<String> list = ['item']

        map['key'] = 'value'
        list.clear()

        assert map.asBoolean()
        assert list.asBoolean() == false
    }

    void test030_StringExpression() {
        // empty (or null) strings are false.
        String stringOne = 'Non-empty string'
        String stringTwo = ''

        stringOne = ''
        stringTwo = 'something'

        if (stringOne) {
            fail()
        }
        assert stringTwo.asBoolean()
    }

    void test04_NumericExpression() {
        // Similar to C code, null or zeros are false. Others are true.
        def balance = [2, -3, 6, 0, 5]
        balance[3] = 3

        def result = true
        for (int i : balance) {
            result = result && i.asBoolean()
        }
        assert result
    }
}

package org.groovycoding.gcode03

class gcode03 extends GroovyTestCase {

    void test010_GBeans() {
        JavaDuke javaPerson = new JavaDuke("Bill", "Clinton", "1234");

        GroovyDude groovyPerson = new GroovyDude('Austin', 'Powers', '6969')

        def javaFirstName
        def groovyFirstName
        javaFirstName = javaPerson.getFirstName();
        groovyFirstName = groovyPerson.firstName

        assert javaFirstName == 'Bill'
        assert groovyFirstName == 'Austin'
    }

    void test020_ReadOnlyFieldGBean() {

        def person = new GroovyDude('David', 'Bowie', '1947')
        def failed = true
        shouldFail (ReadOnlyPropertyException) {
            person.ssn = '1234'
            failed = false
        }
        assert failed
    }

    void test030_NamedParameterConstructor() {
        def stringData =
                "Never trust anything that can think for itself if you can't see where it keeps its brain";
        def quote = new SimpleGroovyBean(title: 'Quote object',
                                         data: stringData)
        assert quote.data == stringData

        def transaction
        transaction = new SimpleGroovyBean(title: 'Transaction', data: -99)
        //assert transaction.data == -99
    }

    void test040_DefaultValues() {
        def nameObject = new NameWithDefaultValue()
        assert nameObject.name == 'Anonymous'
    }
}

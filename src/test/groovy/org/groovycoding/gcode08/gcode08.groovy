package org.groovycoding.gcode08

class gcode08 extends GroovyTestCase {

    void test010_SwitchStatement() {

        def magicClosure = { input ->
            def result
            switch (input) {
                case 1..100:
                    result = input / 2
                    break
                case ~/.*ee/:
                    result = "${input[0..-3]}ey"
                    break;
                default:
                    result = input
            }
            result
        }
        [5: 2.5, 'smile': 'smile', 'smilee': 'smiley', 'heehee': 'heehey'].each { key, expectedValue ->
            assert magicClosure(key) == expectedValue
        }
    }

    void test020_CaseSwitch() {
        def cartoon = new Cartoon(name: 'Mickey Mouse', feeling: Feeling.Guilty)

        switch (cartoon) {
            case Feeling.Guilty:
                // process guilty feeling cartoon
                break
            default:
                fail()
        }

        def person = new Person(name: 'Jack Bauer', feelings: [Feeling.Suicidal, Feeling.Relaxed])

        switch (person) {
            case Feeling.Anticipation:
                break
            case [Feeling.Happy, Feeling.Sad]:
                break
            case Feeling.Suicidal:
                break
            default:
                fail()
        }
    }

    void test030_MultiAssignment() {
        def generateTwoRandomInts = { int maxInt ->
            def random = new Random()
            [random.nextInt(maxInt), random.nextInt(maxInt)]
        }

        def (intA, intB) = generateTwoRandomInts(10)
        assert intA in 0..<10
        assert intB in 0..<10
    }
}

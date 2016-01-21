package org.groovycoding.gcode06

class gcode06 extends GroovyTestCase {

    void test01_WithMethod() {
        StringBuilder javaStringBuilder = new StringBuilder();
        javaStringBuilder.append("roses are #FF0000\\n");
        javaStringBuilder.append("violets are #0000FF\\n");
        javaStringBuilder.append("all my base\\n")
        javaStringBuilder.append("are belong to you\\n")
        String javaResult = javaStringBuilder.toString();

        String groovyResult
        groovyResult = new StringBuilder().with {
            append(/roses are #FF0000\n/)
            append(/violets are #0000FF\n/)
            append(/all my base\n/)
            append(/are belong to you\n/)
            return it.toString()
        }
        assert groovyResult == javaResult
    }

    void test02_CollectMethodOnLists() {
        def differentTypes = [1, 'String', "GString", 'a', 'Another string', 0]
        def uniqueTypes = []
        uniqueTypes = differentTypes.collect { it.class }.unique()
        assert uniqueTypes == [Integer, String]
    }

    void test03_FileIteration() {
        int count = 0
        new File('src').eachFileRecurse { File file ->
            if (!file.isDirectory() && file.text.contains('Lorem'))
                count++
        }
        assert count == 3
    }

    void test04_ConcludingExercise() {
        def primesBetween200And250 = []
        primesBetween200And250 = (200..250).findAll { candidate ->
            (2..<candidate).every { divisor ->
                candidate % divisor != 0
            }
        }
        assert primesBetween200And250 == [211, 223, 227, 229, 233, 239, 241]
    }
}

package org.groovycoding.gcode05

class gcode05 extends GroovyTestCase {

    void test010_IterateEach() {
        def products = ['SCCS', 'PVCS', 'CVS', 'SVN', 'GIT']

        for (String s : products) {
            println s
        }
        println("=====")
        products.each {
            println it
        }

        Map<String, String> idToNameMap = [123: 'Bill', 345: 'Greg', 678: 'Bear']

        List<String> javaIdListResult = new LinkedList<String>();
        for (Map.Entry<String, String> entry : idToNameMap) {
            javaIdListResult.add(entry.getKey() + entry.getValue());
        }

        def idListResult = []
        idToNameMap.each { key, value ->
            idListResult << "$key$value"
        }
        assert idListResult == ['123Bill', '345Greg', '678Bear']
    }

    void test020_LetterRange() {
        def range = 5..10
        // What will range equal?
        def expectedRange = []
        expectedRange = [5, 6, 7, 8, 9, 10]
        assert range == expectedRange
    }

    void test030_IterateLetterRange() {
        def rangeResult = []
        def range = 'a'..'z'
        // alphabet letters defined in groovy range
        // Compare the even count id of letter to the array below
        range.eachWithIndex { value, index ->
            if (index % 2 == 0)
                rangeResult << value
        }
        assert rangeResult == ['a', 'c', 'e', 'g', 'i', 'k', 'm', 'o', 'q', 's', 'u', 'w', 'y']
    }
}

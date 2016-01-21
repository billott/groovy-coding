package org.groovycoding.gcode04

class gcode04 extends GroovyTestCase {

    void test01_IntroToClosures() {
        def sayHelloClosure = { return 'Closure says Hi' }

        def helloClosureResult = sayHelloClosure()
        def expectedHelloClosureResult = 'Closure says Hi'
        assert helloClosureResult == expectedHelloClosureResult

        // Closures has parameters
        def helloClosure = { String name -> return "Hello $name" }
        String helloGreeting = helloClosure('Bill')

        String expectedHelloGreeting
        expectedHelloGreeting = 'Hello Bill'
        assert helloGreeting == expectedHelloGreeting

        def happyBirthdayGreeting = { "Happy Birthday To $it" }
        String happyBirthdayGranger = happyBirthdayGreeting('Bill')

        def expectedHappyBirthdayGranger
        expectedHappyBirthdayGranger = 'Happy Birthday To Bill'
        assert happyBirthdayGranger == expectedHappyBirthdayGranger

        def resultClosure
        resultClosure = { int a, int b -> (a + b) * 2 }

        assert resultClosure(2, 3) == 10
        shouldFail {
            resultClosure('one', 'two')
        }
    }

    void test02_MoreClosureIntro() {
        def list = ['one', 'two', 'three']
        List<String> javaResult = new ArrayList<String>();
        for (String s : list) {
            if (s.startsWith("t")) {
                javaResult.add(s);
            }
        }

        List<String> groovyResult = list.grep({ it.startsWith('t') })
        assert javaResult == groovyResult

        def flagColors = []
        "I have seen blue flags, red flags, and purple flags".eachMatch('(\\w+) flags') { entireMatch, color ->
            flagColors.add(color)
        }

        def expectedFlagColors = []
        expectedFlagColors = ['blue', 'red', 'purple']
        assert flagColors == expectedFlagColors

        StringWriter filteredResult = new StringWriter()
        def prefix = 'src/test/groovy/org/groovycoding/gcode04/'
        def file = new File("$prefix/sample.txt")
        file.filterLine(filteredResult) { String line ->
            !line.startsWith('#')
        }

        String result = filteredResult.toString().trim().replaceAll(/[\n\r]+/, '\n')
        String answer = new File("$prefix/sample-solution.txt").text.replaceAll(/[\n\r]+/, '\n')
        assert answer == result
    }

    void test03_MoreClosureDetails() {
        def count = "The word cattle has the word cat in it, just like catwoman does.".count 'cat'

        def expectedCount
        expectedCount = 3
        assert count == expectedCount

        def languageList = ['Groovy', 'Java', 'Ada'].findAll {  // no parenthesis
            it.contains('v')
        }

        def expectedLanguageList
        expectedLanguageList = ['Groovy', 'Java']
        assert languageList == expectedLanguageList
    }
}

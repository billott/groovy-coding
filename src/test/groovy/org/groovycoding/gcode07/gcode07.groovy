package org.groovycoding.gcode07

import java.util.regex.Matcher
import java.util.regex.Pattern

class gcode07 extends GroovyTestCase {

    void test01_SimpleRegularExpression() {
        def technologies = ['Grails', 'Gradle', '.NET', 'Python', 'Groovy']
        def regexp
        regexp = '^G.*[e|s]$'
        def result = technologies.findAll { it ==~ regexp }

        assert result == ['Grails', 'Gradle']
    }

    void test02_MultilineStrings() {
        String signs = '+, \\, and others'

        // Create the string below with Groovy
        String javaString = "In Java a multiline string\n" +
                "requires using special signs such as " + signs + "\n" +
                "and can become difficult to maintain"
        String groovyString
        groovyString =
"""In Java a multiline string
requires using special signs such as $signs
and can become difficult to maintain"""
        assert groovyString == javaString
    }

    void test03_SlashStrings() {
        def text = '''|Item          # Sold  Leftover
                      |Xbox          2000    10
                      |Playstation   100     5
                      |Wii           5       1000'''.stripMargin()

        // If we wanted to grab the number of leftover items with a regular expression in Java, we would do this:
        int javaSum = 0;
        String javaRegExp = "(?sm)(.*?)\\s+(\\d+)\\s+(\\d+)"
        Matcher javaMatcher = Pattern.compile(javaRegExp).matcher(text);
        while (javaMatcher.find()) {
            javaSum += Integer.valueOf(javaMatcher.group(3));
        }

        def groovyRegExp
        groovyRegExp = /(?sm)(.*?)\s+(\d+)\s+(\d+)/
        def matcher = text =~ groovyRegExp
        def groovySum = matcher.collect { it[3].toInteger() }.sum()

        assert groovySum == javaSum
    }

    void test04_RegexpOperators() {
        Pattern patternInJava = Pattern.compile("\\d{3}([,\\s])?\\d{4}");

        // Create the same Pattern object in Groovy
        def patternInGroovy
        patternInGroovy = ~/\d{3}([,\s])?\d{4}/
        assert patternInGroovy instanceof Pattern
        assertEquals(patternInJava.pattern(), patternInGroovy.pattern())

        def names = 'John Lennon, Paul McCartney, George Harrison, Ringo Starr'
        def firstNamesList = []
        def matcher = names =~ /(\w+)\s(\w+)/
        matcher.each { match, first, last ->
            firstNamesList << first
        }
        assert firstNamesList == ['John', 'Paul', 'George', 'Ringo']

        def number = '4927856234092'
        boolean isNumberValid = false
        isNumberValid = number ==~ /^4[0-9]{12}(?:[0-9]{3})?$/
        assert isNumberValid, 'Visa number should be valid!'
    }

    void test05_ReplaceClosure() {
        def dictionary = ['town': 'ciudad', 'man': 'hombre', 'life': 'vida']
        def song = '''|In the town where I was born
                      |Lived a man who sailed to sea
                      |And he told us of his life
                      |In the land of submarines'''.stripMargin()
        def result
        result = song.replaceAll(/\w+/) { dictionary[it] ?: it }

        def expected = '''|In the ciudad where I was born
                          |Lived a hombre who sailed to sea
                          |And he told us of his vida
                          |In the land of submarines'''.stripMargin()

        assert result == expected
    }

    void test06_MultilineRegexComments() {
        def text = '''|Item          # Sold  Leftover
                      |Xbox          2000    10
                      |Playstation   100     5
                      |Wii           5       1000'''.stripMargin()

        String regexp
        regexp = /(?smx)
                 (.*?)      # item name
                 \s+        # space
                 (\d+)      # number sold
                 \s+        # space
                 (\d+)      # leftover/
        def sum = text.findAll(regexp) { it[3].toInteger() }.sum()

        assert sum == 1015
        def options = regexp.find(/\(\?[^\)]*\)/)
        assert options.contains('x'), 'A commented regex must use the x flag'
        assert regexp.contains('#'), 'Comments can be inserted using the # character'
    }
}

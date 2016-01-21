package org.groovycoding.gcode10

import groovy.xml.MarkupBuilder

class gcode10 extends GroovyTestCase {

    void test010_XmlSlurper() {
        def movieCount
        def xml = new XmlSlurper().parse('src/test/groovy/org/groovycoding/gcode10/movie_catalog.xml')
        movieCount = xml.movie.size()
        assert movieCount == 7

        // Get the movie title containing the word "the" in the movie title
        List<String> moviesWithThe = []
        def xml2 = new XmlSlurper().parse('src/test/groovy/org/groovycoding/gcode10/movie_catalog.xml')
        def filteredNodeChildren = xml2.movie.title.findAll {
            it.text().toLowerCase().contains('the')
        }
        moviesWithThe = filteredNodeChildren.collect() { it.text() }
        assert moviesWithThe.containsAll(['Conan the Barbarian', 'The Expendables', 'The Terminator'])

        // How many movie ids greater than 5 id?
        def movieIdsGreaterThan5
        def xml3 = new XmlSlurper().parse('src/test/groovy/org/groovycoding/gcode10/movie_catalog.xml')
        movieIdsGreaterThan5 = xml3.movie.findAll { it.@id.text().toInteger() > 5 }.size()
        assert movieIdsGreaterThan5 == 2
    }

    void test020_XmlSlurper2() {

        List<String> sortedList = []
        def xml = new XmlSlurper().parse('src/test/groovy/org/groovycoding/gcode10/movie_catalog.xml')
        def listOfNodeChildren = xml.movie.list().sort { node1, node2 ->
            def year1 = node1.year.text()
            def year2 = node2.year.text()
            return (year1 == year2) ? node1.title.text()<=>node2.title.text() : year1<=>year2
        }
        sortedList = listOfNodeChildren.collect { it.title.text() }
        assert sortedList == ['Conan the Barbarian', 'The Terminator', 'Predator',
                'Kindergarten Cop', 'Total Recall', 'True Lies', 'The Expendables']
    }

    void test030_XmlMarkupBuilder1() {
        def html
        def writer = new StringWriter()
        def b = new MarkupBuilder(writer)
        b.html {
            body {
                h1('title')
            }
        }
        html = writer.toString()
        assert formatXml(html) == formatXml("<html><body><h1>title</h1></body></html>")
    }

    void test040_XmlMarkupBuilder2() {
        // <movies>
        //      <movie id='id' title='title' year='year'/>
        // </movies>

        String convertedXml
        def reader = new XmlSlurper().parse('src/test/groovy/org/groovycoding/gcode10/movie_catalog.xml')
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        builder.movies {
            reader.movie.each { movieNode ->
                movie(id: movieNode.@id.text(), title: movieNode.title.text(), year: movieNode.year.text())
            }
        }
        convertedXml = writer.toString()
        def expected = """|<movies>
                            |  <movie id='6' title='Total Recall' year='1990' />
                            |  <movie id='4' title='The Terminator' year='1984' />
                            |  <movie id='5' title='The Expendables' year='2010' />
                            |  <movie id='1' title='Conan the Barbarian' year='1982' />
                            |  <movie id='3' title='Predator' year='1987' />
                            |  <movie id='2' title='True Lies' year='1994' />
                            |  <movie id='7' title='Kindergarten Cop' year='1990' />
                            |</movies>""".stripMargin()

        assert formatXml(expected) == formatXml(convertedXml)
    }

    private String formatXml(String xml) {
        def stringWriter = new StringWriter()
        def node = new XmlParser().parseText(xml.toString());
        new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
        return stringWriter.toString()
    }

    void test050_AntBuilderCopy() {
        // Using Ant task copy movie_catalog.xml to movie_catalog_copy.xml in the same directory
        def baseDir = 'src/test/groovy/org/groovycoding/gcode10'
        new AntBuilder().copy(file: "${baseDir}/movie_catalog.xml", tofile: "${baseDir}/movie_catalog_copy.xml")
        assert new File("${baseDir}/movie_catalog_copy.xml").exists()
    }

    void test060_AntBuilderChecksum() {

        def baseDir = 'src/test/groovy/org/groovycoding/gcode10'
        def actualChecksum
        def antBuilder = new AntBuilder()
        antBuilder.checksum(file: "${baseDir}/movie_catalog.xml", property: 'moviesChecksum')
        actualChecksum = antBuilder.project.properties.moviesChecksum
        assert actualChecksum == '9160b6a6555e31ebc01f30c1db7e1277'
    }
}

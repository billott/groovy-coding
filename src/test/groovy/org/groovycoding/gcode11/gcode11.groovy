package org.groovycoding.gcode11

import groovy.sql.Sql
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

class gcode11 extends GroovyTestCase {
    final String CREATE_STMT = '''|create table PERSON (
                                  |    ID INT PRIMARY KEY AUTO_INCREMENT,
                                  |    FIRSTNAME VARCHAR(64), LASTNAME VARCHAR(64)
                                  |)'''.stripMargin()

    void test010_CreateDbInstance() {

        Connection javaConnection = null;
        try {
            DriverManager.registerDriver(new org.h2.Driver())
            javaConnection = DriverManager.getConnection("jdbc:h2:mem:javaDb", "sa", "");

//            DriverManager.registerDriver(new org.postgresql.Driver())
//            javaConnection = DriverManager.getConnection("jdbc:postgresql://localhost:543/public", "postgres", "postgres!");

            PreparedStatement javaStatement = javaConnection.prepareStatement(CREATE_STMT);
            javaStatement.execute();
            javaStatement.close();
        } catch (Exception ignored) {
            // do some stuff here... or not
        } finally {
            if (javaConnection != null)
                javaConnection.close();
        }

        // Messy, no? Now let's do it the Groovy way:
        Sql.withInstance('jdbc:h2:mem:groovyDb', 'sa', '', 'org.h2.Driver') { db ->
            db.execute(CREATE_STMT) // Note how there's no need for db.close()! Groovy's withInstance does it for you.
        }

        // Using what you just learned add a Person named Jack Dawson to the table
        Sql.withInstance('jdbc:h2:mem:groovyDb2', 'sa', '', 'org.h2.Driver') { db ->
            db.execute(CREATE_STMT)
            db.execute('insert into PERSON (FIRSTNAME, LASTNAME) values (?, ?)', ['Jack', 'Dawson'])
            assert db.firstRow('select count(*) c from Person').c == 1
            assert db.firstRow('select LASTNAME from Person where FIRSTNAME = ?', ['Jack']).lastname == 'Dawson'
        }
    }

    void test020_InsertData() {
        // Let's use what we've learned from gcode10 and import data into db tables
        Sql.withInstance('jdbc:h2:mem:groovyDb2', 'sa', '', 'org.h2.Driver') { db ->
            db.execute(CREATE_STMT)

            // Add all the people from cast.txt into the table we just created.
            new File('src/test/groovy/org/groovycoding/gcode11/cast.txt').eachLine { line ->
                db.execute('insert into PERSON (FIRSTNAME, LASTNAME) values (?, ?)', line.split())
            }
            assert db.firstRow('select count(*) c from Person').c == 23

            // Now do the same with an xml source from cast2.xml (add the actor names):
            def xml = new XmlSlurper().parse('src/test/groovy/org/groovycoding/gcode11/cast2.xml')
            xml.character.@actor.each { attr ->
                db.execute('insert into PERSON (FIRSTNAME, LASTNAME) values (?, ?)', attr.text().split())
            }
            assert db.firstRow('select count(*) c from Person').c == 39

            // Groovy also allows a syntax that doesn't involve SQL at all. Add one more person (anyone) using the
            // db.dataSet('PERSON') method. See http://docs.groovy-lang.org/latest/html/api/groovy/sql/DataSet.html
            def person = db.dataSet('PERSON')
            person.add(firstname: 'William', lastname: 'Shatner')
            assert db.firstRow('select count(*) c from Person').c == 40
        }

    }


    void test030_IterateResults() {
        Sql.withInstance('jdbc:h2:mem:groovyDb3', 'sa', '', 'org.h2.Driver') { db ->
            // Populates the db with some people names. Check out the sql files
            // to understand the table structure.
            initDbInstance(db)

            // Using what you've learned in the link from test01, run an SQL query to find Rose's last name:
            def lastNameRose
            def rose = db.firstRow('select LASTNAME from Person where FIRSTNAME = :firstName', [firstName:'Rose'])
            lastNameRose = rose.lastname
            assert lastNameRose == 'DeWitt'

            // Now, using an SQL select and the eachRow() method, count the number of 'e' (case sensitive) in the
            // last names of the people in Person
            def eCount = 0
            db.eachRow('select LASTNAME from PERSON') { row ->
                eCount += row.lastname.count('e')
            }
            assert eCount == 2
        }
    }

    void test040_UpdateTables() {
        Sql.withInstance('jdbc:h2:mem:groovyDb4', 'sa', '', 'org.h2.Driver') { db ->
            initDbInstance(db)

            // allow resultSets to be able to be changed
            db.resultSetConcurrency = java.sql.ResultSet.CONCUR_UPDATABLE

            // Use eachRow() to change all the first names that contain the letter 'a' (lowercase) into 'Alf'.
            db.eachRow("select * from PERSON p where p.firstname like '%a%'") { row ->
                row.firstname = 'Alf'
            }
            assert db.firstRow("select count(*) c from PERSON where FIRSTNAME = 'Alf'").c == 2
        }
    }

    void initDbInstance(Sql db) {
        def baseDir = 'src/test/groovy/org/groovycoding/gcode11'
        db.execute(new File("${baseDir}/create-schema.sql").text)
        db.execute(new File("${baseDir}/data.sql").text)
    }
}

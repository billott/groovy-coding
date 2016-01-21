## Groovy Coding Project ##

The Groovy Coding project available here is a open source software derived
collection of small code snippets in the form of unit tests,
(including junit, Spock), designed to show the power of Groovy features and common idioms.
It starts out by teaching the Groovy basics and then expands your knowledge
in metaprogramming, slurpers, and all the other goodness in Groovy.
As a long time, Java developer, I was reluctant to go back to Java Coding Projects,
once I saw the power, speed, and ease of use from the Groovy code (gcode) in this project.

In summary, I hope my secret is safe with you, and please don't the let your boss know,
how much time savings and money savings your project can benefit from using Groovy.
We all enjoy doing work the old fashion way, slow and painful,
just like your boss had to as software developer of over 25 years ago.
Oh, yep that is I.

## Getting Started ##
1.  Make sure you have [JDK 1.6+][jdk] installed 
2.  Download and unzip the archive file, and extract groovy-code.zip file
3.  Startup ItelliJ and create new project using import of "groovy-code" path
4.  From cmd shell, change directory to "cd .\groovy-coding\src\test\groovy\org\groovycoding"
5.  Start "Run" test on the class name source line, use the option menu.
6.  Change the code, then run separate method/whole class tests.
    Get a Green bar then goto next method/gcode example or try fixing the code again.
    Welcome to TDD... Test Driven Design!
7.  Practice does make perfect and then you see the Groovy Power.

## Q&A ##

#### How can I use IntelliJ to debug/edit the Groovy-Coding examples? ####

* Download and install [IntelliJ IDEA Community Edition][ideac]
* In IDEA, `File -> Open Project` and open the `build.gradle` file

#### How can I run the examples from behind a proxy server? ####

The `gradlew` script downloads Groovy and Gradle,
The `gradlew` can work through your proxy, just add the following parameters:
```
$ ./gradlew groovy-coding -Dhttp.proxyHost=[http proxy] -Dhttp.proxyPort=[http proxy port]
```

Here are some resources...

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[ideac]: http://www.jetbrains.com/idea/download/

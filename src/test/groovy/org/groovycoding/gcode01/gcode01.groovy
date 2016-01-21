package org.groovycoding.gcode01

class gcode01 extends GroovyTestCase {

    void test010_AssertionsAndSomeSyntax() {

        boolean assertion = false
        def hello = "Hola"

        // required values
        hello = "Hello"
        assertion = true

        assert assertion, 'Assign "true" to the "assertion" variable to proceed'
        assert hello == "Hello", 'Modify "Hola" to "Hello" to proceed'
    }

    void test020_GStrings() {
        // Groovy allows you to use either regular quotes (') or double-quotes (") for String declarations.
        // double-quotes create a GString, which is a super-powered String.
        // GStrings allow you to use the ${} syntax within them.
        def name = 'Bill'
        def greeting = "Hello ${name}, how are you?"
        def math = "The result of 5 + 5 is ${5 + 5}"

        String result
        result = "The size of the string '${greeting}' is ${greeting.size()}"
        def tmpSize = result.size();

        assert result == "The size of the string 'Hello ${name}, how are you?' is 24"
    }

    void test030_GMaps() {
        def map = [right: 'righty side', left: 'lefter side']

        def result
        result = map['right'] + map['left']
        assert result.toCharArray().size() == 22
    }

    void test040_GLists() {

        List<String> javaList = new ArrayList<String>();
        javaList.add("King");
        javaList.add("Queen");
        javaList.add("Prince");

        def groovyList = ['King', 'Prince']
        groovyList = groovyList.plus(1, 'Queen')
        assert groovyList == javaList
    }

    void test050_ElvisControlFlow() {
        User player = new User('Bill', 'Clinton', 'willie', null)
        UserService userServiceWithUserLoggedIn = [getLoggedInUser: { player }] as UserService
        UserService userServiceWithoutLoggedInUser = [getLoggedInUser: { null }] as UserService

        String firstName = player.getFirstName();
        String javaDisplayName = firstName == null ? player.getUsername() : firstName;
        String javaCity = "";
        if (player.getAddress() != null && player.getAddress().getCity() != null) {
            javaCity = player.getAddress().getCity();
        }

        def groovyDisplayName = player.firstName ?: player.username
        def groovyCity = player?.address?.city
        assert createUserMessage(userServiceWithUserLoggedIn) == 'Hello Bill!'
        assert createUserMessage(userServiceWithoutLoggedInUser) == 'Hello Anonymous!'
        // See method below for above method abstraction
    }

    private String createUserMessage(UserService userService) {
        def message
        message = "Hello ${userService.loggedInUser?.firstName ?: 'Anonymous'}!"
        // no return statement is require to pass the value
        message
    }
}

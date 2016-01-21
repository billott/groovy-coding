package org.groovycoding.gcode09

class gcode09 extends GroovyTestCase {

    void test010_Expando() {
        // Create an Expando class by dynamically create a 'firstName' property set with some value.
        // Also add a sayHowdy() method that returns "Hello from ${firstName}"
        def expando = new Expando()

        expando.firstName = 'Bill'
        expando.sayHowdy = { ->
            "Howdy from ${firstName}"
        }

        assert expando?.firstName != null, 'firstName property was not found'
        assert expando.sayHowdy() == "Howdy from ${expando.firstName}"
    }

    void test020_GInterceptors() {
        // AOP method of inspecting the method invocation/call between methods
        // WarefareInterceptor Rule make sure that only admin is allowed to run this service.
        def proxy
        proxy = ProxyMetaClass.getInstance(TargetService)
        proxy.interceptor = new WarefareInterceptor()

        proxy.use {
            def targetService = new TargetService()
            targetService.targetCity('Bill', 'ISIS')
            assert targetService.nukeCount == 0
            targetService.targetCity('admin', 'ISIS')
            assert targetService.nukeCount == 1
        }

    }

    void test030_ThisDelegateOwner() {
        def expectedThisClassName

        expectedThisClassName = 'org.groovycoding.gcode09.gcode09'
        assert this.class.name == expectedThisClassName

        // Class owner is the same thing as 'this'.
        // Unless you are surrounded by a Closure

        // Delegate can be owners assigned by an external method.
        // Changing the delegate allows you to change
        // the 'context' in which the closure is run.

        // Free variables are 'inherited' into the closure
        // from the environment where the closure was defined.
        // See below gravity is free variable

        def calculateWeight = { mass ->
            mass * gravity
        }

        calculateWeight.resolveStrategy = Closure.DELEGATE_ONLY
        calculateWeight.delegate = new MoonConstants()
        def weightOnMoon = calculateWeight(10)

        calculateWeight.delegate = new PlanetConstants()
        def weightOnEarth = calculateWeight(10)

        // Can you figure out what the values for weightOnEarth and weightOnMoon are?
        def expectedWeightOnMoon, expectedWeightOnEarth
        expectedWeightOnMoon = 1.655
        expectedWeightOnEarth = 10
        assert weightOnEarth == expectedWeightOnEarth
        assert weightOnMoon == expectedWeightOnMoon

        calculateWeight.delegate = [gravity: 6]
        def weightOnFakePlanet = calculateWeight(10)
        assert weightOnFakePlanet == 60
    }


    void test040_InvokeMethod() {
        // Robotto object should have a (X,Y) coordinates
        Robotto robotto = new Robotto()

        assert robotto.x == 0
        assert robotto.y == 0

        // Dynamically adding methods to robotto object
        // Change x and y accordingly.
        robotto.left()    // x= -1 y=0
        robotto.left()    // x= -2 y=0
        robotto.right()   // x= -1 y=0
        robotto.up()      // x= -1 y=1
        robotto.down()    // x= -1 y=0
        robotto.down()    // x= -1 y=-1

        assert robotto.x == -1
        assert robotto.y == -1

        // Using invokeMethod(), handle every possible goXYZ combination...
        robotto.goLeftRightRightDown()
        // Left  x=-2 y=-1
        // Right x=-1 y=-1
        // Right x=0 y=-1
        // Down  x=0 y=-2
        assert [robotto.x, robotto.y] == [0, -2]

        robotto.goDownDownDownDown()
        // Down  x=0 y=-3
        // Down x=0 y=-4
        // Down x=0 y=-5
        // Down  x=0 y=-6
        assert [robotto.x, robotto.y] == [0, -6]
    }

    void test050_AddMethodsToObjects() {
        // add a fizzBuzz() method to Integer such that:
        //   - if the integer value is divisible by 3, return 'Fizz'
        //   - if the integer value is divisible by 5, return 'Buzz'
        //   - if it's divisible by both, return 'FizzBuzz'
        //   - otherwise, return the number itself (as a String)

        Integer.metaClass.fizzBuzz = {
            String result = ''
            if (delegate % 3 == 0) result += 'Fizz'
            if (delegate % 5 == 0) result += 'Buzz'
            if (!result) result = delegate.toString()
            result
        }
        def fizzBuzzes = (1..15).collect { it.fizzBuzz() }
        def expectedFizzBuzzes = ['1', '2', 'Fizz', '4', 'Buzz', 'Fizz', '7', '8', 'Fizz',
                'Buzz', '11', 'Fizz', '13', '14', 'FizzBuzz']
        assert fizzBuzzes == expectedFizzBuzzes
    }
}

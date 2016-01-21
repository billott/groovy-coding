package org.groovycoding.gcode12

class gcode12 extends GroovyTestCase {


    void test010_ShouldFail() {
        // Lets make this unit test pass.
        // Try to be specific about the exception type.

        shouldFail(MissingMethodException) {
            Integer.nonExistentMethod()
        }
    }


    void test020_Stubout() {
        // Use the Expando class (remember gcode09?)
        // to stub the hard worker with a quick one by
        // adding a work() method to it
        Factory factory = new Factory()
        long startTime = System.currentTimeMillis()
        def stub = new Expando();
        stub.work = { number -> number + 10 }
        factory.worker = stub
        factory.work()
        long endTime = System.currentTimeMillis()
        assert endTime - startTime < 2000

        // Great! Now let's assume you cannot alter the worker member because
        // the class being tested uses a static call to create the worker.
        startTime = System.currentTimeMillis()
        Worker worker = { number -> number + 10 } as Worker
        StaticFactory.metaClass.static.getWorker = { worker }
       new StaticFactory(numbers: 1..10).work()
        endTime = System.currentTimeMillis()
        assert endTime - startTime < 3000

    }

}

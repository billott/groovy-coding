package org.groovycoding.gcode12

class StaticFactory {

    List<Integer> numbers

    static Worker getWorker() {
        return new LineWorker()
    }

    List<Integer> work() {
        numbers.collect {
            getWorker().work(it)
        }
    }


}

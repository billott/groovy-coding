package org.groovycoding.gcode12

class Factory {

    def numbers;
    def worker;

    // defaults to a Hard (and slow) Worker implementation
    Factory(worker = new LineWorker(), numbers = 1..10) {
        this.numbers = numbers;
        this.worker = worker;
    }

    def work() {
        numbers.collect {
            worker.work(it)
        }
    }
}

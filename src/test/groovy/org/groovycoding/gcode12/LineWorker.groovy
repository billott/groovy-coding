package org.groovycoding.gcode12

class LineWorker implements Worker {

    @Override
    int work(int number) {
        Thread.sleep(1000)
        return number + 10;
    }
}

package main.engine.utils;


public class Timer {
    private double lastLooptime;

    public void init() {
        lastLooptime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1000000000.0;
    }

    public float getElapsedTime() {
        float elpasedTime = (float) (getTime() - lastLooptime);
        return elpasedTime;
    }

    public void reset() {
        lastLooptime = getTime();
    }

    public double getLastLoopTime() {
        return lastLooptime;
    }
}

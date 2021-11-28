package com.awwthefirst.pomodorotimer;

public class Time {
    private final int minutes;
    private final int seconds;

    public Time(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        minutes = (int) (totalSeconds / 60);
        seconds = (int) totalSeconds % 60;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        String minutesString =
                Integer.toString(minutes).length() == 1 ? "0" + minutes : Integer.toString(minutes);
        String secondsString =
                Integer.toString(seconds).length() == 1 ? "0" + seconds : Integer.toString(seconds);
        return minutesString + ":" + secondsString;
    }
}

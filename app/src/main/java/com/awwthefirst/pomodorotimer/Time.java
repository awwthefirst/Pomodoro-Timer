package com.awwthefirst.pomodorotimer;

/**
 * An immutable class which converts milliseconds into minutes and seconds.
 */
public final class Time {
    private final int minutes;
    private final int seconds;

    /**
     * @param milliseconds The number of milliseconds which object represents
     */
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

    /**
     * @return a <code>String</code> with containing the minutes and seconds in a "00:00" format
     */
    @Override
    public String toString() {
        String minutesString =
                Integer.toString(minutes).length() == 1 ? "0" + minutes : Integer.toString(minutes);
        String secondsString =
                Integer.toString(seconds).length() == 1 ? "0" + seconds : Integer.toString(seconds);
        return minutesString + ":" + secondsString;
    }
}

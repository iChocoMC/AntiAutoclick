package com.github.ichocomc.antiautoclick.utils;

public class PlayerInfo {

    /*
     * Store times in miliseconds
     * 
     * Left Click:
     * times 0 = Old Result
     * times 1 = Old Time
     * times 2 = New time
     */
    private final short[] times = new short[3];

    /*
     * Amount of clicks in one second
     * byte 0 = Left Clicks
     * byte 1 = Right Clicks
     * 
     * byte 2 = Amount reports
     */
    private final byte[] bytes = new byte[3];

    public void addByte(int i) {
        ++bytes[i];
    }

    public byte getByte(int i) {
        return bytes[i];
    }

    public void addTime(long time) {
        times[1] = times[2];
        times[2] = (short)time;
    }

    // Milliseconds
    public int getClickDelay() {
        return (times[1] > times[2])
            ? times[1] - times[2]
            : times[2] - times[1];
    }

    public int getResult(int currentDelay) {
        return (currentDelay > times[0])
            ? currentDelay - times[0]
            : times[0] - currentDelay;
    }

    public void setOldClickDelay(int time) {
        times[0] = (short)time;
    }

    public void reset() {
        bytes[0] = 0;
        bytes[1] = 0;
    
        times[0] = 0;
        times[1] = 0;
        times[2] = 0;
    }

    public void resetReports() {
        bytes[2] = 0;
    }
}
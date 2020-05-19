package com.smage.engine.Timings;

import com.smage.engine.Object.GameRoom;

public class Timer extends Thread {
    private GameRoom room;
    public int framesPerSecond;
    public int currentFrame;
    public int currentFramesPerSecond;
    private long lastTimeMillis;

    public Timer(int frames, GameRoom room) {
        this.framesPerSecond = frames;
        this.room = room;
    }

    @Override
    public void run() {
        int sleepLength = 1000/framesPerSecond;
        int milliSecondsPerFrameSecond = sleepLength * framesPerSecond;
        while(!this.isInterrupted()) {
            try {
                Thread.sleep(sleepLength);
                room.tick();
                currentFrame++;

                if(System.currentTimeMillis() > lastTimeMillis + milliSecondsPerFrameSecond) {
                    currentFrame = 1;
                    lastTimeMillis = System.currentTimeMillis();
                    currentFramesPerSecond = currentFrame;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() {
        room.save();
    }
}

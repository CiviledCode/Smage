package com.smage.engine.Timings;

import com.smage.engine.Object.GameRoom;

public class Timer extends Thread {
    GameRoom room;
    int framesPerSecond;
    int currentFrames;

    public Timer(int frames, GameRoom room) {
        this.framesPerSecond = frames;
        this.room = room;
    }

    @Override
    public void run() {
        int sleepLength = 1000/framesPerSecond;
        while(!this.isInterrupted()) {
            try {
                Thread.sleep(sleepLength);
                room.tick();
                currentFrames++;
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

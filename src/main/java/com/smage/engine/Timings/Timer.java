package com.smage.engine.Timings;

import com.smage.engine.Object.GameRoom;

public class Timer extends Thread {
    private GameRoom room;
    public int framesPerSecond;
    public int currentFrame;
    public int currentFramesPerSecond;
    public int currentTick;
    public int ticksPerSecond = 20;
    public double fixedDeltaTime = 1000/framesPerSecond;
    private long lastSecondMillis;
    private long lastUpdateMillis;


    public Timer(int frames, GameRoom room) {
        this.framesPerSecond = frames;
        this.room = room;
    }

    @Override
    public void run() {
        //The amount of time the thread should sleep
        int sleepLength = 1000/framesPerSecond;

        //The amount of time the thread should take to update
        int tickTime = sleepLength * (framesPerSecond/ticksPerSecond);

        //The amount of milliseconds that the frame second should last for
        int milliSecondsPerFrameSecond = sleepLength * framesPerSecond;

        //The last millisecond time that the frame was updated
        long lastFrameMilli = 0;

        //Improvised sleep length to take into account thread backlog to overload the thread if needed and sleep less to avoid frame loss and innacurate updates
        int sleepLengthImprovised = sleepLength;
        while(!this.isInterrupted()) {
            try {
                lastFrameMilli = System.currentTimeMillis();
                Thread.sleep(sleepLengthImprovised);
                room.update();
                currentFrame++;


                //If the system detects that an update needs to be done
                if(System.currentTimeMillis() > lastUpdateMillis + tickTime && currentTick < ticksPerSecond) {

                    //Run the update function
                    room.fixedTick();
                    currentTick++;

                    //If there is a thread backlog and it's lagging, prioritize running the tick to make it a more accurate simulation rather than frames
                    if(((int)(System.currentTimeMillis() - lastSecondMillis)/tickTime) > currentTick) {
                        for(int i = 0; i < ((int)(System.currentTimeMillis() - lastSecondMillis)/tickTime) - currentTick; i++) {
                            room.fixedTick();
                            currentTick++;
                        }
                    }
                }

                //If a single second has gone by, log the lastSecond and set the FPS count and reset frame count
                if(System.currentTimeMillis() > lastSecondMillis + milliSecondsPerFrameSecond) {
                    currentFrame = 1;
                    currentTick = 1;
                    lastSecondMillis = System.currentTimeMillis();
                    currentFramesPerSecond = currentFrame;
                }

                //This makes the thread work overtime if it takes it longer to execute the frame
                sleepLengthImprovised = sleepLength - ((int) lastFrameMilli - sleepLength);
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

package com.smage.engine.Object;

import com.smage.engine.Timings.Timer;
import com.smage.engine.Utilities.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {
    List<GameObject> objects = new ArrayList<>();
    Timer timer;
    GameWindow window;
    GraphicsContext graphicsContext;
    public Paint backgroundColor = Color.BLACK;

    public GameRoom(GameWindow window) {
        this.timer = new Timer(window.fps, this);
        this.window = window;
    }

    protected void start() {
        timer.start();
    }

    /**
     * Adds a GameObject to the room and adds it to the ticking list
     *
     * This is required for objects to properly be seen inside of a room
     * @param object
     */
    public void createObject(GameObject object) {
        this.objects.add(object);
    }

    /**
     * Removes a GameObject from the room and ticking list
     *
     * This will stop updates and ticks being sent to that object and will overall
     * delete it
     * @param object
     */
    public void destroyObject(GameObject object) {
        objects.remove(object);
    }

    /**
     * This function is ran every frame as long as it can keep up to a solid frame count
     *
     * This function calls 3 functions in order: preUpdate(), draw(), then update()
     *
     * this should be used for aesthetic purposes and drawing where losing a single frame or two is manageable
     */
    public void update() {
        for(GameObject object : objects) {
            object.preUpdate();
        }

        cleanWindow();
        for(GameObject object : objects) {
            object.draw(graphicsContext);
        }

        for(GameObject object : objects) {
            object.update();
        }
    }

    /**
     * This function is called once the thread is closing and the window is shutting. This should be used for things such as
     * saving object states, worlds, storing scores, etc.
     *
     * It isn't recommended that you flood this
     *
     * This generally will call the onDisable function inside of your GameInstance
     */
    public void save() {
        GameWindow.gameInstance.onDisable();
    }

    /**
     * This function is ran x amount of times per second. Your tick amount is defined inside of your config, although the default value is 20
     *
     * This should be used for things such as physics, movement, and networking, where missed ticks and delays aren't sustainable
     *
     * This prioritizes over frames in all situations, so you will get your tick amount before you will get your frame amount
     *
     * Having a tick value larger than your frame value will break the system
     */
    public void fixedTick() {
        for(GameObject object : objects) {
            object.fixedTick();
        }
    }

    private void cleanWindow() {
        graphicsContext.setFill(backgroundColor);
        graphicsContext.fillRect(0,0, GameWindow.windowWidth + 10, GameWindow.windowHeight + 10);
    }
}

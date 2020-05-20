package com.smage.engine.Object;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class GameObject {

    public GameObject() {

    }

    /**
     * Ran before draw() and update() every frame
     */
    public void preUpdate() {

    }

    /**
     * Draw objects to the screen using this method
     *
     * This method runs every frame, so it's important that you draw to the screen every frame
     * so your objects don't get cleared
     * @param graphics
     */
    public void draw(GraphicsContext graphics) {

    }

    /**
     * Ran lastly. This should be used for non-important, back burner things
     */
    public void update() {

    }

    /**
     * This is a loop that is ran as a priority over tick, preTick, and draw
     * This should handle things such as collisions and physics to keep parity between
     * devices in terms of physics
     *
     * It runs 20 times per second
     */
    public void fixedTick() {

    }
}

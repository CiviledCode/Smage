package com.smage.engine.Object;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class GameObject {

    public GameObject() {

    }

    public void preTick() {

    }

    public void draw(GraphicsContext graphics) {

    }

    public void tick() {

    }

    /**
     * This is a loop that is ran as a priority over tick, preTick, and draw
     * This should handle things such as collisions and physics to keep parity between
     * devices in terms of physics
     */
    public void fixedUpdate() {

    }
}

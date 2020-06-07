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

    public void start() {
        timer.start();
    }

    public void registerObject(GameObject object) {
        this.objects.add(object);
    }

    public void destroyObject(GameObject object) {
        objects.remove(object);
    }

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

    public void save() {

    }

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

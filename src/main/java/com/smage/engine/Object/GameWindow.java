package com.smage.engine.Object;

import com.smage.engine.IO.FileContents;
import com.smage.engine.Utilities.ClassFunctions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.File;

public class GameWindow extends Application {
    static int windowWidth = 1080, windowHeight = 720;
    static int fps = 60;
    static String title = "";
    static boolean fullscreen;
    static Scene scene;
    private static GameRoom room;
    public static Canvas canvas;
    public static GameInstance gameInstance;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Gets the game jarfile
        ClassFunctions functions = new ClassFunctions();
        File jarFile = getJarFile();

        //Gets the main configuration file from the jarFile resources folder
        // and grabs the values from it to configure
        //the window properties and the Main class

        FileContents file = functions.getFile(jarFile, "config.defi");
        windowHeight = file.getInt("height");
        windowWidth = file.getInt("width");
        fps = file.getInt("fps");
        String mainClass = file.getString("main", false);
        fullscreen = file.getBoolean("fullscreen");

        //Creates the main GameRoom and stores it
        room = new GameRoom(this);

        //Set some properties of the Window so we can properly use it
        primaryStage.setTitle(title);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setFullScreen(fullscreen);
        primaryStage.maximizedProperty();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            room.timer.interrupt();
            System.exit(0);
        });

        //Creates the group that we add the canvas to so we can easily draw
        Group group = new Group();
        scene = new Scene(group);
        canvas = new Canvas(windowWidth, windowHeight);
        group.getChildren().add(canvas);

        //Sets the scene of the window to out canvas and shows the window
        primaryStage.setScene(scene);
        primaryStage.show();

        //Sets the graphic object of the room and starts the game loop
        room.graphicsContext = canvas.getGraphicsContext2D();
        room.start();

        //Gets the class defined inside mainClass and creates a new instance of it
        //Afterwards, the onEnable method is ran because at this point everything should
        //be good to go in terms of rendering and actual game logic
        Class<? extends GameInstance> gameClass = getMainClass(mainClass, jarFile).asSubclass(GameInstance.class);
        gameInstance = gameClass.getConstructor().newInstance();
        gameInstance.onEnable();
    }

    //TODO: Edit the fullscreen boolean if the window is pulled out of fullscreen

    /**
     * Is the window in fullscreenmode
     * @return Fullscreen
     */
    public static boolean isFullScreen() {
        return fullscreen;
    }

    /**
     * Grabs the main GameRoom object for this Window
     * @return Main GameWindow
     */
    public static GameRoom getMainRoom() {
        return room;
    }

    /**
     * Gets the main class from the game jarfile
     * @param mainClass
     * @return
     */
    private static Class getMainClass(String mainClass, File jarFile) {
        ClassFunctions functions = new ClassFunctions();
        return functions.getClass(jarFile, mainClass);
    }

    /**
     * Gets the game jarfile
     * @return
     */
    private static File getJarFile() {
        File file = new File(System.getProperty("user.dir"));
        File currentFile = new ClassFunctions().currentRunningJar();
        ClassFunctions functions = new ClassFunctions();
        for(File f : file.listFiles()) {
            if(f.getName().contains(".jar")) {
                if (!(f.getAbsolutePath() + f.getName()).equals(currentFile.getAbsolutePath() + currentFile.getName())) {
                    String[] split = f.getName().split("\\.");
                    if (split[split.length - 1].equalsIgnoreCase("jar")) {
                        return f;
                    }
                }
            }
        }
        return null;
    }
}

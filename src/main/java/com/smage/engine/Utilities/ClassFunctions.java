package com.smage.engine.Utilities;

import com.smage.engine.IO.FileContents;
import com.smage.engine.Object.GameWindow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFunctions {

    /**
     * Gets the class loader from this class for grabbing classes
     * @return Class loader created
     */
    private static ClassLoader getClassLoader() {
        return ClassFunctions.class.getClassLoader();
    }

    /**
     * Converts the ClassLoader into a URLClassLoader to use for inside of a jarfile
     * @param jarFile Jarfile we are pushing into
     * @param parent ClassLoader that we are mapping this to
     * @return The URL class loader
     */
    private static URLClassLoader urlClassLoader(File jarFile, ClassLoader parent) {
            try {
                return new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, parent);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
    }

    /**
     * Get a class from the inside of a jarfile
     * @param file Jarfile
     * @param name Name of the class in package form
     *             i.e com.smage.testing.MainClass
     * @return Class found
     */
    public static Class getClass(File file, String name) {
        try {
            URLClassLoader loader = urlClassLoader(file, getClassLoader());
            return Class.forName(name, true, loader);
        } catch(ClassNotFoundException e) {
            System.out.println("The class " + name + " was not found inside jar " + file.getName());
        }
        return null;
    }

    /**
     * Get a file from the inside of a jarfile
     * @param jarFile Jarfile we are getting the file from
     * @param name Name of the file
     * @return The FileContents file needed. This will allow us to automatically read the contents of the file
     */
    public static FileContents getFile(File jarFile, String name) {
        try (JarFile jar = new JarFile(jarFile)) {
            JarEntry entry = jar.getJarEntry(name);
            if(entry != null) {
                try (InputStream stream = jar.getInputStream(entry)) {
                    return new FileContents(new InputStreamReader(stream, StandardCharsets.UTF_8));
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get the current running jar
     * @return File of the current running jar
     */
    public static File currentRunningJar() {
        try {
            return new File(ClassFunctions.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}

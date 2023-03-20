package DrawingApplication;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

public class Loader {
    public static Class loadNewClass(File file, String name) {
        URI uri = file.toURI();
        URL[] urls;
        try {
            urls = new URL[] {uri.toURL()};
        } catch (MalformedURLException e) {
            urls = null;
            e.printStackTrace();
        }
        ClassLoader classloader = new URLClassLoader (urls);
        Class newShape;
        try {
            newShape = classloader.loadClass("DrawingApplication." + name);
        } catch (ClassNotFoundException e) {
            newShape = null;
            e.printStackTrace();
        }
        
        return newShape;
    }

    public static Shape getShape(Class shapeClass) {
        Constructor constructor;

        try {
            constructor = shapeClass.getConstructor();
        } catch (NoSuchMethodException | SecurityException e2) {
            constructor = null;
            e2.printStackTrace();
        }

        Shape shape;
        try {
            shape = (Shape) constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            shape = null;
            e.printStackTrace();
        }
        return shape;
    }
}

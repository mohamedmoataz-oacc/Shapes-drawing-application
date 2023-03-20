package DrawingApplication;

import java.awt.*;
import java.awt.geom.*;
import java.util.HashMap;
import java.util.Map;

public class Ellipse implements Shape {
    double width, height;
    Point center;
    Color outline, fillColor;
    static int count = 0;
    int number;

    Ellipse() {
        this.width = 0;
        this.height = 0;
        number = ++count;
    }

    @Override
    public void setPosition(Point position) {
        center = position;
    }

    @Override
    public Point getPosition() {
        return center;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        height = properties.get("0");
        width = properties.get("1");
    }

    @Override
    public Map<String, Double> getProperties() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("Height", height);
        map.put("Width", width);
        return map;
    }

    @Override
    public void setColor(Color color) {
        outline = color;
    }

    @Override
    public Color getColor() {
        return outline;
    }

    @Override
    public void setFillColor(Color color) {
        fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void draw(Graphics canvas) {
        Graphics2D canvas2d = (Graphics2D) canvas;
        double  x = center.getX() - (width/2);
        double y = center.getY() - (height/2);
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, width, height);
        canvas2d.setColor(fillColor);
        canvas2d.fill(ellipse);
        canvas2d.setColor(outline);
        canvas2d.draw(ellipse);
    }
    
    @Override
    public Object clone() {
        Ellipse e = new Ellipse();
        e.setColor(outline);
        e.setFillColor(fillColor);
        HashMap<String, Double> h = new HashMap<>();
        h.put("0", height);
        h.put("1", width);
        e.setProperties(h);
        e.setPosition(center);
        return e;
    }

    @Override
    public String toString() {
        return "Ellipse_" + number;
    }
}

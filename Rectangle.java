package DrawingApplication;

import java.awt.*;
import java.awt.geom.*;
import java.util.HashMap;
import java.util.Map;

public class Rectangle implements Shape {
    double width, length;
    Color outline, fillColor;
    Point startPoint;
    static int count = 0;
    int number;

    Rectangle() {
        length = 0;
        width = 0;
        number = ++count;
    }

    @Override
    public void setPosition(Point position) {
        startPoint = position;
    }

    @Override
    public Point getPosition() {
        return startPoint;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        width = properties.get("1");
        length = properties.get("0");
    }

    @Override
    public Map<String, Double> getProperties() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("Width", width);
        map.put("Length", length);
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
        double x = startPoint.getX();
        double y = startPoint.getY();
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, width, length);
        canvas2d.setColor(fillColor);
        canvas2d.fill(rectangle);
        canvas2d.setColor(outline);
        canvas2d.draw(rectangle);
    }

    @Override
    public Object clone() {
        Rectangle e = new Rectangle();
        e.setColor(outline);
        e.setFillColor(fillColor);
        HashMap<String, Double> h = new HashMap<>();
        h.put("0", length);
        h.put("1", width);
        e.setProperties(h);
        e.setPosition(startPoint);
        return e;
    }

    @Override
    public String toString() {
        return "Rectangle_" + number;
    }
}

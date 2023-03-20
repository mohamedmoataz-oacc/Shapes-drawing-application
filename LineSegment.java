package DrawingApplication;

import java.awt.*;
import java.awt.geom.*;
import java.util.HashMap;
import java.util.Map;

public class LineSegment implements Shape {
    double length;
    Point startPoint;
    Color color;
    static int count = 0;
    int number;

    LineSegment() {
        this.length = 0;
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
        length = properties.get("0");
    }

    @Override
    public Map<String, Double> getProperties() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("Length", length);
        return map;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getFillColor() {
        return color;
    }

    @Override
    public void draw(Graphics canvas) {
        Graphics2D canvas2d = (Graphics2D) canvas;
        double  x = startPoint.getX();
        double y = startPoint.getY();
        Line2D.Double line = new Line2D.Double(x, y, x + length, y + length);
        canvas2d.setColor(color);
        canvas2d.setStroke(new BasicStroke(3));
        canvas2d.draw(line);
    }

    @Override
    public Object clone() {
        LineSegment e = new LineSegment();
        e.setColor(color);
        HashMap<String, Double> h = new HashMap<>();
        h.put("0", length);
        e.setProperties(h);
        e.setPosition(startPoint);
        return e;
    }
    
    public String toString() {
        return "LineSegment_" + number;
    }
}

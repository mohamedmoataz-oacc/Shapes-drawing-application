package DrawingApplication;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Triangle implements Shape {
    double side;
    Point pt;
    Color outline, fillColor;
    static int count = 0;
    int number;

    Triangle() {
        this.side = 0;
        number = ++count;
    }

    @Override
    public void setPosition(Point position) {
        pt = position;
    }

    @Override
    public Point getPosition() {
        return pt;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        side = properties.get("0");
    }

    @Override
    public Map<String, Double> getProperties() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("Side", side);
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
        int s = (int) side;
        int x = (int) pt.getX();
        int y = (int) pt.getY();
        int hs = (int) Math.sqrt(Math.pow(s, 2) - Math.pow((s/2), 2));

        int[] xpts = new int[] {x, x + s, x + (s/2)};
        int[] ypts = new int[] {y, y, y - hs};
        canvas2d.setColor(fillColor);
        canvas2d.fillPolygon(xpts, ypts, 3);
        canvas2d.setColor(outline);
        canvas2d.drawPolygon(xpts, ypts, 3);
    }
    
    @Override
    public Object clone() {
        Triangle e = new Triangle();
        e.setColor(outline);
        e.setFillColor(fillColor);
        HashMap<String, Double> h = new HashMap<>();
        h.put("0", side);
        e.setProperties(h);
        e.setPosition(pt);
        return e;
    }

    @Override
    public String toString() {
        return "Triangle_" + number;
    }
}

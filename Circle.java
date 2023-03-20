package DrawingApplication;

import java.util.HashMap;
import java.util.Map;

public class Circle extends Ellipse {
    static int scount = 0;
    int number;

    Circle() {
        super();
        number = ++scount;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {height = width = properties.get("0") * 2;}

    @Override
    public Map<String, Double> getProperties() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("Radius", width/2);
        return map;
    }
    
    @Override
    public Object clone() {
        Circle e = new Circle();
        e.setColor(outline);
        e.setFillColor(fillColor);
        HashMap<String, Double> h = new HashMap<>();
        h.put("0", height / 2);
        e.setProperties(h);
        e.setPosition(center);
        return e;
    }

    @Override
    public String toString() {
        return "Circle_" + number;
    }
}

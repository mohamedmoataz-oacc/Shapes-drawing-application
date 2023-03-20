package DrawingApplication;

import java.util.HashMap;
import java.util.Map;

public class Square extends Rectangle {
    static int scount = 0;
    int number;

    Square() {
        super();
        number = ++scount;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        length = properties.get("0");
        width = properties.get("0");
    }

    @Override
    public Map<String, Double> getProperties() {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("Side", width);
        return map;
    }
    
    @Override
    public Object clone() {
        Square e = new Square();
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
        return "Square_" + number;
    }
}

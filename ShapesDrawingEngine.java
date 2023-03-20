package DrawingApplication;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javax.swing.JComponent;

public class ShapesDrawingEngine extends JComponent implements DrawingEngine {
    Stack<HashMap<Shape, Character>> to_undo = new Stack<>();
    Stack<HashMap<Shape, Character>> to_redo = new Stack<>();

    LinkedList<Class<? extends Shape>> supported_shapes = new LinkedList<>();
    LinkedList<Shape> shapes = new LinkedList<>();

    private Graphics2D g2d;
    HashMap<Shape, Character> undod, redod;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        
        for (Shape i: shapes) {i.draw(g2d);}
    }

    @Override
    public void addShape(Shape shape) {
        shapes.add(shape);
        HashMap<Shape, Character> added = new HashMap<>();
        added.put(shape, 'a');
        to_undo.add(added);
        to_redo = new Stack<>();
        repaint();
    }

    @Override
    public void removeShape(Shape shape) {
        shapes.remove(shape);
        HashMap<Shape, Character> removed = new HashMap<>();
        removed.put(shape, 'r');
        to_undo.add(removed);
        to_redo = new Stack<>();
        repaint();
    }

    @Override
    public Shape[] getShapes() {return (Shape[]) shapes.toArray();}

    @Override
    public void refresh(Graphics canvas) {repaint();}
    public void refresh() {refresh(g2d);}

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {return supported_shapes;}

    @Override
    public void installPluginShape(Class<? extends Shape> shapeClass) {supported_shapes.add(shapeClass);}

    @Override
    public void undo() {
        if (!to_undo.isEmpty()) {
            HashMap<Shape, Character> undo_shape = to_undo.pop();
            char value = (char) undo_shape.values().toArray()[0];
            Shape shape = (Shape) undo_shape.keySet().toArray()[0];

            HashMap<Shape, Character> addToRedo = new HashMap<>();
            if (value == 'a') {
                shapes.remove(shape);
                addToRedo.put(shape, 'r');
                to_redo.add(addToRedo);
            } else if (value == 'r') {
                shapes.add(shape);
                addToRedo.put(shape, 'a');
                to_redo.add(addToRedo);
            }
            undod = addToRedo;
        }
    }

    public HashMap<Shape, Character> getUndod() {return undod;}

    @Override
    public void redo() {
        if (!to_redo.isEmpty()) {
            HashMap<Shape, Character> redo_shape = to_redo.pop();
            char value = (char) redo_shape.values().toArray()[0];
            Shape shape = (Shape) redo_shape.keySet().toArray()[0];

            HashMap<Shape, Character> addToUndo = new HashMap<>();
            if (value == 'a') {
                shapes.remove(shape);
                addToUndo.put(shape, 'r');
                to_undo.add(addToUndo);
            } else if (value == 'r') {
                shapes.add(shape);
                addToUndo.put(shape, 'a');
                to_undo.add(addToUndo);
            }
            redod = addToUndo;
        }
    }

    public HashMap<Shape, Character> getRedod() {
        HashMap<Shape, Character> to_return = redod;
        redod = null;
        return to_return;
    }
    
}

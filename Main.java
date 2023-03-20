package DrawingApplication;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {

    static JButton circle, triangle, ellipse, rectangle, square, line, undo, redo, edit, copy, move, colorize, refresh, delete, plug;
    static JComboBox<Shape> select_shape;
    static ShapesDrawingEngine sde;
    static JPanel edit_panel, shapes_panel, color1, color2;
    static JLabel select;

    static Color button_color = new Color(245, 223, 77);
    static Color panel_color = new Color(0, 155, 119);
    
    public static void main(String[] args) throws InterruptedException {
        
        sde = new ShapesDrawingEngine();

        JFrame frame = new JFrame();

        edit_panel = new JPanel();
        shapes_panel = new JPanel();

        color1 = new JPanel();
        color1.setPreferredSize(new Dimension(450, 10));
        color1.setBackground(panel_color);
        color2 = new JPanel();
        color2.setPreferredSize(new Dimension(10, 500));
        color2.setBackground(panel_color);

        edit_panel.setPreferredSize(new Dimension(300, 500));
        edit_panel.setLayout(null);
        edit_panel.setBackground(panel_color);

        shapes_panel.setPreferredSize(new Dimension(450, 70));
        shapes_panel.setLayout(null);
        shapes_panel.setBackground(panel_color);

        circle = new JButton("Circle");
        circle.setFocusable(false);
        circle.setBackground(button_color);
        circle.setBounds(310, 20, 125, 30);
        circle.addActionListener(actionListener);

        triangle = new JButton("Triangle");
        triangle.setFocusable(false);
        triangle.setBackground(button_color);
        triangle.setBounds(435, 20, 125, 30);
        triangle.addActionListener(actionListener);

        ellipse = new JButton("Ellipse");
        ellipse.setFocusable(false);
        ellipse.setBackground(button_color);
        ellipse.setBounds(560, 20, 125, 30);
        ellipse.addActionListener(actionListener);

        rectangle = new JButton("Rectangle");
        rectangle.setFocusable(false);
        rectangle.setBackground(button_color);
        rectangle.setBounds(685, 20, 125, 30);
        rectangle.addActionListener(actionListener);

        square = new JButton("Square");
        square.setFocusable(false);
        square.setBackground(button_color);
        square.setBounds(810, 20, 125, 30);
        square.addActionListener(actionListener);

        line = new JButton("Line Segment");
        line.setFocusable(false);
        line.setBackground(button_color);
        line.setBounds(935, 20, 150, 30);
        line.addActionListener(actionListener);

        plug = new JButton("Draw another shape");
        plug.setFocusable(false);
        plug.setBackground(button_color);
        plug.setBounds(50, 15, 200, 40);
        plug.addActionListener(actionListener);

        undo = new JButton("Undo");
        undo.setFocusable(false);
        undo.setBackground(button_color);
        undo.setBounds(15, 10, 125,35);
        undo.addActionListener(actionListener);

        redo = new JButton("Redo");
        redo.setFocusable(false);
        redo.setBackground(button_color);
        redo.setBounds(155, 10, 125, 35);
        redo.addActionListener(actionListener);

        select = new JLabel("Select Shape");
        select.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        select.setBounds(15, 80, 125, 20);

        select_shape = new JComboBox<>();
        select_shape.setFocusable(false);
        select_shape.setBounds(15, 110, 265, 30);

        edit = new JButton("Edit");
        edit.setFocusable(false);
        edit.setBackground(button_color);
        edit.setBounds(15, 230, 125, 35);
        edit.addActionListener(actionListener);

        copy = new JButton("Copy");
        copy.setFocusable(false);
        copy.setBackground(button_color);
        copy.setBounds(155, 230, 125, 35);
        copy.addActionListener(actionListener);

        move = new JButton("Move");
        move.setFocusable(false);
        move.setBackground(button_color);
        move.setBounds(15, 280, 125, 35);
        move.addActionListener(actionListener);

        colorize = new JButton("Colorize");
        colorize.setFocusable(false);
        colorize.setBackground(button_color);
        colorize.setBounds(155, 280, 125, 35);
        colorize.addActionListener(actionListener);

        refresh = new JButton("Dark mode");
        refresh.setFocusable(false);
        refresh.setBackground(button_color);
        refresh.setBounds(15, 330, 125, 35);
        refresh.addActionListener(actionListener);

        delete = new JButton("Delete");
        delete.setFocusable(false);
        delete.setBackground(button_color);
        delete.setBounds(155, 330, 125, 35);
        delete.addActionListener(actionListener);

        shapes_panel.add(circle);
        shapes_panel.add(line);
        shapes_panel.add(square);
        shapes_panel.add(ellipse);
        shapes_panel.add(triangle);
        shapes_panel.add(rectangle);
        shapes_panel.add(plug);

        edit_panel.add(undo);
        edit_panel.add(redo);
        edit_panel.add(select);
        edit_panel.add(select_shape);
        edit_panel.add(edit);
        edit_panel.add(move);
        edit_panel.add(copy);
        edit_panel.add(colorize);
        edit_panel.add(refresh);
        edit_panel.add(delete);

        frame.setTitle("Vector Based Drawing Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 500);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(edit_panel, BorderLayout.WEST);
        frame.add(shapes_panel, BorderLayout.NORTH);
        frame.add(color1, BorderLayout.SOUTH);
        frame.add(color2, BorderLayout.EAST);
        frame.add(sde);
        
    }

    static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pick_outline) {
                outline_color = JColorChooser.showDialog(null, "Pick outline color", Color.BLACK);
                changed_outline = true;
            }
            if (e.getSource() == pick_fill) {
                fill_color = JColorChooser.showDialog(null, "Pick fill color", Color.BLACK);
                changed_fill = true;
            }
            if (e.getSource() == create) createNewShape();
            if (e.getSource() == plug) pluginPopup();
            if (e.getSource() == add) {
                Class cls = getLoadedShape();
                if (cls != null) {
                    newShapes.addItem(cls);
                    sde.installPluginShape(cls);
                }
            }
            if (e.getSource() == draw) {
                Class shapeClass = (Class) newShapes.getSelectedItem();
                
                Shape shape = Loader.getShape(shapeClass);

                if (shape != null) shapesPopup(shape, "all");
            }

            if (e.getSource() == circle) shapesPopup(new Circle(), "all");
            if (e.getSource() == rectangle) shapesPopup(new Rectangle(), "all");
            if (e.getSource() == triangle) shapesPopup(new Triangle(), "all");
            if (e.getSource() == square) shapesPopup(new Square(), "all");
            if (e.getSource() == line) shapesPopup(new LineSegment(), "all");
            if (e.getSource() == ellipse) shapesPopup(new Ellipse(), "all");

            if (e.getSource() == refresh) makeDark();
            if (e.getSource() == move) shapesPopup((Shape) select_shape.getSelectedItem(), "move");
            if (e.getSource() == colorize) shapesPopup((Shape) select_shape.getSelectedItem(), "colorize");
            if (e.getSource() == edit) shapesPopup((Shape) select_shape.getSelectedItem(), "edit");
            if (e.getSource() == copy) {
                Shape s = (Shape) select_shape.getSelectedItem();
                s = (Shape) s.clone();
                Point p = s.getPosition();
                double x = p.getX(), y = p.getY();
                s.setPosition(new Point((int) x + 150, (int) y));
                sde.addShape(s);
                select_shape.addItem(s);
            }
            if (e.getSource() == delete) {
                sde.removeShape((Shape) select_shape.getSelectedItem());
                select_shape.removeItemAt(select_shape.getSelectedIndex());
            }
            if (e.getSource() == undo) {
                sde.undo();
                HashMap<Shape, Character> undod = sde.getUndod();
                if (undod != null) {    
                    char value = (char) undod.values().toArray()[0];
                    Shape shape = (Shape) undod.keySet().toArray()[0];

                    if (value == 'a') select_shape.addItem(shape);
                    else if (value == 'r') select_shape.removeItem(shape);
                    sde.refresh();
                }
            }
            if (e.getSource() == redo) {
                sde.redo();
                HashMap<Shape, Character> redod = sde.getRedod();
                if (redod != null) {    
                    char value = (char) redod.values().toArray()[0];
                    Shape shape = (Shape) redod.keySet().toArray()[0];

                    if (value == 'a') select_shape.addItem(shape);
                    else if (value == 'r') select_shape.removeItem(shape);
                    sde.refresh();
                }
            }
        }
    };

    static JFrame frame1;
    static JButton create, pick_outline, pick_fill;
    static LinkedList<JTextField> lst = new LinkedList<>();
    static Color outline_color, fill_color;
    static Shape being_drawn; static String temp;
    static boolean changed_outline = false, changed_fill = false;

    public static void shapesPopup(Shape shape, String what) {
        lst = new LinkedList<>();
        being_drawn = shape;
        temp = what;

        frame1 = new JFrame();
        frame1.setTitle("Shape Properties");
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setLayout(null);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);

        int x = 20, y = 20;

        Set<String> prop = shape.getProperties().keySet();
        
        if (what.equals("all") || what.equals("edit")) {
            for (String i: prop) {
            JLabel l = new JLabel(i + ":");
            JTextField t = new JTextField();
            lst.add(t);
            t.setBounds(x + 150, y, 125, 25);
            l.setBounds(x, y, 125, 20);
            l.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
            frame1.add(l);
            frame1.add(t);
            y += 50;
            }
        }
        
        if (what.equals("move") || what.equals("all") || what.equals("edit")) {
            JLabel l1 = new JLabel("Point (x,y):");
            JTextField t1 = new JTextField();
            JTextField t2 = new JTextField();
            lst.add(t1);
            lst.add(t2);
            t1.setBounds(x + 150, y, 50, 25);
            t2.setBounds(x + 225, y, 50, 25);
            l1.setBounds(x, y, 125, 20);
            l1.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
            frame1.add(l1);
            frame1.add(t1);
            frame1.add(t2);
            y += 50;
        }

        if (what.equals("colorize") || what.equals("all") || what.equals("edit")) {
            JLabel l2 = new JLabel("Color:");
            pick_outline = new JButton("Pick a color");
            pick_outline.setBounds(x + 150, y, 125, 25);
            pick_outline.setFocusable(false);
            pick_outline.addActionListener(actionListener);
            l2.setBounds(x, y, 125, 20);
            l2.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
            frame1.add(l2);
            frame1.add(pick_outline);
            y += 50;
            if (!shape.toString().contains("LineSegment_")) {
                JLabel l3 = new JLabel("Fill Color:");
                pick_fill = new JButton("Pick a color");
                pick_fill.setBounds(x + 150, y, 125, 25);
                pick_fill.setFocusable(false);
                pick_fill.addActionListener(actionListener);
                l3.setBounds(x, y, 125, 20);
                l3.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
                frame1.add(l3);
                frame1.add(pick_fill);
                y += 80;
            }
        }

        if (what.equals("all")) create = new JButton("Create");
        else  create = new JButton("Save");
        create.setBounds(x + 100, y, 90, 25);
        create.setFocusable(false);
        create.addActionListener(actionListener);
        frame1.add(create);
        frame1.setSize(330, y + 90);
    }

    public static void createNewShape() {
        int sz = lst.size();
        for (int i = 0; i < sz; i++) {
            System.out.println("Text: " + lst.get(i).getText());
        }
        if (temp.equals("all") || temp.equals("edit")) {
            HashMap<String, Double> properties = new HashMap<>();
            for (int i = 0; i < sz-2; i++) {
                properties.put(""+i, Double.parseDouble(lst.get(i).getText()));
            }
            being_drawn.setProperties(properties);
        }
        if (temp.equals("all") || temp.equals("move") || temp.equals("edit"))
        being_drawn.setPosition(new Point(Integer.parseInt(lst.get(sz-2).getText()), Integer.parseInt(lst.getLast().getText())));
        if (temp.equals("all") || temp.equals("colorize") || temp.equals("edit")) {
            if (changed_outline) being_drawn.setColor(outline_color);
            if (changed_fill) being_drawn.setFillColor(fill_color);
        }

        if (temp.equals("all")) {
            sde.addShape(being_drawn);
            select_shape.addItem(being_drawn);
        } else sde.refresh();

        being_drawn = null;
        if (frame1 != null) frame1.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING));
        if (frame2 != null) frame2.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING));
        lst = new LinkedList<>();
        frame1 = null; create = null;
        pick_outline = null; pick_fill = null;
        outline_color = Color.BLACK; fill_color = Color.BLACK;
        changed_fill = false; changed_outline = false;
    }

    static JFrame frame2;
    static JButton add, draw;
    static JFileChooser path_chooser;
    static JComboBox<Class> newShapes = new JComboBox<>();;

    public static void pluginPopup() {
        frame2 = new JFrame();
        frame2.setTitle("Plugins");
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setLayout(null);
        frame2.setLocationRelativeTo(null);
        frame2.setSize(300, 350);
        frame2.setVisible(true);

        JLabel ls = new JLabel("Select a shape");
        ls.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        ls.setBounds(15, 10, 150, 25);
        frame2.add(ls);

        newShapes.setFocusable(false);
        newShapes.setBounds(15, 50, 265, 30);
        frame2.add(newShapes);

        add = new JButton("Add shape");
        add.setFocusable(false);
        add.setBounds(15, 260, 125, 35);
        add.addActionListener(actionListener);
        frame2.add(add);

        draw = new JButton("Draw shape");
        draw.setFocusable(false);
        draw.setBounds(155, 260, 125, 35);
        draw.addActionListener(actionListener);
        frame2.add(draw);
    }

    static boolean light_mode = true;
    public static void makeDark() {
        Color buttons;
        Color panels;
        Color label;
        if (light_mode) {
            buttons = Color.white;
            panels = Color.DARK_GRAY;
            label = Color.WHITE;
        } else {
            buttons = button_color;
            panels = panel_color;
            label = Color.BLACK;
        }
        circle.setBackground(buttons);
        triangle.setBackground(buttons);
        ellipse.setBackground(buttons);
        rectangle.setBackground(buttons);
        square.setBackground(buttons);
        line.setBackground(buttons);
        undo.setBackground(buttons);
        redo.setBackground(buttons);
        edit.setBackground(buttons);
        copy.setBackground(buttons);
        move.setBackground(buttons);
        colorize.setBackground(buttons);
        refresh.setBackground(buttons);
        delete.setBackground(buttons);
        plug.setBackground(buttons);
        select.setForeground(label);
        color1.setBackground(panels);
        color2.setBackground(panels);
        edit_panel.setBackground(panels);
        shapes_panel.setBackground(panels);
        if (light_mode) {
            refresh.setText("Green mode");
            light_mode = false;
        } else {
            refresh.setText("Dark mode");
            light_mode = true;
        }
    }

    public static Class getLoadedShape() {
        path_chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JAR files", "jar");
        path_chooser.setFileFilter(filter);

        int x = path_chooser.showOpenDialog(null);
        if (x == 0) {
            String y = path_chooser.getSelectedFile().getName();
            return Loader.loadNewClass(path_chooser.getSelectedFile(), y.substring(0, y.length() - 4));
        }
        return null;
    }
}
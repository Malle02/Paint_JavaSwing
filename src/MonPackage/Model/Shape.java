package MonPackage.Model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe abstraite représentant une forme générique.
 * Contient des propriétés communes et des méthodes de base
 * utilisées par toutes les formes dérivées.
 */
public abstract class Shape implements Serializable {
    protected Color color;
    protected int brushSize;
    protected ArrayList<Point> points;

    public Shape(Color color, int brushSize) {
        this.color = color;
        this.brushSize = brushSize;
        this.points = new ArrayList<>();
    }

    public abstract void draw(Graphics2D g);
   
    public void addPoint(Point point) {
        this.points.add(point);
    }
    
 
    public abstract boolean contains(Point p);
    
    public ArrayList<Point> getPoints() {
        return new ArrayList<>(points);
    }

    public abstract void resize(Point fixedPoint, Point movingPoint);
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
    }
}

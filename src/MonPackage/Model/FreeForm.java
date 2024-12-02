package MonPackage.Model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class FreeForm extends Shape implements Serializable {
    public FreeForm(Point startPoint, Color color, int brushSize) {
        super(color, brushSize);
        this.points.add(startPoint); // Commence par le premier point
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(brushSize));

        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public void addPoint(Point point) {
        this.points.add(point);  // Ajoute le point au dessin à main levée
    }

    
    @Override
    public boolean contains(Point p) {
   
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            double distance = Math.abs((p2.y - p1.y) * p.x - (p2.x - p1.x) * p.y + p2.x * p1.y - p2.y * p1.x)
                    / Math.sqrt(Math.pow(p2.y - p1.y, 2) + Math.pow(p2.x - p1.x, 2));
            if (distance <= brushSize) {
                return true;
            }
        }
        return false;
    }
    
    public void erase(Point cursorPoint, int brushSize) {

        Iterator<Point> iterator = points.iterator();
        while (iterator.hasNext()) {
            Point p = iterator.next();
            if (p.distance(cursorPoint) < brushSize) {
                iterator.remove();  // Supprime le point proche du curseur
            }
        }
    }
    
    
    @Override
    public void resize(Point fixedPoint, Point movingPoint) {
        
        double scaleX = (double) (movingPoint.x - fixedPoint.x) / (double) (points.get(0).x - fixedPoint.x);
        double scaleY = (double) (movingPoint.y - fixedPoint.y) / (double) (points.get(0).y - fixedPoint.y);

    
        for (Point point : points) {
            point.x = (int) (fixedPoint.x + (point.x - fixedPoint.x) * scaleX);
            point.y = (int) (fixedPoint.y + (point.y - fixedPoint.y) * scaleY);
        }
    }


}

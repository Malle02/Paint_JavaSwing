
package MonPackage.Model;

import java.awt.*;
import java.io.Serializable;

public class Triangle extends Shape implements Serializable  {
    private Point startPoint;
    private Point endPoint;

    public Triangle(Point startPoint, Color color, int brushSize) {
        super(color, brushSize);
        this.startPoint = startPoint;
        this.endPoint = startPoint; // Initialement, le triangle est un point
        this.points.add(startPoint); // Synchronisation avec les points
        this.points.add(endPoint);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(brushSize));

        int midX = (startPoint.x + endPoint.x) / 2;
        int[] xPoints = {startPoint.x, endPoint.x, midX};
        int[] yPoints = {endPoint.y, endPoint.y, startPoint.y};
        g.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void addPoint(Point point) {
        this.endPoint = point; // point d'extrémité pour le triangle
        if (points.size() > 1) {
            this.points.set(1, endPoint); // Met à jour la liste des points
        } else {
            this.points.add(endPoint);
        }
    }

    @Override
    public boolean contains(Point p) {
        if (points.size() < 2) return false;
        Point a = points.get(0);
        Point b = points.get(1);
        Point c = new Point((a.x + b.x) / 2, a.y);


        double totalArea = Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0;
        double area1 = Math.abs(p.x * (b.y - c.y) + b.x * (c.y - p.y) + c.x * (p.y - b.y)) / 2.0;
        double area2 = Math.abs(a.x * (p.y - c.y) + p.x * (c.y - a.y) + c.x * (a.y - p.y)) / 2.0;
        double area3 = Math.abs(a.x * (b.y - p.y) + b.x * (p.y - a.y) + p.x * (a.y - b.y)) / 2.0;

        return Math.abs(totalArea - (area1 + area2 + area3)) < 1e-2;
    }

    // Gomme mais pas optimal 
    public void erase(Point cursorPoint, int brushSize) {
        
        int x1 = startPoint.x, y1 = startPoint.y;
        int x2 = endPoint.x, y2 = endPoint.y;
        int x3 = (startPoint.x + endPoint.x) / 2, y3 = startPoint.y;

       
        int denominator = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        int a = ((y2 - y3) * (cursorPoint.x - x3) + (x3 - x2) * (cursorPoint.y - y3)) / denominator;
        int b = ((y3 - y1) * (cursorPoint.x - x3) + (x1 - x3) * (cursorPoint.y - y3)) / denominator;
        int c = 1 - a - b;

        if (a >= 0 && b >= 0 && c >= 0) {
           
            startPoint = new Point(cursorPoint.x - brushSize, cursorPoint.y - brushSize);  
        }
    }
    
    
    
    //retrecie 
    @Override
    public void resize(Point fixedPoint, Point movingPoint) {
        int index = points.indexOf(fixedPoint);
        if (index != -1) {
            points.set(index, movingPoint);
        }
    }


}

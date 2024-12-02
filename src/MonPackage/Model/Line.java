

package MonPackage.Model;

import java.awt.*;
import java.io.Serializable;

public class Line extends Shape implements Serializable {
    private Point startPoint;
    private Point endPoint;

    public Line(Point startPoint, Color color, int brushSize) {
        super(color, brushSize);
        this.startPoint = startPoint;
        this.endPoint = startPoint;
        this.points.add(startPoint); // Synchronisation avec la liste des points
        this.points.add(endPoint);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(brushSize));
        g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    @Override
    public void addPoint(Point point) {
        this.endPoint = point;
        this.points.set(1, endPoint); // Met à jour la liste des points
    }

   
    @Override
    public boolean contains(Point p) {
    	
    	  double distance = Math.abs((endPoint.y - startPoint.y) * p.x - (endPoint.x - startPoint.x) * p.y +
                  endPoint.x * startPoint.y - endPoint.y * startPoint.x) / startPoint.distance(endPoint);
          return distance <= brushSize;
    }


    public void erase(Point cursorPoint, int brushSize) {
        int newX = cursorPoint.x - brushSize;
        int newY = cursorPoint.y - brushSize;
        this.endPoint = new Point(newX, newY);  
    }
    
    @Override
    public void resize(Point fixedPoint, Point movingPoint) {
        if (points.get(0).equals(fixedPoint)) {
            points.set(1, movingPoint);
        } else {
            points.set(0, movingPoint);
        }
    }



}

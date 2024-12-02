
package MonPackage.Model;

import java.awt.*;
import java.io.Serializable;

public class Cercle extends Shape implements Serializable  {
    private Point centerPoint;
    private Point radiusPoint;

    public Cercle(Point centerPoint, Color color, int brushSize) {
        super(color, brushSize);
        this.centerPoint = centerPoint;
        this.radiusPoint = centerPoint;
        this.points.add(centerPoint); 
        this.points.add(radiusPoint);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(brushSize));
        int radius = (int) centerPoint.distance(radiusPoint);
        int x = centerPoint.x - radius;
        int y = centerPoint.y - radius;
        g.drawOval(x, y, radius * 2, radius * 2);
    }

    @Override
    public void addPoint(Point point) {
        this.radiusPoint = point;
        this.points.set(1, radiusPoint); 
    }

   
    @Override
    public boolean contains(Point p) {
    	 int radius = (int) centerPoint.distance(radiusPoint);
         return centerPoint.distance(p) <= radius;
    }

    public void erase(Point cursorPoint, int brushSize) {
        int radius = (int) centerPoint.distance(radiusPoint);
        double distance = centerPoint.distance(cursorPoint);

        if (distance < radius) {
            // Réduire le rayon du cercle sous la gomme
            radius -= brushSize;
            if (radius > 0) {
                radiusPoint = new Point(centerPoint.x + radius, centerPoint.y);
            }
        }
    }
    
    @Override
    public void resize(Point fixedPoint, Point movingPoint) {
        points.set(1, movingPoint); 
    }



}

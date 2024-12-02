
package MonPackage.Model;

import java.awt.*;
import java.io.Serializable;

public class Rectangle extends Shape implements Serializable  {
    private Point startPoint;
    private Point endPoint;

    public Rectangle(Point startPoint, Color color, int brushSize) {
        super(color, brushSize);
        this.startPoint = startPoint;
        this.endPoint = startPoint;
        this.points.add(startPoint); 
        this.points.add(endPoint);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(brushSize));
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void addPoint(Point point) {
        this.endPoint = point;
        this.points.set(1, endPoint); 
    }

   
    @Override
    public boolean contains(Point p) {
    	
    	 int x = Math.min(startPoint.x, endPoint.x);
         int y = Math.min(startPoint.y, endPoint.y);
         int width = Math.abs(startPoint.x - endPoint.x);
         int height = Math.abs(startPoint.y - endPoint.y);
         return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
    }



    public void erase(Point cursorPoint, int brushSize) {
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        // Efface progressivement une petite portion du rectangle
        if (cursorPoint.x >= x && cursorPoint.x <= x + width &&
            cursorPoint.y >= y && cursorPoint.y <= y + height) {
            // Réduire progressivement la taille du rectangle sous la gomme
            if (cursorPoint.x < x + width && cursorPoint.y < y + height) {
                endPoint = new Point(cursorPoint.x - brushSize, cursorPoint.y - brushSize);
            }
        }
    }
    
    @Override
    public void resize(Point fixedPoint, Point movingPoint) {
        
        points.set(0, fixedPoint); 
        points.set(1, movingPoint); 
    }



}

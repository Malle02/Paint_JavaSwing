package MonPackage.Model;

import java.awt.*;
import javax.swing.ImageIcon;

public class ImageShape extends Shape {
    private ImageIcon image;
    private Point position;

    public ImageShape(ImageIcon image, Point position) {
        super(null, 0);
        this.image = image;
        this.position = position;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image.getImage(), position.x, position.y, null);
    }

    @Override
    public boolean contains(Point p) {
        return new java.awt.Rectangle(position.x, position.y, image.getIconWidth(), image.getIconHeight()).contains(p);
    }

    @Override
    public void resize(Point fixedPoint, Point movingPoint) {
        
    	
    }

    public void move(Point delta) {
        position.translate(delta.x, delta.y);
    }

    public Point getPosition() {
        return position;
    }
}

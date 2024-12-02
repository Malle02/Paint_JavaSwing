//package MonPackage.Model;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.SwingConstants;
//
//public class TextShape extends Shape {
//    private Point position; // Position du texte
//    private String text;    // Contenu du texte
//    private Font font;      // Style de la police
//    private int alignment;  // Alignement du texte (SwingConstants.LEFT, CENTER, RIGHT)
//
//    public TextShape(Point position, String text, Font font, Color color, int alignment) {
//        super(color, font.getSize());
//        this.position = position;
//        this.text = text;
//        this.font = font;
//        this.alignment = alignment;
//    }
//
//    @Override
//    public void draw(Graphics2D g) {
//        g.setColor(color);
//        g.setFont(font);
//
//        FontMetrics metrics = g.getFontMetrics(font);
//        int textWidth = metrics.stringWidth(text);
//        int x = position.x;
//
//        // Ajuster la position selon l'alignement
//        if (alignment == SwingConstants.CENTER) {
//            x -= textWidth / 2;
//        } else if (alignment == SwingConstants.RIGHT) {
//            x -= textWidth;
//        }
//
//        g.drawString(text, x, position.y);
//    }
//
//    @Override
//    public void addPoint(Point point) {
//        this.position = point; // Met à jour la position du texte
//    }
//
//    @Override
//    public boolean contains(Point p) {
//        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
//        int textWidth = metrics.stringWidth(text);
//        int textHeight = metrics.getHeight();
//        int x = position.x;
//        int y = position.y - textHeight; // Ajuste pour inclure la hauteur
//        return (p.x >= x && p.x <= x + textWidth && p.y >= y && p.y <= y + textHeight);
//    }
//
//    // Ajoutez ces accesseurs pour fournir les informations nécessaires
//    public Font getFont() {
//        return font;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public Point getPosition() {
//        return position;
//    }
//}




package MonPackage.Model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

public class TextShape extends Shape implements Serializable {
	private Point position;
    private String text;   
    private Font font;     
    private int alignment; 

    public TextShape(Point position, String text, Font font, Color color, int alignment) {
    	 super(color, font.getSize());
         this.position = position;
         this.text = text;
         this.font = font;
         this.alignment = alignment;
         this.points.add(position); 
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int x = position.x;

        // Ajuste la position selon l'alignement
        if (alignment == SwingConstants.CENTER) {
            x -= textWidth / 2;
        } else if (alignment == SwingConstants.RIGHT) {
            x -= textWidth;
        }

        g.drawString(text, x, position.y);
    }

    @Override
    public void addPoint(Point point) {
    	 this.position = point; // Met à jour la position du texte
         if (points.isEmpty()) {
             this.points.add(point);
         } else {
             this.points.set(0, point);
         }
     
    }

    @Override
    public boolean contains(Point p) {
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

       
        int x = position.x;
        if (alignment == SwingConstants.CENTER) {
            x -= textWidth / 2;
        } else if (alignment == SwingConstants.RIGHT) {
            x -= textWidth;
        }
        int y = position.y - textHeight;

        
        return (p.x >= x && p.x <= x + textWidth && p.y >= y && p.y <= position.y);
    }



    
    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public Point getPosition() {
        return position;
    }
    
    @Override
    public void resize(Point fixedPoint, Point movingPoint) {
        int newSize = Math.max(10, (int) fixedPoint.distance(movingPoint)); 
        this.font = new Font(font.getFontName(), font.getStyle(), newSize);
    }

    
    
    public void setText(String text) {
        this.text = text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

}

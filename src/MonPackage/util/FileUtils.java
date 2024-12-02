package MonPackage.util;
import MonPackage.Model.Shape;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class FileUtils {

    // Sauvegarde au format binaire
    public static void saveShapesToFile(List<Shape> shapes, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(shapes);
        }
    }

    // Chargement des formes depuis un fichier binaire
    public static List<Shape> loadShapesFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Shape>) ois.readObject();
        }
    }
        
        public static void saveAsImage(Component component, File file) throws IOException {
            BufferedImage image = new BufferedImage(
                component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            component.paint(g2d);  
            g2d.dispose();
            ImageIO.write(image, "png", file);  // Sauvegarde au format PNG
        
    }
    
    
      
        
        public static ImageIcon loadImage(File file) {
            return new ImageIcon(file.getAbsolutePath());
        }

    
}
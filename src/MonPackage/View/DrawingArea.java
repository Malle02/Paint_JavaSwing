
package MonPackage.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import MonPackage.Model.Cercle;
import MonPackage.Model.FreeForm;
import MonPackage.Model.Line;
import MonPackage.Model.Shape;
import MonPackage.Model.TextShape;
import MonPackage.Model.Triangle;
import MonPackage.Model.Rectangle;
import MonPackage.Model.ImageShape;


import java.util.List;


public class DrawingArea extends JPanel {
    private Color currentColor = Color.BLACK;
    private int brushSize = 5;
    private ArrayList<Shape> shapes = new ArrayList<>();
    private ArrayList<Shape> undoStack = new ArrayList<>();
    private ArrayList<Shape> redoStack = new ArrayList<>();
    private Shape currentShape;
    private String currentTool = "Libre";
    private boolean eraserMode = false;
    
   
//  Redimentionnement mais ne marche pas correctement 
    private boolean isResizing = false;
    private Point resizingPoint = null;

    
    
    //depalcement des formes et  texte 

    private Shape selectedShape = null; 
    private Point lastMousePosition = null; 

    public DrawingArea() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(800, 600));
        this.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        	    if (currentTool.equals("Sélection")) {
        	        selectedShape = null; 
        	        lastMousePosition = e.getPoint();
        	        resizingPoint = null; 

        	        // Vérifie si l'utilisateur clique sur un point de contrôle, c'etait pour la redimentione mais je ne suis pas parvenue
        	        if (selectedShape != null) {
        	            for (Point point : selectedShape.getPoints()) {
        	            	java.awt.Rectangle controlPoint = new java.awt.Rectangle(point.x - 5, point.y - 5, 10, 10);
        	                if (controlPoint.contains(lastMousePosition)) {
        	                    resizingPoint = point;
        	                    isResizing = true;
        	                    break;
        	                }
        	            }
        	        }

        	        // Sinon, sélectionner une forme
        	        if (!isResizing) {
        	            for (int i = shapes.size() - 1; i >= 0; i--) {
        	                Shape shape = shapes.get(i);
        	                if (shape.contains(lastMousePosition)) {
        	                    selectedShape = shape;
        	                    break;
        	                }
        	            }
        	        }
        	        repaint();
        	    }
        	
        	        	
        	 else if (currentTool.equals("Texte 📝")) {
        	        configureTextAndAdd(e.getPoint());
        	    } else if (eraserMode) {
        	        eraseShapeUnderCursor(e.getPoint());
        	    } else {
        	        // Logique pour dessiner de nouvelles formes
        	        switch (currentTool) {
        	            case "Rectangle ▭":
        	                currentShape = new Rectangle(e.getPoint(), currentColor, brushSize);
        	                break;
        	            case "Cercle ⚫":
        	                currentShape = new Cercle(e.getPoint(), currentColor, brushSize);
        	                break;
        	            case "Ligne ➖":
        	                currentShape = new Line(e.getPoint(), currentColor, brushSize);
        	                break;
        	            case "Triangle 🔺":
        	                currentShape = new Triangle(e.getPoint(), currentColor, brushSize);
        	                break;
        	            default:
        	                currentShape = new FreeForm(e.getPoint(), currentColor, brushSize);
        	                break;
        	        }
        	    }
        	}
        	@Override
        	public void mouseMoved(MouseEvent e) {
        	    if (currentTool.equals("Sélection")) {
        	        boolean overControlPoint = false;

        	        if (selectedShape != null) {
        	            for (Point p : selectedShape.getPoints()) {
        	            	java.awt.Rectangle controlPoint = new java.awt.Rectangle(p.x - 5, p.y - 5, 10, 10);

        	                if (controlPoint.contains(e.getPoint())) {
        	                    
        	                    if (p.equals(selectedShape.getPoints().get(0))) {
        	                        setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR)); 
        	                    } else if (p.equals(selectedShape.getPoints().get(1))) {
        	                        setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR)); 
        	                    } else {
        	                        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); 
        	                    }
        	                    overControlPoint = true;
        	                    break;
        	                }
        	            }
        	        }

        	       
        	        if (!overControlPoint) {
        	            setCursor(Cursor.getDefaultCursor());
        	        }
        	    }
        	}

        	
        	
            @Override
            
      
         
            public void mouseReleased(MouseEvent e) {
                if (isResizing) {
                    isResizing = false;
                    resizingPoint = null;
                    setCursor(Cursor.getDefaultCursor());
                }

                if (currentShape != null && !eraserMode) {
                    shapes.add(currentShape);
                    undoStack.add(currentShape);
                    redoStack.clear();
                    currentShape = null;
                    repaint();
                }
            }

        });
        
        
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentTool.equals("Sélection")) {
                    boolean overControlPoint = false;

                    // Vérifie si la souris est sur un point de contrôle
                    if (selectedShape != null) {
                        for (Point p : selectedShape.getPoints()) {
                        	java.awt.Rectangle controlPoint = new java.awt.Rectangle(p.x - 5, p.y - 5, 10, 10);
                            if (controlPoint.contains(e.getPoint())) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                                overControlPoint = true;
                                break;
                            }
                        }
                    }

                    // Revenient au curseur par défaut si la souris n'est pas sur un point de contrôle
                    if (!overControlPoint) {
                        setCursor(Cursor.getDefaultCursor());
                    }
                }
            }
        });


        this.addMouseMotionListener(new MouseAdapter() {
        	@Override
        	public void mouseDragged(MouseEvent e) {
        	    Point currentMousePosition = e.getPoint();

        	    if (currentTool.equals("Sélection") && selectedShape != null) {
        	        if (isResizing && resizingPoint != null) {
        	            // Redimensionnement de la forme 
        	            selectedShape.resize(resizingPoint, currentMousePosition);
        	        } else {
        	            // Déplacement de la forme
        	            int dx = currentMousePosition.x - lastMousePosition.x;
        	            int dy = currentMousePosition.y - lastMousePosition.y;

        	            if (selectedShape instanceof ImageShape) {
        	                // Si la forme sélectionnée est une image, déplace son point d'origine
        	                ((ImageShape) selectedShape).move(new Point(dx, dy));
        	            } else {
        	                // Pour d'autres formes, déplace leurs points
        	                for (Point p : selectedShape.getPoints()) {
        	                    p.translate(dx, dy);
        	                }
        	            }

        	            // Mettre à jour la dernière position de la souris
        	            lastMousePosition = currentMousePosition;
        	        }

        	       
        	        repaint();
        	    } else if (eraserMode) {
        	        // Mode gomme : efface les formes touchées => mais pas tres optimale
        	        eraseShapeUnderCursor(currentMousePosition);
        	    } else if (currentShape != null) {
        	       
        	        currentShape.addPoint(currentMousePosition);
        	        repaint();
        	    }
        	}

        });

        this.addKeyListener(getKeyboardShortcuts());
        this.setFocusable(true);
        
    }
    
        
    
    //@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

    
        for (Shape shape : shapes) {
            shape.draw(g2d);

           
            if (shape == selectedShape) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(2));

                if (shape instanceof TextShape) {
                    FontMetrics metrics = g2d.getFontMetrics(((TextShape) shape).getFont());
                    String text = ((TextShape) shape).getText();
                    if (text != null && !text.isEmpty()) {
                        int textWidth = metrics.stringWidth(text);
                        int textHeight = metrics.getHeight();
                        Point position = ((TextShape) shape).getPosition();
                        g2d.drawRect(position.x, position.y - textHeight, textWidth, textHeight);
                    }
                } else {
                    if (!shape.getPoints().isEmpty()) {
                        for (Point p : shape.getPoints()) {
                            g2d.fillRect(p.x - 5, p.y - 5, 10, 10); // Petits carrés rouges pour les points de contrôle
                        }
                    }
                }
            }
        }

       
        if (currentShape != null) {
            currentShape.draw(g2d);
        }
    }

    // Effacement partiel (modification progressive) mais ne marche pas tres bien 
    
    private void eraseShapeUnderCursor(Point cursorPoint) {
        ArrayList<Shape> shapesToModify = new ArrayList<>();

       
        for (Shape shape : shapes) {
            if (shape.contains(cursorPoint)) {
                shapesToModify.add(shape);
            }
        }


        for (Shape shape : shapesToModify) {
            if (shape instanceof Rectangle) {
                ((Rectangle) shape).erase(cursorPoint, brushSize);  
            } else if (shape instanceof Cercle) {
                ((Cercle) shape).erase(cursorPoint, brushSize);  
                ((Line) shape).erase(cursorPoint, brushSize);  
                ((FreeForm) shape).erase(cursorPoint, brushSize);  
            }
        }
        repaint();
    }

   
    //Sauvegarde
    
    public List<Shape> getShapes() {
        return shapes;
    }

    
    
    // edition de texte 
    
    
    public void editSelectedText() {
        if (selectedShape instanceof TextShape) {
            TextShape textShape = (TextShape) selectedShape;

            // Utilise TextStylePanel pour modifier les propriétés du texte
            TextStylePanel textStylePanel = new TextStylePanel();
            textStylePanel.getTextField().setText(textShape.getText());
            textStylePanel.getFontSelector().setSelectedItem(textShape.getFont().getFontName());
            textStylePanel.getFontSizeSpinner().setValue(textShape.getFont().getSize());
            textStylePanel.getBoldCheckBox().setSelected(textShape.getFont().isBold());
            textStylePanel.getItalicCheckBox().setSelected(textShape.getFont().isItalic());
            textStylePanel.getColorPickerButton().setBackground(textShape.getColor());

            int result = JOptionPane.showConfirmDialog(this, textStylePanel, "Modifier le texte", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String newText = textStylePanel.getText();
                int fontSize = (int) textStylePanel.getFontSizeSpinner().getValue();
                String fontFamily = (String) textStylePanel.getFontSelector().getSelectedItem();
                int fontStyle = textStylePanel.getFontStyle();
                Color newColor = textStylePanel.getSelectedColor();
                int alignment = textStylePanel.getTextAlignment();

                Font newFont = new Font(fontFamily, fontStyle, fontSize);
                textShape.setText(newText);
                textShape.setFont(newFont);
                textShape.setColor(newColor);
                textShape.setAlignment(alignment);

                repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un texte pour le modifier.");
        }
    }


    public void setShapes(List<Shape> newShapes) {
        shapes.clear();
        shapes.addAll(newShapes);
        repaint();
    }


    public void setColor(Color color) {
        this.currentColor = color;
    }

    public void setBrushSize(int size) {
        this.brushSize = size;
    }

    public void setCurrentTool(String tool) {
        this.currentTool = tool;
        this.eraserMode = false;
        setCursor(Cursor.getDefaultCursor()); 
        if (tool.equals("Gomme")) {
            this.eraserMode = true;
            ImageIcon eraserIcon = new ImageIcon(getClass().getResource("/MonPackage/resources/Icon/images.png"));
            if (eraserIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
                System.out.println("Erreur : L'icône de gomme est introuvable.");
                setCursor(Cursor.getDefaultCursor());
            } else {
                setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                        eraserIcon.getImage(), new Point(0, 0), "eraser"));
            }
        }
    }
    
//    Image pour le cursseur 

    public void toggleEraser() {
        this.eraserMode = !this.eraserMode;
        if (eraserMode) {
            ImageIcon eraserIcon = new ImageIcon(getClass().getResource("/MonPackage/resources/Icon/images.png"));
            if (eraserIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
                System.out.println("Erreur : L'icône de gomme est introuvable.");
                setCursor(Cursor.getDefaultCursor());
            } else {
                setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                        eraserIcon.getImage(), new Point(0, 0), "eraser"));
            }
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    
    
    public void undo() {
        if (!undoStack.isEmpty()) {
            Shape lastAction = undoStack.remove(undoStack.size() - 1);
            
            if (!shapes.contains(lastAction)) {
                
                shapes.add(lastAction);
                System.out.println("Annulation : Forme restaurée.");
            } else {
               
                shapes.remove(lastAction);
                redoStack.add(lastAction); 
                System.out.println("Annulation : Forme retirée.");
            }
        } else {
            System.out.println("Aucune action à annuler.");
        }
        repaint();
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Shape lastRedo = redoStack.remove(redoStack.size() - 1);
            
            if (!shapes.contains(lastRedo)) {
               
                shapes.add(lastRedo);
                System.out.println("Rétablissement : Forme restaurée.");
            } else {
               
                shapes.remove(lastRedo);
                undoStack.add(lastRedo); 
                System.out.println("Rétablissement : Forme retirée.");
            }
        } else {
            System.out.println("Aucune action à rétablir.");
        }
        repaint();
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void deleteSelectedShape() {
        if (selectedShape != null) {
            if (shapes.contains(selectedShape)) {
                undoStack.add(selectedShape); 
                redoStack.clear(); // Réinitialise le Redo Stack
                shapes.remove(selectedShape); // Supprime la forme
                System.out.println("Forme supprimée : " + selectedShape.getClass().getSimpleName());
                selectedShape = null; // Réinitialise la sélection
            } else {
                System.out.println("La forme sélectionnée n'est pas trouvée dans la liste des formes.");
            }
        } else {
            System.out.println("Aucune forme sélectionnée pour suppression.");
        }
        repaint();
    }

    
    
    // texte  -------------------------------
    
    private void configureTextAndAdd(Point position) {
        TextStylePanel textStylePanel = new TextStylePanel();
        int result = JOptionPane.showConfirmDialog(this, textStylePanel, "Configurer le texte", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String text = textStylePanel.getText();
            if (text != null && !text.trim().isEmpty()) {
                int fontSize = textStylePanel.getFontSize();
                String fontFamily = textStylePanel.getFontFamily();
                int fontStyle = textStylePanel.getFontStyle();
                int alignment = textStylePanel.getTextAlignment();
                Color color = textStylePanel.getSelectedColor();

                Font font = new Font(fontFamily, fontStyle, fontSize);
                shapes.add(new TextShape(position, text, font, color, alignment));
                undoStack.add(shapes.get(shapes.size() - 1)); 
                redoStack.clear();
                repaint();
            }
        }
    }

    
    //image 
    


    public void addImageShape(ImageIcon image) {
        Point startPoint = new Point(100, 100); // Position par défaut de l'image
        ImageShape imageShape = new ImageShape(image, startPoint);
        shapes.add(imageShape); // Ajoute l'image comme une forme
        repaint();
    }

//    backgrtound : 
    
    public void setBackgroundColor(Color color) {
        this.setBackground(color);
        repaint();
    }

    
    
//    le clavier mais ne marche pas correctement aussi 


    public KeyAdapter getKeyboardShortcuts() {
        return new KeyAdapter() {
        	
        
        	@Override
        	public void keyPressed(KeyEvent e) {
        	    if (currentTool.equals("Sélection") && e.getKeyCode() == KeyEvent.VK_DELETE) {
        	        if (selectedShape != null) {
        	            System.out.println("Suppression via clavier de la forme : " + selectedShape.getClass().getSimpleName());
        	            deleteSelectedShape();
        	        } else {
        	            System.out.println("Aucune forme sélectionnée pour suppression via clavier.");
        	        }
        	    } else if (e.isControlDown()) {
        	        switch (e.getKeyCode()) {
        	            case KeyEvent.VK_Z -> undo();
        	            case KeyEvent.VK_Y -> redo();
        	        }
        	    }

        	   
        	    switch (e.getKeyCode()) {
        	        case KeyEvent.VK_T -> setCurrentTool("Triangle 🔺");
        	        case KeyEvent.VK_R -> setCurrentTool("Rectangle ▭");
        	        case KeyEvent.VK_C -> setCurrentTool("Cercle ⚫");
        	        case KeyEvent.VK_L -> setCurrentTool("Ligne ➖");
        	        case KeyEvent.VK_F -> setCurrentTool("Libre ✏️");
        	        case KeyEvent.VK_G -> setCurrentTool("Gomme");
        	        case KeyEvent.VK_E -> editSelectedText();

        	    }
      
            }
        };
    }
}

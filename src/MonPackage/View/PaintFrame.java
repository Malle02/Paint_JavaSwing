
package MonPackage.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import MonPackage.Model.Shape;
import MonPackage.util.FileUtils;

public class PaintFrame extends JFrame {
    private DrawingArea drawingArea;

    public PaintFrame() {
        this.setTitle("PaintIt Modern 🎨");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Zone de dessin
        drawingArea = new DrawingArea();
        this.add(drawingArea, BorderLayout.CENTER);

        // Barre latérale
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(ThemeManager.getPanelColor());
        sidebar.setPreferredSize(new Dimension(350, getHeight()));

        // Palette de couleurs
        sidebar.add(createColorPanel());

        // Section outils
        sidebar.add(createToolsPanel());

        // Section pinceau
        sidebar.add(createBrushPanel());

        // Options avancées
        sidebar.add(createAdvancedOptionsPanel());

        this.add(sidebar, BorderLayout.WEST);

        // Gestion des raccourcis clavier
        setupKeyboardShortcuts();

     
        applyTheme();
    }

    private JPanel createColorPanel() {
        JPanel colorPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        colorPanel.setBorder(BorderFactory.createTitledBorder("🎨 Palette de Couleurs"));
        colorPanel.setBackground(ThemeManager.getPanelColor());
        List<Color> colors = List.of(Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK);

        for (Color color : colors) {
            JButton colorButton = new JButton();
            colorButton.setBackground(color);
            colorButton.setPreferredSize(new Dimension(30, 30));
            colorButton.addActionListener(e -> drawingArea.setColor(color));
            colorPanel.add(colorButton);
        }

        JButton customColorButton = new JButton("🎨 Perso");
        customColorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choisissez une couleur", Color.BLACK);
            if (color != null) drawingArea.setColor(color);
        });
        colorPanel.add(customColorButton);

        return colorPanel;
    }

    private JPanel createToolsPanel() {
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));
        toolsPanel.setBorder(BorderFactory.createTitledBorder("🛠️ Outils de Dessin"));
        toolsPanel.setBackground(ThemeManager.getPanelColor());

        // Section : Formes
        JPanel shapeToolsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        shapeToolsPanel.setBorder(BorderFactory.createTitledBorder("📐 Formes"));
        shapeToolsPanel.setBackground(ThemeManager.getPanelColor());
        
        String[] shapeTools = {"Libre ✏️", "Rectangle ▭", "Cercle ⚫", "Ligne ➖", "Triangle 🔺", "Texte 📝"};
        for (String tool : shapeTools) {
            JButton toolButton = new JButton(tool);
            toolButton.setBackground(ThemeManager.getButtonColor());
            toolButton.addActionListener(e -> drawingArea.setCurrentTool(tool));
            shapeToolsPanel.add(toolButton);
        }

        // Section : Manipulation
        JPanel manipulationToolsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        manipulationToolsPanel.setBorder(BorderFactory.createTitledBorder("✋ Manipulation"));
        manipulationToolsPanel.setBackground(ThemeManager.getPanelColor());
        
        JButton selectButton = new JButton("Sélection");
        selectButton.setBackground(ThemeManager.getButtonColor());
        selectButton.addActionListener(e -> drawingArea.setCurrentTool("Sélection"));
        manipulationToolsPanel.add(selectButton);

        JButton eraserButton = new JButton("🩹 Gomme");
        eraserButton.addActionListener(e -> drawingArea.toggleEraser());
        manipulationToolsPanel.add(eraserButton);

        // Section : Image
        JPanel imageToolsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        imageToolsPanel.setBorder(BorderFactory.createTitledBorder("🖼️ Images"));
        imageToolsPanel.setBackground(ThemeManager.getPanelColor());
        
        JButton loadImageButton = new JButton("📷 Importer Image");
        loadImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int choice = fileChooser.showOpenDialog(this);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                ImageIcon imageIcon = FileUtils.loadImage(file);
                drawingArea.addImageShape(imageIcon);
            }
        });
        imageToolsPanel.add(loadImageButton);

        JButton editTextButton = new JButton("✏️ Modifier Texte");
        editTextButton.addActionListener(e -> drawingArea.editSelectedText());
        imageToolsPanel.add(editTextButton);

        // Ajouter les sous-sections dans le panneau principal des outils
        toolsPanel.add(shapeToolsPanel);
        toolsPanel.add(manipulationToolsPanel);
        toolsPanel.add(imageToolsPanel);

        return toolsPanel;
    }

    private JPanel createBrushPanel() {
        JPanel brushPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        brushPanel.setBorder(BorderFactory.createTitledBorder("🖌️ Taille du pinceau"));
        brushPanel.setBackground(ThemeManager.getPanelColor());

        JSlider brushSizeSlider = new JSlider(1, 20, 5);
        brushSizeSlider.addChangeListener(e -> drawingArea.setBrushSize(brushSizeSlider.getValue()));
        brushPanel.add(new JLabel("Taille :"));
        brushPanel.add(brushSizeSlider);

        return brushPanel;
    }

    private JPanel createAdvancedOptionsPanel() {
        JPanel advancedPanel = new JPanel();
        advancedPanel.setLayout(new GridLayout(4, 1, 5, 5));
        advancedPanel.setBorder(BorderFactory.createTitledBorder("⚙️ Options Avancées"));
        advancedPanel.setBackground(ThemeManager.getPanelColor());

        
        JButton backgroundColorButton = new JButton("🎨 Changer Fond");
        backgroundColorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choisissez une couleur de fond", drawingArea.getBackground());
            if (color != null) {
                drawingArea.setBackgroundColor(color);
            }
        });
        advancedPanel.add(backgroundColorButton);

        
        JButton undoButton = new JButton("↩️ Annuler");
        undoButton.addActionListener(e -> drawingArea.undo());
        advancedPanel.add(undoButton);

        JButton redoButton = new JButton("↪️ Refaire");
        redoButton.addActionListener(e -> drawingArea.redo());
        advancedPanel.add(redoButton);

        JButton deleteButton = new JButton("🗑️ Supprimer");
        deleteButton.addActionListener(e -> drawingArea.deleteSelectedShape());
        advancedPanel.add(deleteButton);

        JButton saveButton = new JButton("💾 Sauvegarder");
        saveButton.addActionListener(e -> saveDrawing());
        advancedPanel.add(saveButton);
        
        JButton saveImageButton = new JButton("🖼️ Sauvegarder Image");
        saveImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int choice = fileChooser.showSaveDialog(this);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    file = new File(file.getAbsolutePath() + ".png");
                }
                try {
                    FileUtils.saveAsImage(drawingArea, file);
                    JOptionPane.showMessageDialog(this, "Image sauvegardée avec succès !");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde de l'image : " + ex.getMessage());
                }
            }
        });

        advancedPanel.add(saveImageButton);

        JButton loadButton = new JButton("📂 Charger(binaire)");
        loadButton.addActionListener(e -> loadDrawing());
        advancedPanel.add(loadButton);

        JToggleButton darkModeToggle = new JToggleButton("🌙 Mode Sombre");
        darkModeToggle.addActionListener(e -> toggleDarkMode(darkModeToggle));
        advancedPanel.add(darkModeToggle);

        return advancedPanel;
    }

    private void saveDrawing() {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showSaveDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileUtils.saveShapesToFile(drawingArea.getShapes(), file);
                JOptionPane.showMessageDialog(this, "Dessin sauvegardé avec succès !");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde : " + ex.getMessage());
            }
        }
    }

    private void loadDrawing() {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                List<Shape> shapes = FileUtils.loadShapesFromFile(file);
                drawingArea.setShapes(shapes);
                JOptionPane.showMessageDialog(this, "Dessin chargé avec succès !");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement : " + ex.getMessage());
            }
        }
    }

    private void toggleDarkMode(JToggleButton toggleButton) {
        ThemeManager.toggleDarkMode();
        applyTheme();
        toggleButton.setText(ThemeManager.isDarkMode() ? "☀️ Mode Clair" : "🌙 Mode Sombre");
    }

    private void applyTheme() {
        this.getContentPane().setBackground(ThemeManager.getBackgroundColor());
        drawingArea.setBackground(ThemeManager.getBackgroundColor());
        repaint();
    }

    private void setupKeyboardShortcuts() {
        this.addKeyListener(drawingArea.getKeyboardShortcuts());
        this.setFocusable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaintFrame().setVisible(true));
    }
}

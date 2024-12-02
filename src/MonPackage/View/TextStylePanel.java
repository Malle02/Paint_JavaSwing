package MonPackage.View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class TextStylePanel extends JPanel {
    private JTextField textField;
    private JSpinner fontSizeSpinner;
    private JComboBox<String> fontSelector;
    private JButton colorPickerButton;
    private JCheckBox boldCheckBox, italicCheckBox;
    private JRadioButton alignLeft, alignCenter, alignRight;
    private JLabel previewLabel;
    private Color selectedColor;

    public TextStylePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Texte
        JLabel textLabel = new JLabel("Entrez le texte :");
        textLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(textLabel, gbc);

        textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.addActionListener(e -> updatePreview());
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(textField, gbc);

        // Taille de police
        JLabel fontSizeLabel = new JLabel("Taille de la police :");
        fontSizeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(fontSizeLabel, gbc);

        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(16, 8, 72, 1));
        fontSizeSpinner.addChangeListener((ChangeEvent e) -> updatePreview());
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(fontSizeSpinner, gbc);

        // Sélecteur de police
        JLabel fontSelectorLabel = new JLabel("Police :");
        fontSelectorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(fontSelectorLabel, gbc);

        fontSelector = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontSelector.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font((String) value, Font.PLAIN, 14));
                return label;
            }
        });
        fontSelector.addActionListener(e -> updatePreview());
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(fontSelector, gbc);

        // Style de texte
        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boldCheckBox = new JCheckBox("Gras");
        boldCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
        boldCheckBox.addActionListener(e -> updatePreview());

        italicCheckBox = new JCheckBox("Italique");
        italicCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
        italicCheckBox.addActionListener(e -> updatePreview());

        stylePanel.add(boldCheckBox);
        stylePanel.add(italicCheckBox);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(stylePanel, gbc);

        // Couleur
        JLabel colorLabel = new JLabel("Couleur :");
        colorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(colorLabel, gbc);

        colorPickerButton = new JButton("Choisir...");
        selectedColor = Color.BLACK;
        colorPickerButton.setBackground(selectedColor);
        colorPickerButton.setOpaque(true);
        colorPickerButton.setBorderPainted(false);
        colorPickerButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choisissez une couleur", selectedColor);
            if (newColor != null) {
                selectedColor = newColor;
                colorPickerButton.setBackground(selectedColor);
                updatePreview();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(colorPickerButton, gbc);

        // Alignement
        JLabel alignLabel = new JLabel("Alignement :");
        alignLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(alignLabel, gbc);

        JPanel alignPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        alignLeft = new JRadioButton("Gauche");
        alignCenter = new JRadioButton("Centre", true);
        alignRight = new JRadioButton("Droite");
        alignLeft.addActionListener(e -> updatePreview());
        alignCenter.addActionListener(e -> updatePreview());
        alignRight.addActionListener(e -> updatePreview());

        ButtonGroup alignGroup = new ButtonGroup();
        alignGroup.add(alignLeft);
        alignGroup.add(alignCenter);
        alignGroup.add(alignRight);

        alignPanel.add(alignLeft);
        alignPanel.add(alignCenter);
        alignPanel.add(alignRight);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(alignPanel, gbc);

        // Prévisualisation
        JLabel previewTextLabel = new JLabel("Aperçu :");
        previewTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(previewTextLabel, gbc);

        previewLabel = new JLabel("Aucun texte");
        previewLabel.setOpaque(true);
        previewLabel.setBackground(Color.WHITE);
        previewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(previewLabel, gbc);

        updatePreview(); 
    }

    private void updatePreview() {
        String text = textField.getText();
        int fontSize = (int) fontSizeSpinner.getValue();
        String fontFamily = (String) fontSelector.getSelectedItem();
        int fontStyle = (boldCheckBox.isSelected() ? Font.BOLD : Font.PLAIN) |
                        (italicCheckBox.isSelected() ? Font.ITALIC : Font.PLAIN);

        Font font;
        try {
            font = new Font(fontFamily, fontStyle, fontSize);
            if (font.getFamily().equals("Dialog")) { // Vérifie si la police est invalide
                throw new IllegalArgumentException("Police non disponible");
            }
        } catch (Exception e) {
            font = new Font("Arial", fontStyle, fontSize); // Police de secours
        }

        previewLabel.setFont(font);
        previewLabel.setText(text.isEmpty() ? "Aucun texte" : text);
        previewLabel.setForeground(selectedColor);

        if (alignLeft.isSelected()) {
            previewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        } else if (alignCenter.isSelected()) {
            previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } else if (alignRight.isSelected()) {
            previewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }

    public String getText() {
        return textField.getText();
    }

    public int getFontSize() {
        return (int) fontSizeSpinner.getValue();
    }

    public String getFontFamily() {
        return (String) fontSelector.getSelectedItem();
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public int getFontStyle() {
        return (boldCheckBox.isSelected() ? Font.BOLD : Font.PLAIN) |
               (italicCheckBox.isSelected() ? Font.ITALIC : Font.PLAIN);
    }

    public int getTextAlignment() {
        if (alignLeft.isSelected()) return SwingConstants.LEFT;
        if (alignRight.isSelected()) return SwingConstants.RIGHT;
        return SwingConstants.CENTER;
    }
    
    
    
    
    public JTextField getTextField() {
        return textField;
    }

    public JSpinner getFontSizeSpinner() {
        return fontSizeSpinner;
    }

    public JComboBox<String> getFontSelector() {
        return fontSelector;
    }

    public JButton getColorPickerButton() {
        return colorPickerButton;
    }

    public JCheckBox getBoldCheckBox() {
        return boldCheckBox;
    }

    public JCheckBox getItalicCheckBox() {
        return italicCheckBox;
    }



}

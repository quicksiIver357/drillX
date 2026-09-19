package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DrillPanel extends JPanel {
    // settings
    private String drawMode;

    // stuff on the panel
    JButton toggleFieldViewButton;

    // classifiers
    public static final String TOGGLE_FIELD_VIEW = "toggle_field_view";

    // constructors
    public DrillPanel(Color c) {
        setBackground(c); 
        drawMode = FieldPanel.YARD;

        // add buttons and input fields
        toggleFieldViewButton = new JButton(drawMode); // starts off as yard
        toggleFieldViewButton.setActionCommand(TOGGLE_FIELD_VIEW);
        toggleFieldViewButton.setToolTipText("Toggles the field view between yard and coordinate.");

        add(toggleFieldViewButton);
    }

    // setters
    public void setDrawMode(String drawMode) {
        if (drawMode.equals(FieldPanel.YARD) || drawMode.equals(FieldPanel.COORDINATE)) { 
            // sets the draw mode and updates the button text
            this.drawMode = drawMode; 
            toggleFieldViewButton.setText(drawMode);
        }
        else { throw new InvalidParameterException("drawMode must either be YARDS or COORDINATES."); }
    }
    public void addToggleFieldViewButtonListener(ActionListener l) { toggleFieldViewButton.addActionListener(l); }

    // getters
    public String getDrawMode() { return drawMode; }
}

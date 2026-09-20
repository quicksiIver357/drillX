package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class DrillPanel extends JPanel {
    // settings
    private String drawMode;

    // stuff on the panel
    JButton toggleFieldViewButton;
    JCheckBox toggleSquadNumbersCheckBox, showArrowsCheckBox;

    // constructors
    public DrillPanel(Color c) {
        setBackground(c); 
        drawMode = FieldPanel.YARD;

        // add buttons and input fields
        toggleFieldViewButton = new JButton(drawMode); // starts off as yard
        toggleFieldViewButton.setToolTipText("Toggles the field view between yard and coordinate.");

        toggleSquadNumbersCheckBox = new JCheckBox("Squad Numbers");
        toggleSquadNumbersCheckBox.setToolTipText("Turns squad numbers on or off.");

        showArrowsCheckBox = new JCheckBox("Show Arrows");
        showArrowsCheckBox.setToolTipText("Turns arrows showing how squads are facing on or off.");

        add(toggleFieldViewButton);
        add(toggleSquadNumbersCheckBox);
        add(showArrowsCheckBox);
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
    public void addToggleSquadNumbersCheckBoxListener(ActionListener l) {toggleSquadNumbersCheckBox.addActionListener(l); }
    public void addShowArowsCheckBoxListener(ActionListener l) { showArrowsCheckBox.addActionListener(l); }

    // getters
    public String getDrawMode() { return drawMode; }
}

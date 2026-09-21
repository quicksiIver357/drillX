package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class DrillPanel extends JPanel {
    // settings
    private String drawMode = FieldPanel.YARD;

    // stuff on the panel
    private JButton toggleFieldViewButton = new JButton(drawMode); // starts off as yard
    private JCheckBox toggleSquadNumbersCheckBox = new JCheckBox("Squad Numbers");
    private JCheckBox showArrowsCheckBox = new JCheckBox("Show Arrows");
    private JCheckBox dragSquadsCheckBox = new JCheckBox("Drag Squads");
    private JCheckBox snapToGridCheckBox = new JCheckBox("Snap to Grid");

    // constructors
    public DrillPanel(Color c) {
        setBackground(c);

        // add buttons and input fields, and config them
        toggleFieldViewButton.setToolTipText("Toggles the field view between yard and coordinate.");
        toggleSquadNumbersCheckBox.setToolTipText("Turns squad numbers on or off.");
        showArrowsCheckBox.setToolTipText("Turns arrows showing how squads are facing on or off.");
        dragSquadsCheckBox.setToolTipText("Enables or disables dragging of squads on the field.");
        snapToGridCheckBox.setToolTipText("Snap squads to the grid while dragging.");

        add(toggleFieldViewButton);
        add(toggleSquadNumbersCheckBox);
        add(showArrowsCheckBox);
        add(dragSquadsCheckBox);
        add(snapToGridCheckBox);

        // custom functionality
        dragSquadsCheckBox.addActionListener(e -> { snapToGridCheckBox.setEnabled(!snapToGridCheckBox.isEnabled()); });

        snapToGridCheckBox.setEnabled(false);
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
    public void addDragSquadsCheckBoxListener(ActionListener l) { dragSquadsCheckBox.addActionListener(l); }
    public void addSnapToGridCheckBoxListener(ActionListener l) { snapToGridCheckBox.addActionListener(l); }

    // getters
    public String getDrawMode() { return drawMode; }
}

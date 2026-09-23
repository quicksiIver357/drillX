package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.io.quicksiiver.drillx.field.Formation;
import main.java.io.quicksiiver.drillx.field.RotationDirection;

public class DrillPanel extends JPanel {
    // settings
    private String drawMode = FieldPanel.YARD;

    // stuff on the panel
    private JButton toggleFieldViewButton = new JButton(drawMode); // starts off as yard
    private JComboBox<Formation> defaultFormationComboBox = new JComboBox<>(Formation.ALL_FORMATIONS);
    private JComboBox<RotationDirection> defaultRotationDirectionComboBox = new JComboBox<>(RotationDirection.ROTATION_DIRECTIONS);
    private JCheckBox toggleSquadNumbersCheckBox = new JCheckBox("Show Numbers");
    private JCheckBox showArrowsCheckBox = new JCheckBox("Show Arrows");
    private JCheckBox dragSquadsCheckBox = new JCheckBox("Drag Squads");
    private JCheckBox snapToGridCheckBox = new JCheckBox("Snap to Grid");

    // labels
    private JLabel toggleFieldViewButtonLabel = new JLabel("Toggle Field View: ");
    private JLabel defaultFormationComboBoxLabel = new JLabel("Default Formation: ");
    private JLabel defaultRotationDirectionComboBoxLabel = new JLabel("Default Rotation: ");

    // constructors
    public DrillPanel(Color c) {
        super(new GridBagLayout());

        setBackground(c);

        // add stuff and config them
        toggleFieldViewButton.setToolTipText("Toggles the field view between yard and coordinate.");
        defaultFormationComboBox.setToolTipText("Sets the default squad formation when creating a new squad.");
        defaultFormationComboBox.setToolTipText("Sets the default squad rotation when creating a new squad.");
        toggleSquadNumbersCheckBox.setToolTipText("Turns squad numbers on or off.");
        showArrowsCheckBox.setToolTipText("Turns arrows showing how squads are facing on or off.");
        dragSquadsCheckBox.setToolTipText("Enables or disables dragging of squads on the field.");
        snapToGridCheckBox.setToolTipText("Snap squads to the grid while dragging.");

        toggleFieldViewButtonLabel.setLabelFor(toggleFieldViewButton);
        defaultFormationComboBoxLabel.setLabelFor(defaultFormationComboBox);
        defaultRotationDirectionComboBoxLabel.setLabelFor(defaultRotationDirectionComboBox);

        // then do formatting with GridBagConstraints
        // -----------------------------------------------------------------------
        GridBagConstraints toggleFieldViewButtonConstraints = new GridBagConstraints();
        GridBagConstraints defaultFormationComboBoxConstraints = new GridBagConstraints();
        GridBagConstraints defaultRotationDirectionComboBoxConstraints = new GridBagConstraints();
        GridBagConstraints toggleSquadNumbersCheckBoxConstraints = new GridBagConstraints();
        GridBagConstraints showArrowsCheckBoxConstraints = new GridBagConstraints();
        GridBagConstraints dragSquadsCheckBoxConstraints = new GridBagConstraints();
        GridBagConstraints snapToGridCheckBoxConstraints = new GridBagConstraints();

        GridBagConstraints toggleFieldViewButtonLabelConstraints = new GridBagConstraints();
        GridBagConstraints defaultFormationComboBoxLabelConstraints = new GridBagConstraints();
        GridBagConstraints defaultRotationDirectionComboBoxLabelConstraints = new GridBagConstraints();

        toggleFieldViewButtonLabelConstraints.gridx = 0;
        toggleFieldViewButtonLabelConstraints.gridy = 0;
        toggleFieldViewButtonLabelConstraints.fill = GridBagConstraints.BOTH;

        toggleFieldViewButtonConstraints.gridx = 1;
        toggleFieldViewButtonConstraints.gridy = 0;
        toggleFieldViewButtonConstraints.fill = GridBagConstraints.BOTH;

        toggleSquadNumbersCheckBoxConstraints.gridx = 0;
        toggleSquadNumbersCheckBoxConstraints.gridy = 1;
        toggleSquadNumbersCheckBoxConstraints.fill = GridBagConstraints.BOTH;

        showArrowsCheckBoxConstraints.gridx = 1;
        showArrowsCheckBoxConstraints.gridy = 1;
        showArrowsCheckBoxConstraints.fill = GridBagConstraints.BOTH;

        dragSquadsCheckBoxConstraints.gridx = 0;
        dragSquadsCheckBoxConstraints.gridy = 2;
        dragSquadsCheckBoxConstraints.fill = GridBagConstraints.BOTH;

        snapToGridCheckBoxConstraints.gridx = 1;
        snapToGridCheckBoxConstraints.gridy = 2;
        snapToGridCheckBoxConstraints.fill = GridBagConstraints.BOTH;

        defaultFormationComboBoxLabelConstraints.gridx = 0;
        defaultFormationComboBoxLabelConstraints.gridy = 3;
        defaultFormationComboBoxLabelConstraints.fill = GridBagConstraints.BOTH;

        defaultFormationComboBoxConstraints.gridx = 1;
        defaultFormationComboBoxConstraints.gridy = 3;
        defaultFormationComboBoxConstraints.fill = GridBagConstraints.BOTH;

        defaultRotationDirectionComboBoxLabelConstraints.gridx = 0;
        defaultRotationDirectionComboBoxLabelConstraints.gridy = 4;
        defaultRotationDirectionComboBoxLabelConstraints.fill = GridBagConstraints.BOTH;

        defaultRotationDirectionComboBoxConstraints.gridx = 1;
        defaultRotationDirectionComboBoxConstraints.gridy = 4;
        defaultRotationDirectionComboBoxConstraints.fill = GridBagConstraints.BOTH;
        // ---------------------------------------------------------------------------

        // add all of the componenents
        add(toggleFieldViewButtonLabel, toggleFieldViewButtonLabelConstraints);
        add(defaultFormationComboBoxLabel, defaultFormationComboBoxLabelConstraints);
        add(defaultRotationDirectionComboBoxLabel, defaultRotationDirectionComboBoxLabelConstraints);

        add(toggleFieldViewButton, toggleFieldViewButtonConstraints);
        add(toggleSquadNumbersCheckBox, toggleSquadNumbersCheckBoxConstraints);
        add(showArrowsCheckBox, showArrowsCheckBoxConstraints);
        add(dragSquadsCheckBox, dragSquadsCheckBoxConstraints);
        add(snapToGridCheckBox, snapToGridCheckBoxConstraints);
        add(defaultFormationComboBox, defaultFormationComboBoxConstraints);
        add(defaultRotationDirectionComboBox, defaultRotationDirectionComboBoxConstraints);

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
    public void addDefaultFormationComboBoxListener(ActionListener l) { defaultFormationComboBox.addActionListener(l); }
    public void addDefaultRotationDirectionComboBoxListener(ActionListener l) { defaultRotationDirectionComboBox.addActionListener(l); }

    // getters
    public String getDrawMode() { return drawMode; }
}

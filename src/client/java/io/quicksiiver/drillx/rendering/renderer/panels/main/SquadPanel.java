package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.java.io.quicksiiver.drillx.Main;
import main.java.io.quicksiiver.drillx.field.Formation;
import main.java.io.quicksiiver.drillx.field.RotationDirection;
import main.java.io.quicksiiver.drillx.field.Squad;

public class SquadPanel extends JPanel {
    private Squad squad;

    // stuff for drawing
    private JTextArea noSquadSelectedText = new JTextArea("No squad selected. Click on a squad to select it.");
    private JComboBox<RotationDirection> rotationDirectionSelector = new JComboBox<>(RotationDirection.ROTATION_DIRECTIONS);
    private JComboBox<Formation> formationSelector = new JComboBox<>(Main.ALL_FORMATIONS.values().toArray(new Formation[0]));

    // LABELS
    private JLabel rotationDirectionSelectorLabel = new JLabel("Rotation: ");
    private JLabel formationSelectorLabel = new JLabel("Formation: ");

    public SquadPanel(final Color c) { this(c, null); }
    public SquadPanel(final Color c, Squad s) {
        super(new GridBagLayout());

        setBackground(c);
        squad = s; // apply the reference

        // rendering
        noSquadSelectedText.setEditable(false);

        rotationDirectionSelectorLabel.setLabelFor(rotationDirectionSelector);
        formationSelectorLabel.setLabelFor(formationSelector);

        // formatting wth GridBagConstraints
        // ----------------------------------------------------------
        GridBagConstraints rotationDirectionSelectorLabelConstraints = new GridBagConstraints();
        GridBagConstraints rotationDirectionSelectorConstraints = new GridBagConstraints();
        GridBagConstraints formationSelectorConstraints = new GridBagConstraints();
        GridBagConstraints formationSelectorLabelConstraints = new GridBagConstraints();
        
        rotationDirectionSelectorLabelConstraints.gridx = 0;
        rotationDirectionSelectorLabelConstraints.gridy = 0;
        rotationDirectionSelectorLabelConstraints.fill = GridBagConstraints.BOTH;

        rotationDirectionSelectorConstraints.gridx = 1;
        rotationDirectionSelectorConstraints.gridy = 0;
        rotationDirectionSelectorConstraints.fill = GridBagConstraints.BOTH;

        formationSelectorLabelConstraints.gridx = 0;
        formationSelectorLabelConstraints.gridy = 1;
        formationSelectorLabelConstraints.fill = GridBagConstraints.BOTH;

        formationSelectorConstraints.gridx = 1;
        formationSelectorConstraints.gridy = 1;
        formationSelectorConstraints.fill = GridBagConstraints.BOTH;
        // ---------------------------------------------------------

        // add all of the components
        add(noSquadSelectedText);
        add(rotationDirectionSelectorLabel, rotationDirectionSelectorLabelConstraints);
        add(rotationDirectionSelector, rotationDirectionSelectorConstraints);
        add(formationSelectorLabel, formationSelectorLabelConstraints);
        add(formationSelector, formationSelectorConstraints);

        // set everything to not visible and then set the noSquadSelectedText to visible
        for (Component component : getComponents()) { component.setVisible(false); }
        noSquadSelectedText.setVisible(true);
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    //     super.paintComponent(g);

    //     Graphics2D g2d = (Graphics2D) g;


    // }

    // getters
    public Squad getSquad() { return new Squad(squad); }

    // setters
    public void setSelectedSquad(Squad s) {
        squad = s; 

        if (squad == null ^ noSquadSelectedText.isVisible()) { // if squad is null XOR squadSelected text is visible
            for (Component c : getComponents()) { c.setVisible(!c.isVisible()); } // swap visibility
        }

        if (squad != null) {
            // update the components
            rotationDirectionSelector.setSelectedItem(squad.getRotationDirection());
            formationSelector.setSelectedItem(squad.getFormation());
        }

        

        // refresh screen
        revalidate();
        repaint();
    }

    // listeners
    public void addRotationDirectionSelectorListener(ActionListener l) { rotationDirectionSelector.addActionListener(l); }
    public void addFormationSelectorListener(ActionListener l) { formationSelector.addActionListener(l); }
}

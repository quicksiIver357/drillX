package client.java.io.quicksiiver.drillx.rendering.renderer;

import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;

import client.java.io.quicksiiver.drillx.rendering.renderer.layout.MainLayout;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.DrillPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.FieldPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.MainPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.SquadPanel;

public class MainRenderer {
    public static final MainRenderer instance = new MainRenderer();

    private MainRenderer() { // hides default constructor
        // Create and set up the window.
        JFrame frame = new JFrame("drillX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create panels
        FieldPanel fieldPanel = new FieldPanel(Color.BLUE);
        SquadPanel squadPanel = new SquadPanel(Color.RED);
        DrillPanel drillPanel = new DrillPanel(Color.YELLOW);
        MainPanel mainPanel = new MainPanel(new MainLayout(fieldPanel, squadPanel, drillPanel), Color.BLACK);

        // create constainsts
        GridBagConstraints fieldGridBagConstraints = new GridBagConstraints();
        GridBagConstraints squadGridBagConstraints = new GridBagConstraints();
        GridBagConstraints drillGridBagConstraints = new GridBagConstraints();

        fieldGridBagConstraints.fill = GridBagConstraints.BOTH;
        fieldGridBagConstraints.weightx = 3;
        fieldGridBagConstraints.weighty = 2.5;
        fieldGridBagConstraints.gridx = 0;
        fieldGridBagConstraints.gridy = 0;

        squadGridBagConstraints.fill = GridBagConstraints.BOTH;
        squadGridBagConstraints.weightx = 3;
        squadGridBagConstraints.weighty = 1;
        squadGridBagConstraints.gridx = 0;
        squadGridBagConstraints.gridy = 1;

        drillGridBagConstraints.fill = GridBagConstraints.BOTH;
        drillGridBagConstraints.weightx = 1;
        drillGridBagConstraints.weighty = 3.5;
        drillGridBagConstraints.gridx = 1;
        drillGridBagConstraints.gridy = 0;
        drillGridBagConstraints.gridheight = 2;
        
        // add to main panle
        mainPanel.add(fieldPanel, fieldGridBagConstraints);
        mainPanel.add(squadPanel, squadGridBagConstraints);
        mainPanel.add(drillPanel, drillGridBagConstraints);

        // JLabel label = new JLabel("Hello World");
        // frame.getContentPane().add(label);
 
        // Display the window.
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    } 
    
    public static void main(String[] args) {
        
    }
}

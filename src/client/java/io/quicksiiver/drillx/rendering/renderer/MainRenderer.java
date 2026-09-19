package client.java.io.quicksiiver.drillx.rendering.renderer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import client.java.io.quicksiiver.drillx.rendering.renderer.layout.MainLayout;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.DrillPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.FieldPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.MainPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.SquadPanel;

public class MainRenderer implements ActionListener {
    public static final MainRenderer instance = new MainRenderer();

    // instance variables
    private JFrame frame;
    private FieldPanel fieldPanel;
    private SquadPanel squadPanel;
    private DrillPanel drillPanel;
    private MainPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveMenuItem;

    private MainRenderer() { // hides default constructor
        // Create and set up the window.
        frame = new JFrame("drillX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create panels
        fieldPanel = new FieldPanel(new Color(94, 169, 79), Color.WHITE, FieldPanel.YARD);
        squadPanel = new SquadPanel(Color.WHITE);
        drillPanel = new DrillPanel(Color.WHITE);
        mainPanel = new MainPanel(new MainLayout(fieldPanel, squadPanel, drillPanel), Color.BLACK);

        drillPanel.addToggleFieldViewButtonListener(e -> {
            // do stuff when the toggleFieldViewButton is pressed
            if (e.getActionCommand().equals(DrillPanel.TOGGLE_FIELD_VIEW)) {
                // swap them
                if (fieldPanel.getDrawMode() == FieldPanel.YARD) { setDrawMode(FieldPanel.COORDINATE); }
                else { setDrawMode(FieldPanel.YARD); }
            }
        });

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

        // add a menu bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveMenuItem = new JMenuItem("Save (Ctrl + S)");

        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        // JLabel label = new JLabel("Hello World");
        // frame.getContentPane().add(label);
 
        // Display the window.
        frame.setContentPane(mainPanel);
        frame.setSize(1024, 576);
        frame.setVisible(true);
    } 

    // event responding
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(DrillPanel.TOGGLE_FIELD_VIEW)) {
            // swap them
            if (fieldPanel.getDrawMode() == FieldPanel.YARD) { setDrawMode(FieldPanel.COORDINATE); }
            else { setDrawMode(FieldPanel.YARD); }
        }
    }
    
    public static void main(String[] args) {
        while (true) { instance.fieldPanel.repaint(); }
    }

    // helpers
    private void setDrawMode(String drawMode) {
        fieldPanel.setDrawMode(drawMode);
        drillPanel.setDrawMode(drawMode);
    }
}

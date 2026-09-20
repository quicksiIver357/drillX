package client.java.io.quicksiiver.drillx.rendering.renderer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import client.java.io.quicksiiver.drillx.rendering.renderer.layout.MainLayout;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.DrillPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.FieldPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.MainPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.SquadPanel;
import main.java.io.quicksiiver.drillx.Main;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Squad;

public class MainRenderer implements ActionListener {
    public static final MainRenderer instance = new MainRenderer();

    // instance variables
    private JFrame frame;
    private FieldPanel fieldPanel;
    private SquadPanel squadPanel;
    private DrillPanel drillPanel;
    private MainPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu, fieldMenu;
    private JMenuItem saveMenuItem, newSquadMenuItem;

    public Drill drill = null; // the currently used drill

    // classifiers
    private static final String SAVE = "save";
    private static final String NEW_SQUAD = "new_squad";

    private MainRenderer() { // hides default constructor
        // Create and set up the window.
        frame = new JFrame("drillX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create panels
        fieldPanel = new FieldPanel();
        squadPanel = new SquadPanel(Color.WHITE);
        drillPanel = new DrillPanel(Color.WHITE);
        mainPanel = new MainPanel(new MainLayout(fieldPanel, squadPanel, drillPanel), Color.BLACK);

        fieldPanel.drill = drill;
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
        fieldMenu = new JMenu("Field");
        saveMenuItem = new JMenuItem("Save");
        newSquadMenuItem = new JMenuItem("New Squad");

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(this);
        saveMenuItem.setActionCommand(SAVE);

        newSquadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newSquadMenuItem.addActionListener(this);
        newSquadMenuItem.setActionCommand(NEW_SQUAD);

        fileMenu.add(saveMenuItem);
        fieldMenu.add(newSquadMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(fieldMenu);

        frame.setJMenuBar(menuBar);
 
        // Display the window.
        frame.setContentPane(mainPanel);
        frame.setSize(1024, 576);
        frame.setVisible(true);
    } 

    // event responding
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SAVE -> { drill.save(Main.gson, Path.of("src", "main", "resources", "data", "drills", drill.getName() + ".json")); }
            case NEW_SQUAD -> { drill.squads.add(new Squad()); }
        }
    }

    public void repaint() { frame.repaint(); }

    // helpers
    private void setDrawMode(String drawMode) {
        fieldPanel.setDrawMode(drawMode);
        drillPanel.setDrawMode(drawMode);
    }
}

package client.java.io.quicksiiver.drillx.rendering.renderer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import client.java.io.quicksiiver.drillx.rendering.renderer.input.MainWindowListener;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.MainPanel;
import main.java.io.quicksiiver.drillx.Main;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Squad;

public class MainRenderer {
    public static final MainRenderer INSTANCE = new MainRenderer();

    // instance variables
    private JFrame frame;
    private MainPanel mainPanel;

    private JMenuBar mainMenuBar;
    private JMenu fileMenu, fieldMenu;
    private JMenuItem saveMenuItem, newSquadMenuItem;

    // classifiers
    private static final String SAVE = "save";
    private static final String NEW_SQUAD = "new_squad";

    private MainRenderer() { // hides default constructor
        // Create and set up the window.
        frame = new JFrame("drillX");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new MainWindowListener());

        // create panels
        mainPanel = new MainPanel();

        // add a menu bar
        mainMenuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fieldMenu = new JMenu("Field");
        saveMenuItem = new JMenuItem("Save");
        newSquadMenuItem = new JMenuItem("New Squad");

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(e -> { mainPanel.getDrill().save(Main.GSON); });

        newSquadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newSquadMenuItem.addActionListener(e -> { mainPanel.getDrill().squads.add(new Squad()); });

        fileMenu.add(saveMenuItem);
        fieldMenu.add(newSquadMenuItem);
        mainMenuBar.add(fileMenu);
        mainMenuBar.add(fieldMenu);

        frame.setJMenuBar(mainMenuBar);
 
        // Display the window.
        frame.setContentPane(mainPanel);
        frame.setSize(1024, 576);
        frame.setVisible(true);
    } 

    // accessors
    public void repaint() { frame.repaint(); }
    public void dispose() { frame.dispose(); }

    // getters
    public Drill getDrill() { return mainPanel.getDrill(); }

    // setters
    public void setDrill(Drill d) { mainPanel.setDrill(d); }
}

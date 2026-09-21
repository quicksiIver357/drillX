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
    private JFrame frame = new JFrame("drillX");
    private MainPanel mainPanel = new MainPanel();

    private JMenuBar mainMenuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenu fieldMenu = new JMenu("Field");
    private JMenuItem saveMenuItem = new JMenuItem("Save");
    private JMenuItem newSquadMenuItem = new JMenuItem("New Squad");

    private MainRenderer() { // hides default constructor
        // set up the window
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new MainWindowListener());

        // config menu bar
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(e -> { mainPanel.getDrill().save(Main.GSON); });

        newSquadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newSquadMenuItem.addActionListener(e -> { 
            mainPanel.getDrill().squads.add(new Squad()); 

            // refresh screen
            repaint();
        });

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

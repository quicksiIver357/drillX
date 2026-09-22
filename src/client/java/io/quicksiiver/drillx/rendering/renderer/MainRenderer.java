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
import main.java.io.quicksiiver.drillx.coordinates.Point;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Formation;
import main.java.io.quicksiiver.drillx.field.RotationDirection;
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
    private JMenuItem deleteSquadMenuItem = new JMenuItem("Delete Selected Squad");

    private RotationDirection defaultRotationDirection = RotationDirection.NORTH;
    private Formation defaultFormation = Formation.HORIZONTAL_BOTTOM;

    private MainRenderer() { // hides default constructor
        // set up the window
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new MainWindowListener());

        // config menu bar
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(e -> { mainPanel.getDrill().save(Main.GSON); });

        newSquadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newSquadMenuItem.addActionListener(e -> { 
            mainPanel.getDrill().squads.add(new Squad(defaultRotationDirection, defaultFormation, Squad.NO_KEY, new Point(16, 16)));

            // refresh screen
            repaint();
        });

        deleteSquadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        deleteSquadMenuItem.addActionListener(e -> { 
            mainPanel.deleteSelectedSquad(); 
            repaint();
        });

        // actionListeners for mainPanel (drillPanel)
        mainPanel.addDefaultFormationComboBoxListener(e -> { defaultFormation = mainPanel.getSelection(Formation.HORIZONTAL_BOTTOM, e); });
        mainPanel.addDefaultRotationDirectionComboBoxListener(e -> { defaultRotationDirection = mainPanel.getSelection(RotationDirection.EAST, e); });

        fileMenu.add(saveMenuItem);
        fieldMenu.add(newSquadMenuItem);
        fieldMenu.add(deleteSquadMenuItem);
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

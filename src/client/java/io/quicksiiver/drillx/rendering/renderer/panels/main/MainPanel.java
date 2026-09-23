package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import client.java.io.quicksiiver.drillx.rendering.renderer.layout.MainLayout;
import client.java.io.quicksiiver.drillx.rendering.renderer.misc.Theme;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Formation;
import main.java.io.quicksiiver.drillx.field.RotationDirection;
import main.java.io.quicksiiver.drillx.field.Squad;

public class MainPanel extends JPanel {
    // instacne variables
    private FieldPanel fieldPanel;
    private DrillPanel drillPanel;
    private SquadPanel squadPanel;

    public MainPanel(Theme t, Drill d) {
        // store data
        super(new MainLayout(null, null, null));

        this.fieldPanel = new FieldPanel(this, t.field, t.fieldLine, t.fieldSquad, t.fieldNumber, t.fieldArrow, t.fieldSelectedSquad, d);
        this.squadPanel = new SquadPanel(t.squad);
        this.drillPanel = new DrillPanel(t.drill);

        MainLayout layout = (MainLayout) getLayout();

        layout.fieldPanel = this.fieldPanel;
        layout.squadPanel = this.squadPanel;
        layout.drillPanel = this.drillPanel;

        setDrill(d);

        // not storing stuff, setting up the ui and such

        // ACTION LISTENER STUFF
        // --------------------------------------------------------------------------------------------
        drillPanel.addToggleFieldViewButtonListener(e -> {
            if (fieldPanel.getDrawMode() == FieldPanel.YARD) { setDrawMode(FieldPanel.COORDINATE); }
            else { setDrawMode(FieldPanel.YARD); }

            repaint();
        });
        drillPanel.addToggleSquadNumbersCheckBoxListener(e -> { 
            fieldPanel.showNumbers = !fieldPanel.showNumbers; 

            repaint();
        });
        drillPanel.addShowArowsCheckBoxListener(e -> { 
            fieldPanel.showArrows = !fieldPanel.showArrows; 

            repaint();
        });
        drillPanel.addDragSquadsCheckBoxListener(e -> { fieldPanel.dragSquads = !fieldPanel.dragSquads; });
        drillPanel.addSnapToGridCheckBoxListener(e -> { fieldPanel.snapToGrid = !fieldPanel.snapToGrid; });

        squadPanel.addRotationDirectionSelectorListener(e -> {
            fieldPanel.setRotationDirection(getSelection(RotationDirection.EAST, e)); // apply it
            repaint();
        });
        squadPanel.addFormationSelectorListener(e -> { 
            // you can pass in any formation just for the type
            fieldPanel.setFormation(getSelection(Formation.LEFT_SLANT, e)); 
            repaint();
        });
        // ------------------------------------------------------------------------------

        add(fieldPanel);
        add(squadPanel);
        add(drillPanel);
    }
    public MainPanel(Theme t) { this(t, null); }
    public MainPanel(Drill d) { this(Theme.DEFAULT, d); }
    public MainPanel() { this(Theme.DEFAULT); }

    // setters
    public void setDrill(Drill d) { fieldPanel.drill = d; }
    public void deleteSelectedSquad() {
        fieldPanel.drill.removeSquad(fieldPanel.getSelectedSquad()); // remove it from drill
        setSelectedSquad(null); // remove it from selection
    }
    public void addDefaultFormationComboBoxListener(ActionListener l) { drillPanel.addDefaultFormationComboBoxListener(l); }
    public void addDefaultRotationDirectionComboBoxListener(ActionListener l) { drillPanel.addDefaultRotationDirectionComboBoxListener(l); }
    public void addSquad(Squad s) { fieldPanel.drill.addSquad(s); }

    // getters
    public Drill getDrill() { return fieldPanel.drill; }

    // helpers
    private void setDrawMode(String drawMode) {
        fieldPanel.setDrawMode(drawMode);
        drillPanel.setDrawMode(drawMode);
    }
    public <T> T getSelection(T type, ActionEvent e) {
        // get the selection
        @SuppressWarnings("unchecked") // stop yellow underline
        JComboBox<T> formationSelector = (JComboBox<T>) e.getSource();
        @SuppressWarnings("unchecked") // stop yellow underline
        T selection = (T) formationSelector.getSelectedItem();

        // return it
        return selection;
    }

    public void setSelectedSquad(Squad s) {
        fieldPanel.setSelectedSquad(s);
        squadPanel.setSelectedSquad(s);
    }

}
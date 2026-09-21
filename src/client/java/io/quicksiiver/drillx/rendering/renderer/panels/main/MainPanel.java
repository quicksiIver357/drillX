package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import client.java.io.quicksiiver.drillx.rendering.renderer.MainRenderer;
import client.java.io.quicksiiver.drillx.rendering.renderer.layout.MainLayout;
import client.java.io.quicksiiver.drillx.rendering.renderer.misc.Theme;
import main.java.io.quicksiiver.drillx.field.Drill;
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

        // DRILL PANEL STUFF
        // --------------------------------------------------------------------------------------------
        drillPanel.addToggleFieldViewButtonListener(e -> {
            if (fieldPanel.getDrawMode() == FieldPanel.YARD) { setDrawMode(FieldPanel.COORDINATE); }
            else { setDrawMode(FieldPanel.YARD); }

            MainRenderer.INSTANCE.repaint();
        });

        drillPanel.addToggleSquadNumbersCheckBoxListener(e -> { 
            fieldPanel.showNumbers = !fieldPanel.showNumbers; 

            MainRenderer.INSTANCE.repaint();
        });

        drillPanel.addShowArowsCheckBoxListener(e -> { 
            fieldPanel.showArrows = !fieldPanel.showArrows; 

            MainRenderer.INSTANCE.repaint();
        });

        drillPanel.addDragSquadsCheckBoxListener(e -> { fieldPanel.dragSquads = !fieldPanel.dragSquads; });
        drillPanel.addSnapToGridCheckBoxListener(e -> { fieldPanel.snapToGrid = !fieldPanel.snapToGrid; });
        // ------------------------------------------------------------------------------


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
        
        // add to self for drawing
        add(fieldPanel, fieldGridBagConstraints);
        add(squadPanel, squadGridBagConstraints);
        add(drillPanel, drillGridBagConstraints);
    }
    public MainPanel(Theme t) { this(t, null); }
    public MainPanel(Drill d) { this(Theme.DEFAULT, d); }
    public MainPanel() { this(Theme.DEFAULT); }

    // setters
    public void setDrill(Drill d) { fieldPanel.drill = d; }

    // getters
    public Drill getDrill() { return fieldPanel.drill; }

    // helpers
    private void setDrawMode(String drawMode) {
        fieldPanel.setDrawMode(drawMode);
        drillPanel.setDrawMode(drawMode);
    }

    public void setSelectedSquad(Squad s) {
        fieldPanel.setSelectedSquad(s);
        squadPanel.setSelectedSquad(s);
    }
}
package client.java.io.quicksiiver.drillx.rendering.renderer.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.DrillPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.FieldPanel;
import client.java.io.quicksiiver.drillx.rendering.renderer.panels.main.SquadPanel;

public class MainLayout implements LayoutManager {
    public FieldPanel fieldPanel;
    public SquadPanel squadPanel;
    public DrillPanel drillPanel;

    private static final double RATIO = 4.0 / 9; // width to height ratio

    // constructor
    public MainLayout(FieldPanel fieldPanel, SquadPanel squadPanel, DrillPanel drillPanel) {
        this.fieldPanel = fieldPanel;
        this.squadPanel = squadPanel;
        this.drillPanel = drillPanel;
    }

    // layout handling
    @Override
    public void layoutContainer(Container parent) {
        int width = parent.getWidth();
        int height = parent.getHeight();

        // Drill panel gets 25% of the width
        int drillWidth = width / 4;
        int leftWidth = width - drillWidth; // Everything else goes to the left
        int fieldHeight = (int) (leftWidth * RATIO); // Field is 4:3

        // Don't let the field exceed the available height
        if (fieldHeight > height) {
            fieldHeight = height;
            leftWidth = (int) (fieldHeight / RATIO);
            drillWidth = width - leftWidth;
        }

        int squadHeight = height - fieldHeight;

        fieldPanel.setBounds(0, 0, leftWidth, fieldHeight);
        squadPanel.setBounds(0, fieldHeight, leftWidth, squadHeight);
        drillPanel.setBounds(leftWidth, 0, drillWidth, height);
    }

    // methods
    @Override
    public Dimension preferredLayoutSize(Container parent) { return new Dimension(1000, 700); }
    @Override
    public Dimension minimumLayoutSize(Container parent) { return new Dimension(300, 200); }

    // fulfill interface stuff
    @Override
    public void addLayoutComponent(String name, Component comp) { throw new UnsupportedOperationException("addLayoutComponent should not be called by a MainLayout object"); }
    @Override
    public void removeLayoutComponent(Component comp) { throw new UnsupportedOperationException("removeLayoutComponent should not be called by a MainLayout object"); }
}

package client.java.io.quicksiiver.drillx.rendering.renderer.misc;

import java.awt.Color;

public class Theme {
    public Color drill; // color of drill background
    public Color field; // color of field background
    public Color fieldLine; // color of lines on field (and hashes)
    public Color fieldNumber; // color of squad numbers when shown
    public Color fieldSquad; // color of squads drawn on the field
    public Color squad; // color of the squad panel

    public static final Theme DEFAULT = new Theme(Color.WHITE, new Color(94, 169, 79), Color.WHITE, Color.RED, Color.BLACK, Color.WHITE); // preset

    public Theme(Color drill, Color field, Color fieldLine, Color fieldNumber, Color fieldSquad, Color squad) {
        this.drill = drill;
        this.field = field;
        this.fieldLine = fieldLine;
        this.fieldNumber = fieldNumber;
        this.fieldSquad = fieldSquad;
        this.squad = squad;
    }
}

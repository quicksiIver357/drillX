package client.java.io.quicksiiver.drillx.rendering.renderer.input;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import client.java.io.quicksiiver.drillx.rendering.renderer.MainRenderer;
import main.java.io.quicksiiver.drillx.Main;

public class MainWindowListener implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {}
    
    @Override
    public void windowClosed(WindowEvent e) { System.exit(0); }

    @Override
    public void windowClosing(WindowEvent e) { 
        MainRenderer.INSTANCE.getDrill().save(Main.GSON);
        MainRenderer.INSTANCE.dispose();
    }

    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
}

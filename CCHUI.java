

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;


public class CCHUI {
    public JFrame frame;
    public Component contents;

    public CCHUI(Snapshot snap, String name) {
        frame = new JFrame(name);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setLayout(new BorderLayout());
        contents =  new CCHComponent(CCHUI.this, snap);
        frame.add(contents);
        frame.setFocusableWindowState(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                notifyWindowClosing();
            }
        });
    }
    
    private void notifyWindowClosing() {
        frame.dispose();
    }

    public void setFrameVisible(boolean visible) {
        frame.setVisible(visible);
    }
}

/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */

package jpwp;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Font;

public class FPS_MenuPanel extends JPanel {

    /* parameter that defines menu panel width */
    public int panelWidth;
    /* parameter that defines menu panel height */
    public int panelHeight;

    /* Definition of menu font */
    public Font menuFont;
    /* Definition of alert font */
    public Font alertFont;

    public FPS_MenuPanel(int width, int height) {

        /* Create menu font */
        menuFont = new Font("Dialog", Font.BOLD, 36);
        /* Create alert font */
        alertFont = new Font("Dialog", Font.BOLD, 92);

        /* Assign the value width to global parameter */
        this.panelWidth = width;
        /* Assign the value height to global parameter */
        this.panelHeight = height;

        JMenuItem ResumeItem = new JMenuItem("Resume");
        add(ResumeItem);

        JMenuItem ChangeFreqItem = new JMenuItem("Change frequency");
        add(ChangeFreqItem);

        JMenuItem ChangeSizeItem = new JMenuItem("Change object size");
        add(ChangeSizeItem);

        JMenuItem ExitItem = new JMenuItem("Exit");
        add(ExitItem);
    }

}

/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import javax.swing.JPanel;
import java.awt.*;

public class FPS_Panel extends JPanel {

    public int panelWidth;
    public int panelHeight;
    public int barHeight;

    public FPS_Status fpsStatus;
    public Font menuFont;
    public Font alertFont;

    public FPS_Panel(int width, int height) {
        fpsStatus = new FPS_Status();
        fpsStatus.reset();
        menuFont = new Font("Dialog", Font.BOLD, 36);
        alertFont = new Font("Dialog", Font.BOLD, 92);

        this.panelWidth = width;
        this.panelHeight = height;
        barHeight = 50;
    }
}

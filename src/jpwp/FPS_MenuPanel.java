/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */

package jpwp;

import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class FPS_MenuPanel extends JPanel {

    /* parameter that defines game panel width */
    private int panelWidth;
    /* parameter that defines game panel height */
    private int panelHeight;

    /* Definition of fpsStatus object */
    public FPS_Status fpsStatus;
    /* Definition of menu font */
    private Font menuFont;

    private FPS_LayoutPanel fpsLayoutManager; // definition of layout manager

    private String resumeGameString = "RESUME GAME";
    private String changeFreqString = "CHANGE FREQUENCY";
    private String changeSizeString = "CHANGE SIZE";
    private String endGameString = "END GAME";

    public FPS_MenuPanel(FPS_LayoutPanel fpsLayoutManager, int width, int height) {
        super();
        /* Create menu font */
        menuFont = new Font("Dialog", Font.BOLD + Font.ITALIC, 50);

        this.fpsLayoutManager = fpsLayoutManager;
        /* Assign the value width to global parameter */
        this.panelWidth = width;
        /* Assign the value height to global parameter */
        this.panelHeight = height;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                int mouseX = mEvent.getX(); // get mouse X coordinate
                int mouseY = mEvent.getY(); // get mouse Y coordinate

                // check if the resume button was clicked
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 240
                        && mouseY < 350) {
                    FPS_GPars.pause = false;
                    FPS_GPars.levelPause = false;
                    fpsLayoutManager.showGamePanel();
                }

                // check if the end game was clicked
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 700
                        && mouseY < 800) {
                    System.exit(0);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics gs) {
        Graphics2D g = (Graphics2D) gs;
        // Set antyalisiang and texture smoothing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw the background
        g.drawImage(FPS_GPars.bgImage, 0, 0, null);

        g.setFont(menuFont);
        g.setColor(Color.BLACK);

        FontMetrics fm = g.getFontMetrics(); // get current font
        int resumeGameStringWidth = fm.stringWidth(resumeGameString); // get width of given string
        int changeFreqStringWidth = fm.stringWidth(changeFreqString); // get width of given string
        int changeSizeStringWidth = fm.stringWidth(changeSizeString); // get width of given string
        int endGameStringWidth = fm.stringWidth(endGameString); // get width of given string

        int menuGameImageWidth = FPS_GPars.menuGameImage.getWidth(getFocusCycleRootAncestor()); // get width of the
                                                                                                // image

        g.setColor(Color.BLACK);
        g.drawImage(FPS_GPars.menuGameImage, (panelWidth / 2) - (menuGameImageWidth / 2), 100, null);
        g.drawString(resumeGameString, (panelWidth / 2) - (resumeGameStringWidth / 2), 300);
        g.drawString(changeFreqString, (panelWidth / 2) - (changeFreqStringWidth / 2), 450);
        g.drawString(changeSizeString, (panelWidth / 2) - (changeSizeStringWidth / 2), 600);
        g.setColor(Color.RED);
        g.drawString(endGameString, (panelWidth / 2) - (endGameStringWidth / 2), 750);
    }
}

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
    /* Definition of alert font */
    private Font alertFont;

    private FPS_LayoutPanel fpsLayoutManager; // definition of layout manager

    private String newGameString = "NEW GAME";
    private String changeSpeedString = "CHANGE SPEED";
    private String changeSizeString = "CHANGE SIZE";
    private String endGameString = "END GAME";

    public FPS_MenuPanel(FPS_LayoutPanel fpsLayoutManager, int width, int height) {
        super();
        /* Create menu font */
        menuFont = new Font("Dialog", Font.BOLD, 48);
        /* Create alert font */
        alertFont = new Font("Dialog", Font.BOLD, 92);

        this.fpsLayoutManager = fpsLayoutManager;
        /* Assign the value width to global parameter */
        this.panelWidth = width;
        /* Assign the value height to global parameter */
        this.panelHeight = height;

        System.out.println(+panelWidth);
        System.out.println(+panelHeight);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                int mouseX = mEvent.getX();
                int mouseY = mEvent.getY();

                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 240
                        && mouseY < 350) {
                    fpsLayoutManager.showGamePanel();
                }

                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 560
                        && mouseY < 660) {
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

        FontMetrics fm = g.getFontMetrics();
        int newGameStringWidth = fm.stringWidth(newGameString);
        int changeSpeedStringWidth = fm.stringWidth(changeSpeedString);
        int changeSizeStringWidth = fm.stringWidth(changeSizeString);
        int endGameStringWidth = fm.stringWidth(endGameString);

        int menuGameImageWidth = FPS_GPars.menuGameImage.getWidth(getFocusCycleRootAncestor());

        g.setColor(Color.BLACK);
        g.drawImage(FPS_GPars.menuGameImage, (panelWidth / 2) - (menuGameImageWidth / 2), 100, null);
        g.drawString(newGameString, (panelWidth / 2) - (newGameStringWidth / 2), 300);
        g.drawString(changeSpeedString, (panelWidth / 2) - (changeSpeedStringWidth / 2), 450);
        g.drawString(changeSizeString, (panelWidth / 2) - (changeSizeStringWidth / 2), 600);
        g.setColor(Color.RED);
        g.drawString(endGameString, (panelWidth / 2) - (endGameStringWidth / 2), 750);
    }
}

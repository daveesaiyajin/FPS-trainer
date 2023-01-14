/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */

package jpwp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.geom.Rectangle2D;

public class FPS_MainMenuPanel extends JPanel {

    /* parameter that defines menu panel width */
    private int panelWidth;
    /* parameter that defines menu panel height */
    private int panelHeight;
    /* Definition of menu font */
    private Font menuFont;
    /* Variable that handle the title of the game */
    private String GameTitle = "FPS Game";
    /* Variable that defines button width */
    private int buttonWidth = 200;
    /* Variable that defines button height */
    private int buttonHeight = 100;

    private FPS_LayoutPanel fpsLayoutManager;

    private String newGameString = "NEW GAME";
    private String aboutString = "ABOUT THE GAME";
    private String exitString = "EXIT GAME";

    public FPS_MainMenuPanel(FPS_LayoutPanel fpsLayoutManager, int width, int height) {
        super();
        /* Create menu font */
        menuFont = new Font("Menu", Font.BOLD, 48);
        /* Assign the value width to global parameter */
        this.fpsLayoutManager = fpsLayoutManager;
        this.panelWidth = width;
        this.panelHeight = height;

        System.out.println(+panelWidth);
        System.out.println(+panelHeight);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                int mouseX = mEvent.getX();
                int mouseY = mEvent.getY();

                if (mouseX > ((panelWidth / 2) - 100) && mouseX < ((panelWidth / 2) + 100) && mouseY < 250) {
                    System.out.println("Czemu nie działa?");
                    fpsLayoutManager.showGamePanel();
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
        int aboutStringWidth = fm.stringWidth(aboutString);
        int exitStringWidth = fm.stringWidth(exitString);

        int menuGameImageWidth = FPS_GPars.menuGameImage.getWidth(getFocusCycleRootAncestor());
        int menuGameImageHeight = FPS_GPars.menuGameImage.getHeight(getFocusCycleRootAncestor());
        int menuImageWidth = FPS_GPars.menuImage.getWidth(getFocusCycleRootAncestor());
        int menuImageHeight = FPS_GPars.menuImage.getHeight(getFocusCycleRootAncestor());
        int logoImageWidth = FPS_GPars.logoImage.getWidth(getFocusCycleRootAncestor());
        int logoImageHeight = FPS_GPars.logoImage.getHeight(getFocusCycleRootAncestor());

        g.setColor(Color.BLACK);
        g.drawImage(FPS_GPars.menuGameImage, (panelWidth / 2) - (menuGameImageWidth / 2), 100, null);
        g.drawString(newGameString, (panelWidth / 2) - (newGameStringWidth / 2), 250);
        g.drawString(aboutString, (panelWidth / 2) - (aboutStringWidth / 2), 400);
        g.setColor(Color.MAGENTA);
        g.drawString(exitString, (panelWidth / 2) - (exitStringWidth / 2), 700);

    }

}

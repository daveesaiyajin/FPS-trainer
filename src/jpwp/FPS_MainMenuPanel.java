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

import javax.swing.JPanel;

public class FPS_MainMenuPanel extends JPanel {

    /* parameter that defines menu panel width */
    private int panelWidth;
    /* parameter that defines menu panel height */
    private int panelHeight;
    /* Definition of menu font */
    private Font menuFont;

    private FPS_LayoutPanel fpsLayoutManager;

    private String newGameString = "NEW GAME";
    private String aboutString = "ABOUT THE GAME";
    private String exitString = "EXIT GAME";

    public FPS_MainMenuPanel(FPS_LayoutPanel fpsLayoutManager, int width, int height) {
        super();
        menuFont = new Font("Menu", Font.BOLD + Font.ITALIC, 50); // create new font
        this.fpsLayoutManager = fpsLayoutManager; // initialize fpsLayoutManager
        this.panelWidth = width; // assign value to variable
        this.panelHeight = height; // assign value to variable

        FPS_GPars.pause = false;
        FPS_GPars.levelPause = false;
        FPS_GPars.mainMenu = true;
        FPS_GPars.end = false;

        /* override the method responsible of catching the mouse click */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                int mouseX = mEvent.getX(); // get X coordinates of mouse
                int mouseY = mEvent.getY(); // get Y coordinates of mouse

                // check if the new game string was pointed
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 240
                        && mouseY < 350) {
                    fpsLayoutManager.restartGamePanel();
                }

                // check if about game string was pointed
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 400
                        && mouseY < 500) {
                    System.out.println("Naciśnięto przycisk");
                }

                // check if end game string was pointed
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 560
                        && mouseY < 660) {
                    System.exit(0);
                }
            }
        }); // end of MouseListener method

    }

    /*
     * override the paint component method to draw main menu panel with all the
     * buttons
     * 
     * @param gs
     */
    @Override
    protected void paintComponent(Graphics gs) {
        Graphics2D g = (Graphics2D) gs;
        // Set antyalisiang and texture smoothing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(FPS_GPars.bgImage, 0, 0, null); // Draw the background

        g.setFont(menuFont);
        g.setColor(Color.BLACK);

        FontMetrics fm = g.getFontMetrics(); // get the font metrics
        int newGameStringWidth = fm.stringWidth(newGameString); // get width of the string based on the initialized font
        int aboutStringWidth = fm.stringWidth(aboutString); // get width of the string based on the initialized font
        int exitStringWidth = fm.stringWidth(exitString); // get width of the string based on the initialized font

        int menuGameImageWidth = FPS_GPars.menuGameImage.getWidth(getFocusCycleRootAncestor()); // get width of the
                                                                                                // image

        g.setColor(Color.WHITE);
        g.drawImage(FPS_GPars.menuGameImage, (panelWidth / 2) - (menuGameImageWidth / 2), 100, null);
        g.drawString(newGameString, (panelWidth / 2) - (newGameStringWidth / 2), 300);
        g.drawString(aboutString, (panelWidth / 2) - (aboutStringWidth / 2), 450);
        g.setColor(Color.RED);
        g.drawString(exitString, (panelWidth / 2) - (exitStringWidth / 2), 600);

    } // end of paintComponent() method

}

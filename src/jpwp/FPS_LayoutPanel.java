/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */

package jpwp;

import javax.swing.JPanel;
import java.awt.CardLayout;

/* This class is definition of the main JPanel object that will be responsible of managing the layout setup */

public class FPS_LayoutPanel extends JPanel {

    private FPS_Panel fpsGamePanel; // game panel definition
    private FPS_MainMenuPanel fpsMainMenuPanel; // main menu panel definition
    private FPS_MenuPanel fpsMenuPanel; // menu panel definition
    private CardLayout cardLayout; // definition of layout manager

    public FPS_LayoutPanel(int width, int height) {

        cardLayout = new CardLayout(); // constructor of the layout
        setLayout(cardLayout); // set the panel layout

        fpsMainMenuPanel = new FPS_MainMenuPanel(this, width, height); // constructor of main menu panel
        fpsMenuPanel = new FPS_MenuPanel(this, width, height); // constructor of the menu panel
        fpsGamePanel = new FPS_Panel(this, width, height); // constructor of main menu panel
        add(fpsMainMenuPanel, "mainMenuPanel"); // add main menu panel to the layout
        add(fpsGamePanel, "gamePanel"); // add game panel to layout
        add(fpsMenuPanel, "menuPanel"); // add menu panel to layout

    }

    // method to show main menu panel
    public void showMainMenu() {
        FPS_GPars.mainMenu = true;
        FPS_GPars.pause = true;
        FPS_GPars.levelPause = true;
        cardLayout.show(this, "mainMenuPanel");
        repaint();
    } // end of show main menu panel method

    // method to show game panel
    public void showGamePanel() {
        FPS_GPars.mainMenu = false;
        FPS_GPars.pause = false;
        FPS_GPars.levelPause = false;
        cardLayout.show(this, "gamePanel");
        repaint();
    } // end of show game panel method

    // method to show menu panel
    public void showMenuPanel() {
        FPS_GPars.mainMenu = false;
        FPS_GPars.levelPause = true;
        cardLayout.show(this, "menuPanel");
        repaint();
    } // end of show menu panel method
}

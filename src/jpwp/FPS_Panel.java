/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class FPS_Panel extends JPanel {

    /* parameter that defines game panel width */
    public int panelWidth;
    /* parameter that defines game panel height */
    public int panelHeight;
    /* parameter that defines menu bar height */
    public int barHeight;

    /* Number of objects on the screen */
    public int objectsOnTheScreen;
    /* Shift between the objects on the screen */
    public int objectsShift;

    /* Definition of fpsStatus object */
    public FPS_Status fpsStatus;
    /* Definition of menu font */
    public Font menuFont;
    /* Definition of alert font */
    public Font alertFont;

    /** Table of objects */
    private FPS_GameObjects[] gameObjects;

    public FPS_Panel(int width, int height) {
        /* Create the object */
        fpsStatus = new FPS_Status();
        /* Reset all the game parameters */
        fpsStatus.reset();
        /* Create menu font */
        menuFont = new Font("Dialog", Font.BOLD, 36);
        /* Create alert font */
        alertFont = new Font("Dialog", Font.BOLD, 92);

        /* Assign the value width to global parameter */
        this.panelWidth = width;
        /* Assign the value height to global parameter */
        this.panelHeight = height;
        /* Set the bar height - given in pixels */
        barHeight = 200;

        objectsOnTheScreen = 4;
        objectsShift = panelWidth / (FPS_GPars.noOfObjects / objectsOnTheScreen);
        gameObjects = new FPS_GameObjects[FPS_GPars.noOfObjects];

        restartFPSGame();

        /* Mouse action definition */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                int mouseX = mEvent.getX(); // position X of the coursor
                int mouseY = mEvent.getY(); // position Y of the coursor

                /* Check if the menu bar was choosen */
                if (mouseX > (width - 150) && mouseY > (height - barHeight)) {
                    FPS_GPars.pause = !FPS_GPars.pause;
                    return;
                }

                /* Check if user choose Exit button */
                if (mouseX < 300 && mouseY > (height - barHeight)) {
                    if (FPS_GPars.pause) {
                        System.exit(1);
                    }
                }
            }
        }); // end of MouseListener method
    }

    /**
     * Nadpisz metodę odpowiedzialną za odrysowanie panelu - własne wypełnienie
     * pola graficznego gry, zgodnie z wybraną treścią.
     * 
     * @param gs
     */
    @Override
    protected void paintComponent(Graphics gs) {
        Graphics2D g = (Graphics2D) gs;
        // Set antyalisiang and texture smoothing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw the background
        g.drawImage(FPS_GPars.bgImage, 0, 0, null);

        // Na tle obiektu pierwszego planu
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i].calculatePathPos(FPS_GPars.MoveMODE);
            if (!gameObjects[i].hit)
                g.drawImage(gameObjects[i].icon, gameObjects[i].currX, panelHeight - gameObjects[i].currY,
                        (int) (gameObjects[i].icon.getWidth(null)
                                * (1.0 - gameObjects[i].currY / (double) panelHeight)),
                        (int) (gameObjects[i].icon.getHeight(null)
                                * (1.0 - gameObjects[i].currY / (double) panelHeight)),
                        null);
        }

        // Ustaw kolor dolnego paska Menu i narysuj pasek
        g.setColor(new Color(50, 30, 0));
        g.fillRect(0, panelHeight - barHeight, panelWidth, barHeight);
        // Ustaw kolor domyślny
        g.setColor(Color.white);
        // Ustaw czcionki do wypełnienia paska Menu
        g.setFont(menuFont);

        // Jeśli już wybrano Menu (czyli pausa) narysuj stosowną wersję paska Menu
        if (FPS_GPars.pause) {
            g.drawImage(FPS_GPars.menuGameImage, panelWidth - 150, panelHeight - barHeight - 30, null);
            g.setColor(Color.red);
            g.drawString("KONIEC GRY!", 10, panelHeight - 10);
            g.setColor(Color.white);
            g.drawString("O GRZE...", 300, panelHeight - 10);
            g.drawString("NOWA GRA!", 550, panelHeight - 10);
            if (FPS_GPars.end) { // Czy wszystkie poziomy skończone - koniec gry
                g.setColor(Color.RED);
                // g.drawString("KONIEC?",500, panelHeight-10);
                g.setFont(alertFont);
                DecimalFormat df = new DecimalFormat("#.##");
                g.drawString("KONIEC GRY! ", 170, panelHeight / 2);
                g.drawString("CZAS RAZEM=" + df.format(fpsStatus.time) + "s", 10, panelHeight / 2 + 100);
                g.setColor(Color.white);
                g.setFont(menuFont);
            }
            // Nie wybranu nic z menu - pokaż poziom i stan punktów w trakcie gry
        } else {
            g.drawString("POZIOM:", 10, panelHeight - 10);

            g.drawString("" + fpsStatus.level, 200, panelHeight - 10);
            g.drawString("PUNKTY:", 300, panelHeight - 10);
            // Czy ukończono poziom - wskazano wszystkie obiekty pozciomu
            if (fpsStatus.ObjectsCounter == FPS_GPars.noOfObjects) {
                if (!FPS_GPars.levelPause) {
                    long stopTime = System.currentTimeMillis();
                    FPS_GPars.levelTime = (stopTime - FPS_GPars.startTime) / 1000.0;
                    FPS_GPars.levelPause = true;
                }
                g.setColor(Color.RED);
                g.drawString("GRASZ DALEJ?", 500, panelHeight - 10);
                g.setFont(alertFont);
                DecimalFormat df = new DecimalFormat("#.##");
                g.drawString("WYGRANA:" + df.format(FPS_GPars.levelTime) + "s", 150, panelHeight / 2);
                g.setColor(Color.white);
                g.setFont(menuFont);
                // Jak nie zmień punkty jeśli stosowne
            } else
                g.drawString("" + fpsStatus.ObjectsCounter, 500, panelHeight - 10);
            // narysuj ikonę z napisem Menu
            g.drawImage(FPS_GPars.menuImage, panelWidth - 150, panelHeight - barHeight - 30, null);
        }
        // narysuj ikonę z logo
        g.drawImage(FPS_GPars.logoImage, panelWidth - 180, panelHeight - barHeight + 15, null);

    }

    public void restartFPSGame() {
        fpsStatus.reset();
        FPS_GPars.startTime = System.currentTimeMillis();
        FPS_GPars.pause = false;
        int offset = panelWidth / objectsOnTheScreen;
        int inLine = 0;
        int yLine = 0;
        for (int i = 0; i < FPS_GPars.noOfObjects; i++) {

            gameObjects[i] = new FPS_GameObjects(
                    (((inLine % objectsOnTheScreen) + 1) * offset)
                            - FPS_GPars.balloons[(i % FPS_GPars.balloons.length)].getWidth(null),
                    0, 100, 0.0025, FPS_GPars.balloons);
            gameObjects[i].setScreenSize(panelWidth, panelHeight);

            if (inLine >= objectsOnTheScreen) {
                yLine++;
                inLine %= objectsOnTheScreen;
            }
            inLine++;
            gameObjects[i].setYPos(yLine * objectsShift * -1);
        } // koniec for i
    }
}

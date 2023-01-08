/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import javax.swing.JButton;
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
        barHeight = 130;

        objectsOnTheScreen = 4;
        objectsShift = panelWidth / (FPS_GPars.noOfObjects / objectsOnTheScreen);
        gameObjects = new FPS_GameObjects[FPS_GPars.noOfObjects];

        restartFPSGame();

        /* Mouse action definition */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                int mouseX = mEvent.getX();
                int mouseY = mEvent.getY();

                /* Choose menu bar */
                if (mouseX > (width - 400) && mouseY > (height - barHeight)) {
                    FPS_GPars.pause = !FPS_GPars.pause;
                    return;
                }

                /* Check if user choose end game button */
                if (mouseX < ((panelWidth / 8) + 30) && mouseY > (height - barHeight)) {
                    if (FPS_GPars.pause) {
                        System.exit(1);
                    }
                }
                // czy wybrano rozpoczęcie nowego poziomu lub nowej gry
                if (mouseX > ((panelWidth / 8) + 30) && mouseX < 800
                        && mouseY > (panelHeight - barHeight)) {
                    // Nowa gra
                    if (FPS_GPars.pause) {
                        FPS_GPars.MoveMODE = 1;
                        FPS_GPars.end = false;
                        fpsStatus.reset();
                        FPS_GPars.levelPause = false;
                        FPS_GPars.bgImage = FPS_GPars.loadImage("images/water_1920_1080.jpg");
                        restartFPSGame();
                        repaint();
                    } else {
                        // Nowy poziom
                        if (FPS_GPars.levelPause) {
                            // Czy dostępny jest kolejny poziom
                            if (FPS_GPars.MoveMODE < FPS_GPars.NO_LEVELS) {
                                FPS_GPars.MoveMODE++;
                                fpsStatus.time += FPS_GPars.levelTime;
                                FPS_GPars.levelPause = false;
                                FPS_GPars.bgImage = FPS_GPars.loadImage("images/water_1920_1080.jpg");
                                fpsStatus.nextLevel();
                                restartFPSGame();
                            } else {
                                // koniec poziomów = koniec gry
                                FPS_GPars.end = true;
                                fpsStatus.time += FPS_GPars.levelTime;
                                FPS_GPars.pause = true;
                            }
                            repaint();
                        }
                    }
                }
                // inaczej sprawdź czy trafiono obiekt (balon)
                for (int i = 0; i < gameObjects.length; i++) {
                    if (mouseY < (panelHeight - barHeight)) {
                        if (gameObjects[i].containsPoint(mouseX, mouseY)) {
                            if (!gameObjects[i].hit) {
                                gameObjects[i].setHit();
                                fpsStatus.ObjectsCounter++;
                            }
                        }
                    }
                } // koniec for i
            }
        }); // end of MouseListener method
    }

    /**
     * Overwrite the method that is responsible of painting the panel
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
        g.setColor(new Color(70, 40, 20));
        g.fillRect(0, panelHeight - barHeight, panelWidth, barHeight);
        // Ustaw czcionki do wypełnienia paska Menu
        g.setFont(menuFont);

        // Draw the menu once the pause is set to true
        if (FPS_GPars.pause) {
            g.setColor(Color.RED);
            g.drawImage(FPS_GPars.menuGameImage, panelWidth / 2, panelHeight - 200, null);
            g.drawString("END GAME", (panelWidth / 8), panelHeight - 80);
            g.setColor(Color.WHITE);
            g.drawString("ABOUT GAME", 3 * (panelWidth / 8), panelHeight - 80);
            g.drawString("CHANGE SETTINGS", 5 * (panelWidth / 8), panelHeight - 80);
            g.drawString("NEW GAME", 7 * (panelWidth / 8), panelHeight - 80);
            if (FPS_GPars.end) { // Czy wszystkie poziomy skończone - koniec gry
                g.setColor(Color.RED);
                g.setFont(alertFont);
                DecimalFormat df = new DecimalFormat("#.##");
                g.drawString("GAME IS FINISHED! ", panelWidth / 2, panelHeight / 2);
                g.setColor(Color.white);
                g.drawString("TOTAL TIME=" + df.format(fpsStatus.time) + "s", panelWidth / 2, panelHeight / 2 - 200);
                g.setFont(menuFont);
            }
            // Nie wybranu nic z menu - pokaż poziom i stan punktów w trakcie gry
        } else {
            g.setColor(Color.WHITE);
            g.drawString("LEVEL:" + fpsStatus.level, 50, panelHeight - 75);
            g.drawString("Object counter:" + fpsStatus.ObjectsCounter, 300, panelHeight - 75);
            // Czy ukończono poziom - wskazano wszystkie obiekty pozciomu
            if (fpsStatus.ObjectsCounter == FPS_GPars.noOfObjects) {
                if (!FPS_GPars.levelPause) {
                    long stopTime = System.currentTimeMillis();
                    FPS_GPars.levelTime = (stopTime - FPS_GPars.startTime) / 1000.0;
                    FPS_GPars.levelPause = true;
                }
                g.setColor(Color.RED);
                g.drawString("GRASZ DALEJ?", panelWidth / 2, panelHeight - 300);
                g.setFont(alertFont);
                DecimalFormat df = new DecimalFormat("#.##");
                g.setColor(Color.white);
                g.drawString("WYGRANA:" + df.format(FPS_GPars.levelTime) + "s", panelWidth / 2, panelHeight / 2);
                g.setFont(menuFont);
                // Jak nie zmień punkty jeśli stosowne
            } else
                g.drawString("" + fpsStatus.ObjectsCounter, 500, panelHeight - 10);
            // narysuj ikonę z napisem Menu
            g.drawImage(FPS_GPars.menuImage, panelWidth - 400, panelHeight - barHeight, null);
        }
        // Draw the icon
        g.drawImage(FPS_GPars.logoImage, panelWidth / 2, panelHeight - barHeight + 20, null);

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

/**
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.DecimalFormat;

import javax.swing.JPanel;

/**
 * Definition of the FPS_Panel class that contains details of the game panel
 * that will be displayed
 */

public class FPS_Panel extends JPanel {

    /** parameter that defines game panel width */
    private int panelWidth;
    /** parameter that defines game panel height */
    private int panelHeight;
    /** parameter that defines menu bar height */
    private int barHeight;

    /** Number of objects on the screen */
    public int objectsOnTheScreen;
    /** Shift between the objects on the screen */
    public int objectsShift;

    /** Definition of fpsStatus object */
    public FPS_Status fpsStatus;
    /** Definition of menu font */
    public Font menuFont;
    /** Definition of alert font */
    public Font alertFont;

    /** Table of objects */
    private FPS_GameObjects[] gameObjects;

    /** definition of layout manager */
    private FPS_LayoutPanel fpsLayoutManager;

    /** string definition */
    private String newGameString = "NEW GAME";
    /** string definition */
    private String gameFinishedString = "GAME IS FINISHED!";
    /** string definition */
    private String totalTimeString = "TOTAL TIME= ";
    /** string definition */
    private String levelString = "LEVEL: ";
    /** string definition */
    private String objectCounterString = "OBJECT COUNTER: ";
    /** string definition */
    private String continueString = "CONTINUE";
    /** string definition */
    private String winTimeString = "WIN! Time: ";

    /**
     * This is the layout manager panel definition
     * 
     * @param fpsLayoutManager - referrence to the layout manager panel
     * @param width            - width of the panel
     * @param height           - height of the panel
     */
    public FPS_Panel(FPS_LayoutPanel fpsLayoutManager, int width, int height) {
        super();
        /** Create the object */
        fpsStatus = new FPS_Status();
        /** Reset all the game parameters */
        fpsStatus.reset();
        /** Create menu font */
        menuFont = new Font("MenuButton", Font.BOLD + Font.ITALIC, 50);
        /** Create alert font */
        alertFont = new Font("Dialog", Font.BOLD + Font.ITALIC, 72);
        this.fpsLayoutManager = fpsLayoutManager;
        /** Assign the value width to global parameter */
        this.panelWidth = width;
        /** Assign the value height to global parameter */
        this.panelHeight = height;
        /** Set the bar height - given in pixels */
        barHeight = 130;

        /** definition of objects displayed in the line */
        objectsOnTheScreen = 5;
        /** shift between displayed objects */
        objectsShift = panelWidth / (FPS_GPars.noOfObjects[fpsStatus.level - 1] / objectsOnTheScreen);
        /** constructor of game objects */
        gameObjects = new FPS_GameObjects[FPS_GPars.noOfObjects[fpsStatus.level - 1]];

        restartFPSGame();

        /**
         * Override the mouse listener
         * 
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                /** get X coordinates of the mouse */
                int mouseX = mEvent.getX();
                /** get Y coordinates of the mouse */
                int mouseY = mEvent.getY();

                /* Choose menu bar */
                if (mouseX > ((panelWidth / 2 - 50)) && mouseX < ((panelWidth / 2 + 50))
                        && mouseY > (panelHeight - barHeight)) {
                    FPS_GPars.levelPause = true;
                    FPS_GPars.pause = false;
                    fpsLayoutManager.showMenuPanel();
                    return;
                }
                // new game or new level
                if (mouseX > ((panelWidth / 8) - 35) && mouseX < ((panelWidth / 8) + 35)
                        && mouseY > (panelHeight - barHeight) && mouseY < (panelHeight - 30)) {
                    // new game
                    if (FPS_GPars.pause) {
                        FPS_GPars.MoveMODE = 1;
                        FPS_GPars.end = false;
                        fpsStatus.reset();
                        FPS_GPars.levelPause = false;
                        FPS_GPars.bgImage = FPS_GPars.loadImage("images/water_1920_1080.jpg");
                        restartFPSGame();
                        repaint();
                    } else {
                        // new level
                        if (FPS_GPars.levelPause) {
                            // check if there is another level
                            if (FPS_GPars.MoveMODE < FPS_GPars.NO_LEVELS) {
                                FPS_GPars.MoveMODE++;
                                fpsStatus.time += FPS_GPars.levelTime;
                                FPS_GPars.levelPause = false;
                                FPS_GPars.bgImage = FPS_GPars.loadImage("images/water_1920_1080.jpg");
                                fpsStatus.nextLevel();
                                restartFPSGame();
                            } else {
                                // game ends - no new levels
                                FPS_GPars.end = true;
                                fpsStatus.time += FPS_GPars.levelTime;
                                FPS_GPars.pause = true;
                            }
                            repaint();
                        }
                    }
                }
                // check if the baloon was hit
                for (int i = 0; i < gameObjects.length; i++) {
                    if (mouseY < (panelHeight - barHeight)) {
                        if (gameObjects[i].containsPoint(mouseX, mouseY)) {
                            if (!gameObjects[i].hit) {
                                gameObjects[i].setHit();
                                fpsStatus.ObjectsCounter++;
                            }
                        }
                    }
                } // end of for i loop
            }
        }); // end of MouseListener method
    }

    /**
     * Overwrite the paiting method
     * 
     * @param gs
     */
    @Override
    protected void paintComponent(Graphics gs) {
        Graphics2D g = (Graphics2D) gs;
        /** Set antyalisiang and texture smoothing */
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /** Draw the background */
        g.drawImage(FPS_GPars.bgImage, 0, 0, null);

        /** draw on the first plan */
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i].calculatePathPos(FPS_GPars.MoveMODE);
            if (!gameObjects[i].hit)
                g.drawImage(gameObjects[i].icon, gameObjects[i].currX, panelHeight - gameObjects[i].currY,
                        (int) (gameObjects[i].icon.getWidth(this)
                                * (1.0 - gameObjects[i].currY / (double) panelHeight)),
                        (int) (gameObjects[i].icon.getHeight(this)
                                * (1.0 - gameObjects[i].currY / (double) panelHeight)),
                        this);
        }

        /** Set the color and draw the bottom bar */
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, panelHeight - barHeight, panelWidth, barHeight);
        /** set the font */
        g.setFont(menuFont);

        /** get current font */
        FontMetrics fm = g.getFontMetrics();

        /** get width of the string based on the initialized font */
        int newGameStringWidth = fm.stringWidth(newGameString);
        /** get width of string */
        int gameFinishedStringWidth = fm.stringWidth(gameFinishedString);
        /** get width of string */
        int totalTimeStringWidth = fm.stringWidth(totalTimeString);

        /** get width of string */
        int continueStringWidth = fm.stringWidth(continueString);
        /** get width of string */
        int winTimeStringWidth = fm.stringWidth(winTimeString);

        /** get image width */
        int menuImageWidth = FPS_GPars.menuImage.getWidth(getFocusCycleRootAncestor());
        /** get image height */
        int menuImageHeight = FPS_GPars.menuImage.getHeight(getFocusCycleRootAncestor());

        // Draw the details once game is fully end
        if (FPS_GPars.pause && FPS_GPars.end) {
            g.setColor(Color.WHITE);
            g.setFont(alertFont);
            DecimalFormat df = new DecimalFormat("#.##");
            g.drawString(newGameString, (panelWidth / 8) - (newGameStringWidth / 2), panelHeight - (barHeight / 2));
            g.setColor(Color.RED);
            g.drawString(gameFinishedString, 5 * (panelWidth / 8) - (gameFinishedStringWidth / 2),
                    panelHeight - (barHeight / 2));
            g.setColor(Color.WHITE);
            g.drawString(totalTimeString + df.format(fpsStatus.time) + "s",
                    7 * (panelWidth / 2) - (totalTimeStringWidth / 2), panelHeight - (barHeight / 2));
            g.setFont(menuFont);
        }

        else {
            // Check if all objects for the level was hitted
            if (fpsStatus.ObjectsCounter == FPS_GPars.noOfObjects[fpsStatus.level - 1]) {
                if (!FPS_GPars.levelPause) {
                    long stopTime = System.currentTimeMillis();
                    FPS_GPars.levelTime = (stopTime - FPS_GPars.startTime) / 1000.0;
                    FPS_GPars.levelPause = true;
                }
                // draw the panel once the level is end
                g.setColor(Color.RED);
                g.drawString(continueString, (panelWidth / 8) - (continueStringWidth / 2),
                        panelHeight - (barHeight / 2));
                g.setFont(alertFont);
                DecimalFormat df = new DecimalFormat("#.##");
                g.setColor(Color.WHITE);
                g.drawString(winTimeString + df.format(FPS_GPars.levelTime) + "s",
                        5 * (panelWidth / 8) - (winTimeStringWidth / 2), panelHeight - (barHeight / 2));
                g.setFont(menuFont);
            } else {
                // draw the panel for normal game
                g.setColor(Color.WHITE);
                g.drawString(levelString + fpsStatus.level, 50, panelHeight - (barHeight / 2));
                g.drawString(objectCounterString + fpsStatus.ObjectsCounter, 300, panelHeight - (barHeight / 2));
                g.drawImage(FPS_GPars.menuImage, (panelWidth / 2) - (menuImageWidth / 2),
                        panelHeight - (barHeight / 2) - (menuImageHeight / 2), null);
            }
        }
    }

    /**
     * restartFPSGame method, that restarts the whole game and re-draw the objects
     */

    public void restartFPSGame() {
        fpsStatus.resetObjectsCounter(); // reset the objects counter
        FPS_GPars.startTime = System.currentTimeMillis(); // set the current time
        FPS_GPars.pause = false; // set the game pause flag
        int offset = panelWidth / objectsOnTheScreen; // calculate the offset
        int inLine = 0;
        int yLine = 0;
        for (int i = 0; i < FPS_GPars.noOfObjects[fpsStatus.level - 1]; i++) {

            gameObjects[i] = new FPS_GameObjects(
                    (((inLine % objectsOnTheScreen) + 1) * offset)
                            - FPS_GPars.balloons[(i % FPS_GPars.balloons.length)].getWidth(null),
                    0, 100, (0.0025 * FPS_GPars.frequency), FPS_GPars.balloons);
            gameObjects[i].setScreenSize(panelWidth, panelHeight);

            if (inLine >= objectsOnTheScreen) {
                yLine++;
                inLine %= objectsOnTheScreen;
            }
            inLine++;
            gameObjects[i].setYPos(yLine * objectsShift * -1);
        } // end of for i loop
    } // end of restartFPSGame() method
}

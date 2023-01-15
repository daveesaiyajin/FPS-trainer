/**
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

/**
 * Definition of the menu panel class
 */
public class FPS_MenuPanel extends JPanel {

    /** parameter that defines game panel width */
    private int panelWidth;
    /** parameter that defines game panel height */
    private int panelHeight;
    /** parameter that defines menu bar height */
    private int barHeight;

    /** Definition of fpsStatus object */
    public FPS_Status fpsStatus;
    /** Definition of menu font */
    private Font menuFont;
    /** definition of layout manager */
    private FPS_LayoutPanel fpsLayoutManager;

    /** string variable */
    private String resumeGameString = "RESUME GAME";
    /** string variable */
    private String changeFreqString = "CHANGE FREQUENCY";
    /** string variable */
    private String changeSizeString = "CHANGE SIZE";
    /** string variable */
    private String endGameString = "END GAME";
    /** string variable */
    private String smallerValueString = "0.8";
    /** string variable */
    private String valueString = "1";
    /** string variable */
    private String biggerValueString = "1.2";

    /**
     * FPS_MenuPanel method that creates the menu panel
     * 
     * @param fpsLayoutManager - referrence to the layout manager panel
     * @param width            - width of the panel
     * @param height           - height of the panel
     */
    public FPS_MenuPanel(FPS_LayoutPanel fpsLayoutManager, int width, int height) {
        super();
        /** Create menu font */
        menuFont = new Font("Dialog", Font.BOLD + Font.ITALIC, 50);

        /** assign the fpsLayoutManager */
        this.fpsLayoutManager = fpsLayoutManager;
        /** Assign the value width to global parameter */
        this.panelWidth = width;
        /** Assign the value height to global parameter */
        this.panelHeight = height;
        /** Set the bar height - given in pixels */
        barHeight = 130;

        /**
         * Override the mouseListener method
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mEvent) {

                /** get mouse X coordinate */
                int mouseX = mEvent.getX();
                /** get mouse Y coordinate */
                int mouseY = mEvent.getY();

                /** check if the resume button was clicked */
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 240
                        && mouseY < 350) {
                    FPS_GPars.pause = false;
                    FPS_GPars.levelPause = false;
                    fpsLayoutManager.showGamePanel();
                }

                /** check if change freq button was clicked */
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 410
                        && mouseY < 510 && !FPS_GPars.changeFreq) {
                    FPS_GPars.changeFreq = true;
                    System.out.println("Change freq");
                    repaint();
                }

                /** check if change size button was clicked */
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 560
                        && mouseY < 660 && !FPS_GPars.changeSize) {
                    FPS_GPars.changeSize = true;
                    System.out.println("Change size");
                    repaint();
                }

                /** mouse clicked on the smaller string value */
                if (mouseX > ((panelWidth / 4) - 30) && mouseX < ((panelWidth / 4) + 30)
                        && mouseY > ((panelHeight - barHeight) + 30) && mouseY < (panelHeight - 40)) {
                    // change freq for smaller
                    if (FPS_GPars.changeFreq) {
                        FPS_GPars.frequency = 0.8;
                        FPS_GPars.changeFreq = false;
                        System.out.println("Freq = " + FPS_GPars.frequency);
                        fpsLayoutManager.restartGamePanel();
                    }
                    /** cale the object to smaller */
                    if (FPS_GPars.changeSize) {
                        FPS_GPars.size = 0.8;
                        FPS_GPars.changeSize = false;
                        System.out.println("Size = " + FPS_GPars.size);
                        fpsLayoutManager.restartGamePanel();
                    }
                }

                /** mouse clicked on the normal string value */
                if (mouseX > ((panelWidth / 2) - 30) && mouseX < ((panelWidth / 2) + 30)
                        && mouseY > ((panelHeight - barHeight) + 30) && mouseY < (panelHeight - 40)) {
                    /** change freq for normal */
                    if (FPS_GPars.changeFreq) {
                        FPS_GPars.frequency = 1;
                        FPS_GPars.changeFreq = false;
                        System.out.println("Freq = " + FPS_GPars.frequency);
                        fpsLayoutManager.restartGamePanel();
                    }
                    /** change freq for normal */
                    if (FPS_GPars.changeSize) {
                        FPS_GPars.size = 1;
                        FPS_GPars.changeSize = false;
                        System.out.println("Size = " + FPS_GPars.size);
                        fpsLayoutManager.restartGamePanel();
                    }
                }

                /** ouse clicked on the bigger string value */
                if (mouseX > (3 * (panelWidth / 4) - 30) && mouseX < (3 * (panelWidth / 4) + 30)
                        && mouseY > ((panelHeight - barHeight) + 30) && mouseY < (panelHeight - 40)) {
                    /** change freq for bigger */
                    if (FPS_GPars.changeFreq) {
                        FPS_GPars.frequency = 1.2;
                        FPS_GPars.changeFreq = false;
                        System.out.println("Freq = " + FPS_GPars.frequency);
                        fpsLayoutManager.restartGamePanel();
                    }
                    /** scale the object to bigger */
                    if (FPS_GPars.changeSize) {
                        FPS_GPars.size = 1.2;
                        FPS_GPars.changeSize = false;
                        System.out.println("Size = " + FPS_GPars.size);
                        fpsLayoutManager.restartGamePanel();
                    }
                }

                /** check if the end game was clicked */
                if (mouseX > ((panelWidth / 2) - 50) && mouseX < ((panelWidth / 2) + 50) && mouseY > 700
                        && mouseY < 800) {
                    System.exit(0);
                }
            }
        }); // end of mouseListener method
    }

    /**
     * 
     * Override the paint method
     */
    @Override
    protected void paintComponent(Graphics gs) {
        Graphics2D g = (Graphics2D) gs;
        /** Set antyalisiang and texture smoothing */
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /** Draw the background */
        g.drawImage(FPS_GPars.bgImage, 0, 0, null);

        /** set the color */
        g.setColor(Color.DARK_GRAY);
        /** draw the bar in the bottom */
        g.fillRect(0, panelHeight - barHeight, panelWidth, barHeight);

        /** set the menu font */
        g.setFont(menuFont);
        /** set the color */
        g.setColor(Color.BLACK);

        /** get current font */
        FontMetrics fm = g.getFontMetrics();
        /** get width of given string */
        int resumeGameStringWidth = fm.stringWidth(resumeGameString);
        /** get width of given string */
        int changeFreqStringWidth = fm.stringWidth(changeFreqString);
        /** get width of given string */
        int changeSizeStringWidth = fm.stringWidth(changeSizeString);
        /** get width of given string */
        int endGameStringWidth = fm.stringWidth(endGameString);
        /** get width of given string */
        int smallerValueStringWidth = fm.stringWidth(smallerValueString);
        /** get width of given string */
        int valueStringWidth = fm.stringWidth(valueString);
        /** get width of given string */
        int biggerValueStringWidth = fm.stringWidth(biggerValueString);

        /** get width of the image */
        int menuGameImageWidth = FPS_GPars.menuGameImage.getWidth(getFocusCycleRootAncestor());

        // change the frequency
        if (FPS_GPars.changeFreq) {
            g.setColor(Color.WHITE);
            g.drawString(changeFreqString, (panelWidth / 2) - (changeFreqStringWidth / 2), 450);
            g.drawString(smallerValueString, (panelWidth / 4) - (smallerValueStringWidth / 2),
                    panelHeight - (barHeight / 2));
            g.drawString(valueString, (panelWidth / 2) - (valueStringWidth / 2), panelHeight - (barHeight / 2));
            g.drawString(biggerValueString, 3 * (panelWidth / 4) - (biggerValueStringWidth / 2),
                    panelHeight - (barHeight / 2));
        }
        // change the size
        if (FPS_GPars.changeSize) {
            g.setColor(Color.WHITE);
            g.drawString(changeSizeString, (panelWidth / 2) - (changeSizeStringWidth / 2), 600);
            g.drawString(smallerValueString, (panelWidth / 4) - (smallerValueStringWidth / 2),
                    panelHeight - (barHeight / 2));
            g.drawString(valueString, (panelWidth / 2) - (valueStringWidth / 2), panelHeight - (barHeight / 2));
            g.drawString(biggerValueString, 3 * (panelWidth / 4) - (biggerValueStringWidth / 2),
                    panelHeight - (barHeight / 2));
        }
        // draw normal panel
        if (!FPS_GPars.changeFreq && !FPS_GPars.changeSize) {
            g.setColor(Color.WHITE);
            g.drawImage(FPS_GPars.menuGameImage, (panelWidth / 2) - (menuGameImageWidth / 2), 100, null);
            g.drawString(resumeGameString, (panelWidth / 2) - (resumeGameStringWidth / 2), 300);
            g.drawString(changeFreqString, (panelWidth / 2) - (changeFreqStringWidth / 2), 450);
            g.drawString(changeSizeString, (panelWidth / 2) - (changeSizeStringWidth / 2), 600);
            g.setColor(Color.RED);
            g.drawString(endGameString, (panelWidth / 2) - (endGameStringWidth / 2), 750);
        }
    }
} // end of FPS_MenuPanel

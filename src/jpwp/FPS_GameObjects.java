package jpwp;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Constructor of the objects that will appear on the screen
 */

public class FPS_GameObjects {

    /** Starting point of the object - axis X */
    public int startPosX;
    /** Starting point of the object - axis Y */
    public int startPosY;
    /** Current position of the object on axis X */
    public int currX;
    /** Current position of the object on axis Y */
    public int currY;
    /** Width of the object */
    public int objectWidth;
    /** Height of the object */
    public int objectHeight;

    /** diff of moving the objects from bottom to top */
    public int dy;
    /** angle of the sinus method */
    private double angle;
    /** ampl of sinus function */
    public int ampl;
    /** freq of sinus function that describe the move */
    public double freq;
    /** baloon color */
    public int color;
    /** Omega 2Pi*f */
    public final static double w = 2 * Math.PI;
    /** width of the game panel */
    public int sWidth;
    /** height of the game panel */
    public int sHeight;

    /** flag that sets if the balloon was hit */
    public boolean hit;
    /** icon of the baloong image */
    public Image icon;

    /**
     * Constructor - set the object param, draw the baloon color
     * 
     * @param x      starting point X
     * @param y      starting point Y
     * @param ampl   amplitude of sinus function
     * @param freq   freq of sinus function
     * @param images table of icons
     */
    public FPS_GameObjects(int x, int y, int ampl, double freq, Image[] images) {
        this.startPosX = x;
        this.startPosY = y;
        currX = x;
        currY = y;
        this.dy = 10;
        sWidth = 1920;
        sHeight = 1080;
        hit = false;

        this.ampl = ampl;
        this.freq = freq;
        // draw the baloon color
        color = (int) (0.4 + Math.random() * images.length);
        if (color >= images.length)
            color = images.length - 1;
        icon = images[color];
        objectWidth = icon.getWidth(null);
        objectHeight = icon.getHeight(null);
        // set the omega of sinus 2 Pi f function
        setOmega(this.freq);

    }

    /**
     * Balloon hit - set state and play the sound
     */
    public void setHit() {
        if (!hit) {
            hit = true;
            playSound(new File("sounds/balloon_boom.wav"));
        }
    }// setHit()

    /**
     * Set the balloon position
     * 
     * @param cX - coordinate X
     * @param cY - coordinate Y
     */
    public void setPosition(int cX, int cY) {
        currX = cX;
        currY = cY;
    }// setPosition()

    /**
     * Set the panel width and height
     * 
     * @param gWidth  width
     * @param gHeight height
     */
    public void setScreenSize(int gWidth, int gHeight) {
        sWidth = gWidth;
        sHeight = gHeight;
    }

    /**
     * Set the position Y of the object
     * 
     * @param cY
     */
    public void setYPos(int cY) {
        currY = cY;
    }// setYPos()

    /**
     * Get the object position
     * 
     * @return balloon position
     */
    public Point getPosition() {
        return new Point(currX, currY);
    }// getPosition()

    /**
     * Set 2 Pi f
     * 
     * @param freq
     */
    public void setOmega(double freq) {
        angle = w * freq;
    }// setOmega()

    /**
     * Method that calculate the position of the balloon
     * 
     * @param mode State that describe the move:
     *             1 - linear - from bottom to top
     *             2 - sinus - from bottom to top
     */
    public void calculatePathPos(int mode) {
        int tmpX = 0;
        switch (mode) {
            case 1: // linear
                currY = currY + dy;
                if (currY > sHeight) {
                    currY = 0;
                }
                tmpX = 0;
                currX = startPosX + tmpX;
                break;
            case 2:// sinus
                currY = currY + dy;
                if (currY > sHeight) {
                    currY = 0;
                }
                tmpX = (int) (ampl * Math.sin(angle * currY));
                currX = startPosX + tmpX;

                break;
            default:
                break;
        }

    }// calculatePathPos()

    /**
     * Function tha checks if the mouse click was in the bounds of the object
     * 
     * @param x coordinate X
     * @param y coordinate Y
     * @return true once the balloon was picked
     */
    public boolean containsPoint(int x, int y) {
        // scale the object with the move from bottom to top
        scaleWidthHeight((double) sHeight);
        if (x >= currX && x < currX + objectWidth) {
            if (y >= (sHeight - currY) && y < (sHeight - currY + objectHeight)) {
                return true;
            }
        }

        return false;
    }// containsPoint()

    /**
     * Scale the object with the move from bottom to top
     * 
     * @param scale
     */
    public void scaleWidthHeight(double scale) {
        objectWidth = (int) (icon.getWidth(null) * (1.0 - currY / (scale * FPS_GPars.size)));
        objectHeight = (int) (icon.getHeight(null) * (1.0 - currY / (scale * FPS_GPars.size)));
    }// scaleWidthHeight()

    /**
     * Play the sound
     * 
     * @param f - object of File class that represent path to the MP3 file
     */
    public static synchronized void playSound(final File f) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }// playSound()

}
// end of class GameObjects

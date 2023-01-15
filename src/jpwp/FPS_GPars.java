/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import java.awt.Image;
import javax.swing.ImageIcon;

public class FPS_GPars {
    public static long GAME_TIME = Long.MAX_VALUE; // max game time
    public static long LEVEL_TIME = 600000; // level time set to 10 minutes
    public final static long NO_LEVELS = 2; // defines number of levels
    public static Image bgImage; // set the background image
    public static Image menuImage; // set the menu button image
    public static Image menuGameImage; // set the game image
    public static Image logoImage; // set the logo
    public static Image cursorImage; // set custom cursor image
    public static Image[] balloons; // defines objects
    public static boolean pause = false; // variable that set the current status of the game
    public static boolean mainMenu = true; // variable that defines that we're in main menu
    public static boolean levelPause = false; // variable that checks if level is paused
    public static long startTime; // variable that define the starting time of the game
    public static double levelTime; // defines the level time
    public static int MoveMODE = 1; // defines current levele
    public static boolean changeFreq = false; // check if the change freq button was hit
    public static boolean changeSize = false; // check if the change freq button was hit
    public static double frequency = 1; // set frequency for the level
    public static double size = 1; // set size of the objects
    public static boolean end = false; // defines end of the game
    public static int[] noOfObjects = { 5, 5, 75, 75, 100 }; // defines amount of objects that have to be hit per each
                                                             // level
    public static int gWidth = 1920; // defines width of the panel
    public static int gHeight = 1080; // defines height of the panel

    public static void loadInitialImages() {

        bgImage = loadImage("images/water_1920_1080.jpg");
        menuImage = loadImage("images/menu_1024.png");
        menuGameImage = loadImage("images/gra_1024.png");
        logoImage = loadImage("images/domestic_logo_url.png");
        cursorImage = loadImage("images/target_32_r.png");

        balloons = new Image[5];
        balloons[0] = loadImage("images/b_blue_300.png");
        balloons[1] = loadImage("images/b_red_300.png");
        balloons[2] = loadImage("images/b_violet_300.png");
        balloons[3] = loadImage("images/b_brown_300.png");
        balloons[4] = loadImage("images/b_green_300.png");

    }

    public static Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

}

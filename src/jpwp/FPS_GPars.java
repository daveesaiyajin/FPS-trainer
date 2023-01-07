/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import java.awt.Image;
import javax.swing.ImageIcon;

public class FPS_GPars {
    public static long GAME_TIME = Long.MAX_VALUE;
    public final static long NO_LEVELS = 2;
    public static Image bgImage;
    public static Image menuImage;
    public static Image menuGameImage;
    public static Image logoImage;
    public static Image cursorImage;
    public static Image[] balloons;
    public static boolean pause = false; // variable that set the current status of the game
    public static boolean levelPause = false;
    public static long startTime; // variable that define the starting time of the game
    public static double levelTime;
    public static int MoveMODE = 1;
    public static boolean end = false;
    public static int noOfObjects = 12;
    public static int gWidth = 1024;
    public static int gHeight = 768;

    public static void loadInitialImages() {

        bgImage = loadImage("images/grass_sky_1024.jpg");
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

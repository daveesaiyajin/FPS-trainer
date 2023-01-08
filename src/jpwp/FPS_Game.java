/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

/* 
 * Simple interactive game created for the JPWP classess 
 * author @daveesaiyajin 
 */

public class FPS_Game {

    /*
     * This is a main method that creates GUI and starts the whole game
     */
    public static void main(String[] args) {
        System.out.println("Test czy działa wszystko ");

        /* Definition of the game window width */
        int gameWidth = 1920;
        /* Definition of the game window height */
        int gameHeight = 1080;

        /*
         * This create FPS_Window object which contain definition of GUI and all the
         * actions that will happen over the game
         */
        FPS_Window myFPSWindow = new FPS_Window(gameWidth, gameHeight);
    }
}
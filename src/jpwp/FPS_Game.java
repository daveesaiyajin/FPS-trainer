/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

public class FPS_Game {

    public static void main(String[] args) {
        System.out.println("Test czy działa wszystko ");

        int gameWidth = 1600;
        int gameHeight = 1024;

        FPS_Window myFPSWindow = new FPS_Window(gameWidth, gameHeight);
    }
}
/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

/* 
 * This is defitnition of the main window of FPS Trainer game 
 */
public class FPS_Window extends JFrame {

    /*
     * This is main constructor, which setup all the game parameters
     * 
     * @param width - set width of the window
     * 
     * @param height - set height of the window
     */

    private FPS_LayoutPanel fpsLayoutPanel; // definition of the layout panel

    public FPS_Window(int width, int height) {
        super();
        setTitle("FPS Trainer game"); // set the title of the game
        setSize(width, height); // set the size of the window
        setResizable(false); // set parameter that allows to change the size of the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set the default window operations
        // setUndecorated(true); - parametr ukrywający ramkę okna
        setLocationRelativeTo(rootPane); // set the location of the window in the center
        initGUI(width, height); // this is a method that initialize the interface
        setVisible(true); // set the visibility of the window
        animationLoop(); // start the game loop
    }

    /*
     * Initialize the user interface
     * 
     * @param width - set width
     * 
     * @param height - set height
     */

    private void initGUI(int width, int height) {
        setLayout(new GridLayout(1, 1));
        // This methods sets the initial resources
        FPS_GPars.loadInitialImages();
        Toolkit tk = Toolkit.getDefaultToolkit();

        int CursorWidth = FPS_GPars.cursorImage.getWidth(null);
        int CursorHeight = FPS_GPars.cursorImage.getHeight(null);

        /* Set the cursor middle point */
        Point cursorPoint = new Point(CursorWidth / 2, CursorHeight / 2);

        // Set the courses shape
        Cursor tCursor = tk.createCustomCursor(FPS_GPars.cursorImage, cursorPoint, "Target Cursor");
        setCursor(tCursor);

        fpsLayoutPanel = new FPS_LayoutPanel(width, height); // declaration of layout panel

        add(fpsLayoutPanel);
    }

    /**
     * Main loop of the game
     */
    private void animationLoop() {
        // Set the starting time of the game by getting the current system timestamp
        FPS_GPars.startTime = System.currentTimeMillis();
        long currTime = FPS_GPars.startTime;

        while (currTime - FPS_GPars.startTime < FPS_GPars.GAME_TIME) {
            long elapsedTime = System.currentTimeMillis() - currTime;
            // check current time - will be used to check if the level time was exceeded
            currTime += elapsedTime;

            long levelTime = System.currentTimeMillis() - FPS_GPars.startTime;

            System.out.println("Aktualny czas gry: " + levelTime);

            if (currTime - System.currentTimeMillis() >= FPS_GPars.LEVEL_TIME) {
                System.out.println("Time's up! " + levelTime);
            }
            // draw position of next objects
            repaint();

            // sleep time
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Wyjątek: " + ex);
            }
        } // end of while condition
    }// end of animationLoop() method

}

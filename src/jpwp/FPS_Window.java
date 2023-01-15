/**
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

/**
 * This is defitnition of the main window of FPS Trainer game
 */
public class FPS_Window extends JFrame {

    /** definition of the layout panel */
    private FPS_LayoutPanel fpsLayoutPanel;

    /**
     * This is main constructor, which setup all the game parameters
     * 
     * @param width  - set width of the window
     * 
     * @param height - set height of the window
     */
    public FPS_Window(int width, int height) {
        super();
        /** set the title of the game */
        setTitle("FPS Trainer game");
        /** set the size of the window */
        setSize(width, height);
        /** set parameter that allows to change the size of the window */
        setResizable(false);
        /** set the default window operations */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        /** hide the frame */
        setUndecorated(true);
        /** set the location of the window in the center */
        setLocationRelativeTo(rootPane);
        /** this is a method that initialize the interface */
        initGUI(width, height);
        /** set the visibility of the window */
        setVisible(true);
        /** start the game loop */
        animationLoop();
    } // end of FPS_Window

    /**
     * Initialize the user interface
     * 
     * @param width  - set width
     * 
     * @param height - set height
     */
    private void initGUI(int width, int height) {
        /** set the layout grid */
        setLayout(new GridLayout(1, 1));
        /** Sets the initial resources */
        FPS_GPars.loadInitialImages();
        Toolkit tk = Toolkit.getDefaultToolkit();

        /** get the image width */
        int CursorWidth = FPS_GPars.cursorImage.getWidth(null);
        /** get the image height */
        int CursorHeight = FPS_GPars.cursorImage.getHeight(null);

        /** Set the cursor middle point */
        Point cursorPoint = new Point(CursorWidth / 2, CursorHeight / 2);

        /** Set the courses shape */
        Cursor tCursor = tk.createCustomCursor(FPS_GPars.cursorImage, cursorPoint, "Target Cursor");
        setCursor(tCursor);

        /** declaration of layout panel */
        fpsLayoutPanel = new FPS_LayoutPanel(width, height);

        add(fpsLayoutPanel);
    } // end of initGUI()

    /**
     * Main loop of the game
     */
    private void animationLoop() {
        /** Set the starting time of the game by getting the current system timestamp */
        FPS_GPars.startTime = System.currentTimeMillis();
        /** assign variable */
        long currTime = FPS_GPars.startTime;

        while (currTime - FPS_GPars.startTime < FPS_GPars.GAME_TIME) {
            long elapsedTime = System.currentTimeMillis() - currTime; // check the diff between each tick
            currTime += elapsedTime; // counting current time

            long levelTime = System.currentTimeMillis() - FPS_GPars.startTime;

            System.out.println("Game time : " + levelTime);

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

} // end of FPS_Window class

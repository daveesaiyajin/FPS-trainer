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

    public FPS_MenuBar fpsMenuBar;

    public FPS_Window(int width, int height) {
        super();
        fpsMenuBar = new FPS_MenuBar();
        setJMenuBar(fpsMenuBar);
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
        setLayout(new GridLayout(1, 1)); // set the layout grid
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
        // set the game panel
        add(new FPS_Panel(width, height));
    }

    /**
     * Główna pętla gry - takt animacji (w procesie dalszej edukacji
     * można używać wątków czy klasy Timer)
     */
    private void animationLoop() {
        // Set the starting time of the game by getting the current system timestamp
        FPS_GPars.startTime = System.currentTimeMillis();
        long currTime = FPS_GPars.startTime;

        while (currTime - FPS_GPars.startTime < FPS_GPars.GAME_TIME) {
            long elapsedTime = System.currentTimeMillis() - currTime;
            // licz czas gry - może się przydać w ograniczeniach czasowych
            // w tej demonstracji nie wykorzystane
            currTime += elapsedTime;

            // odrysuj kolejny ekran gry (nowe pozycje obiektów - symulacja ruchu)
            repaint();

            // przerwa w czasie
            try {
                Thread.sleep(80);
            } catch (InterruptedException ex) {
                System.out.println("Wyjątek: " + ex);
            }
        } // koniec while
    }// koniec animationLoop()

}

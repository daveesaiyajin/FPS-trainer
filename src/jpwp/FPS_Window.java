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

public class FPS_Window extends JFrame {

    public FPS_Window(int width, int height) {
        setTitle("FPS Trainer game");
        setSize(width, height);
        setResizable(true);
        // setUndecorated(true); - parametr ukrywający ramkę okna
        setLocationRelativeTo(rootPane);
        setVisible(true);
        initGUI(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initGUI(int width, int height) {
        setLayout(new GridLayout(1, 1));
        FPS_GPars.loadInitialImages();
        Toolkit tk = Toolkit.getDefaultToolkit();

        Cursor tCursor = tk.createCustomCursor(FPS_GPars.cursorImage, new Point(10, 10), "Target Cursor");
        setCursor(tCursor);
        add(new FPS_Panel(width, height));
    }

}

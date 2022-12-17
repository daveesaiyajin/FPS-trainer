/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class FPS_Window extends JFrame {

    public FPS_Window(int width, int height) {
        setTitle("FPS Trainer game");
        setSize(width, height);
        setResizable(true);
        setLocationRelativeTo(rootPane);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}

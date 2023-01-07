/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */

package jpwp;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FPS_MenuBar extends JMenuBar {

    public FPS_MenuBar() {
        JMenu menuFile = new JMenu("File");
        add(menuFile);
        JMenuItem exitItem = new JMenuItem("Exit");
        menuFile.add(exitItem);

        JMenu menuAbout = new JMenu("About");
        add(menuAbout);
        JMenuItem aboutItem = new JMenuItem("About");
        menuAbout.add(aboutItem);
    }

}

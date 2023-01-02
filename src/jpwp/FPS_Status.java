/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

public class FPS_Status {

    public int level;
    public double time;

    public void reset() {
        level = 0;
        time = 0.0;
    }

    public void nextLevel() {
        level++;
    }

}

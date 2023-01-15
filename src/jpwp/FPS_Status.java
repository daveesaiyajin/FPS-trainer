/**
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

/**
 * FPS_Status class definition that contains the basic details about the level
 */
public class FPS_Status {

    /** define number of objects that was hit */
    public int ObjectsCounter;
    /** define current level of the game */
    public int level;
    /** define time of current level */
    public double time;

    /**
     * Reset status of the game to the beginning
     */
    public void reset() {
        ObjectsCounter = 0;
        level = 1;
        time = 0.0;
    } // end of reset() method

    /**
     * Reset counter of hitted objects on particular level
     */
    public void resetObjectsCounter() {
        ObjectsCounter = 0;
    } // end of resetObjectsCounter() method

    /**
     * Increment the level counter
     */
    public void nextLevel() {
        level++;
    } // end of nextLevel() method

}

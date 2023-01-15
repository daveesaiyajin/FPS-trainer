/*
 * Created by Dawid Zieliński
 * Elektronika i telekomunikacja sem. 7 
 */
package jpwp;

public class FPS_Status {

    public int ObjectsCounter; // define number of objects that was hit
    public int level; // define current level of the game
    public double time; // define time of current level

    /*
     * Reset status of the game to the beginning
     */

    public void reset() {
        ObjectsCounter = 0;
        level = 1;
        time = 0.0;
    } // end of reset() method

    /*
     * Reset counter of hitted objects on particular level
     */

    public void resetObjectsCounter() {
        ObjectsCounter = 0;
    } // end of resetObjectsCounter() method

    /*
     * Increment the level counter
     */
    public void nextLevel() {
        level++;
    } // end of nextLevel() method

}

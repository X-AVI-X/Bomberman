package demolition;
import processing.core.PApplet;
/** Abstract class Enemy */
public abstract class Enemy extends Player{

    /**
     * The Enemy constructor takes mapObject, appObject, and initial position of the Enemy
     * @param mapObject
     * @param appObject
     * @param x
     * @param y
     */
    public Enemy(Map mapObject, PApplet appObject, int x, int y){
        super(mapObject,appObject, x, y);
    }
    /** abstract method step that needs to be implemented by the enemies */
    public abstract void step();
}
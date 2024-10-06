package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;

public class RedEnemy extends Enemy{
    /** The PApplet object */
    PApplet appObject;
    /** ArrayList that contains the sprites for the left movement */
    ArrayList<PImage> moveLeftSprites = new ArrayList<>();
    /** ArrayList that contains the sprites for the right movement */
    ArrayList<PImage> moveRightSprites = new ArrayList<>();
    /** ArrayList that contains the sprites for the vertical up direction */
    ArrayList<PImage> moveUpSprites = new ArrayList<>();
    /** ArrayList that contains the sprites for the vertical down direction */
    ArrayList<PImage> moveDownSprites = new ArrayList<>();
    /**
     * Direction counter that is set to 4
     * It will generate a random number between 0 to 4
     */
    private int DIRECTIONCOUNTER = 4;

    /**A random object which is needed to generate a random direction */
    Random r = new Random();
      /**
     * Constructor for a Red Enemy requires a Map Object,
     * App Object of PApplet type, and it's initial coordinates (x and y)
     * @param mapObject
     * @param appObject
     * @param x
     * @param y
     */

    public RedEnemy(Map mapObject, PApplet appObject, int x, int y) {
        super(mapObject, appObject, x, y);
        this.appObject = appObject;
        // loadRedEnemyImages();
        super.moveDownSprites = this.moveDownSprites;
        super.moveUpSprites = this.moveUpSprites;
        super.moveRightSprites=this.moveRightSprites;
        super.moveLeftSprites = this.moveLeftSprites;
    }

    /**
     * A method that loads all the Red Enemy moving sprites
     */
    public void loadRedEnemyImages() {
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_left1.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_left2.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_left3.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_left4.png"));

        moveRightSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_right1.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_right2.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_right3.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_right4.png"));

        moveUpSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_up1.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_up2.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_up3.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_up4.png"));

        moveDownSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_down1.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_down2.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_down3.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/red_enemy/red_down4.png"));

    }
    /** Generates a free random direction when a Red Enemy is faced with walls 
     * It takes the previous direction the Red Enemy was moving in.
     * @param pasDirection
     * @return newDirection
    */
    public String generateRandomDirection(String pastDirection) {

        while (true){
            String newDirection = directions[r.nextInt(DIRECTIONCOUNTER)];
            if (!collidesWithWall(newDirection)){
                return newDirection;
            }
        }
    }
    /**A method that will keep the Red Enemy moving in a random direction */

    public void step (){
        if (!this.collidesWithWall("Left") && this.direction.equals("Left")) {
            this.sub_x_direction();
        }
        else if (!this.collidesWithWall("Right") && this.direction.equals("Right")) {
            this.add_x_direction();
        }
        else if (!this.collidesWithWall("Down") && this.direction.equals("Down")) {
            this.add_y_direction();
        }
        else if (!this.collidesWithWall("Up") && this.direction.equals("Up")) {
            this.sub_y_direction();
        }else {
            this.direction = this.generateRandomDirection(this.direction);
            this.move(this.direction);
        }
    }

  

}   
package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class YellowEnemy extends Enemy{
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
     * Constructor for a Yellow Enemy requires a Map Object,
     * App Object of PApplet type, and it's initial coordinates (x and y)
     * @param mapObject
     * @param appObject
     * @param x
     * @param y
     */
    public YellowEnemy(Map mapObject, PApplet appObject, int x, int y) {
        super(mapObject,appObject, x, y);
        this.appObject = appObject;
        // loadYellowEnemyImages();
        super.moveDownSprites = this.moveDownSprites;
        super.moveUpSprites = this.moveUpSprites;
        super.moveRightSprites=this.moveRightSprites;
        super.moveLeftSprites = this.moveLeftSprites;
    }

    /**
     * A method to load the sprites of the Yellow Enemy
     */
    public void loadYellowEnemyImages() {

        moveLeftSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_left1.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_left2.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_left3.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_left4.png"));

        moveRightSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_right1.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_right2.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_right3.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_right4.png"));

        moveUpSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_up1.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_up2.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_up3.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_up4.png"));

        moveDownSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_down1.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_down2.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_down3.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/yellow_enemy/yellow_down4.png"));

    }
    /**
     * A method to move the Yellow Enemy in clockwise direction
     */
    public void step() {
        if (!this.collidesWithWall("Left") && this.direction.equals("Left")) {
            this.sub_x_direction();
        } else if (!this.collidesWithWall("Right") && this.direction.equals("Right")) {
            this.add_x_direction();
        } else if (!this.collidesWithWall("Down") && this.direction.equals("Down")) {
            this.add_y_direction();
        } else if (!this.collidesWithWall("Up") && this.direction.equals("Up")) {
            this.sub_y_direction();
        } else {
            this.direction = this.clockwiseDirection(this.direction);
            this.move(this.direction);
        }
    }
    /**
     * Given the previous direction of the Yellow Enemy,
     * a new clockwise direction will be returned 
     * which is free from any hindrances
     * @param direction
     * @return newDirection
     */
    public String clockwiseDirection(String direction) {
        int i=0;
        String newDirection;

            for (int c=0;c<4;c++)
            {
                if (direction.equals(directions[c]))
                {
                    i=c;
                }
            }

        while (true)
        {
            newDirection = directions[(i+1)%4];
            i++;

            if (!collidesWithWall(newDirection))
            {
                return newDirection;
            }
        }
    }
    
}
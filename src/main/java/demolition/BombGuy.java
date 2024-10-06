package demolition;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class BombGuy extends Player {
    /** PApplet object */
    public PApplet appObject;
    /** Number of like Bomb Guy has*/
    public static int life;
      /** ArrayList that contains the sprites for the left movement */
    public ArrayList<PImage> moveLeftSprites = new ArrayList<>();
      /** ArrayList that contains the sprites for the right movement */
    public ArrayList<PImage> moveRightSprites= new ArrayList<>();
    /** ArrayList that contains the sprites for the vertical up direction */
    public ArrayList<PImage> moveUpSprites = new ArrayList<>();
    /** ArrayList that contains the sprites for the vertical down direction */
    public ArrayList<PImage> moveDownSprites = new ArrayList<>();
    /** Map object */
    public Map myMap;
    /** Arraylist that contains the config file details */
    public ArrayList<String> configDetails; 
 
   /**
    * Constructor for the BombGuy requires map object, app onject and the coordinates of Bomb Guy
    * @param mapObject
    * @param appObject
    * @param x
    * @param y
    */
    public BombGuy(Map mapObject, PApplet appObject, int x, int y) {
        super(mapObject, appObject, x, y);
        this.appObject = appObject;
        myMap = mapObject;
      
        setLives();

        super.moveDownSprites = this.moveDownSprites;
        super.moveUpSprites = this.moveUpSprites;
        super.moveRightSprites=this.moveRightSprites;
        super.moveLeftSprites = this.moveLeftSprites;

    }
    /**
     * Method that reads the config file and sets the number of lives BombGuy has
     */
    public void setLives(){
       configDetails = myMap.readConfigFile(); 
       life = Integer.parseInt(configDetails.get(0));
    }

    /**Method that loads the BombGuy images */
    public void loadBombGuyImages(){
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/player/player_left1.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/player/player_left2.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/player/player_left3.png"));
        moveLeftSprites.add(appObject.loadImage("/src/main/resources/player/player_left4.png"));

        moveRightSprites.add(appObject.loadImage("/src/main/resources/player/player_right1.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/player/player_right2.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/player/player_right3.png"));
        moveRightSprites.add(appObject.loadImage("/src/main/resources/player/player_right4.png"));

        moveUpSprites.add(appObject.loadImage("/src/main/resources/player/player_up1.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/player/player_up2.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/player/player_up3.png"));
        moveUpSprites.add(appObject.loadImage("/src/main/resources/player/player_up4.png"));

        moveDownSprites.add(appObject.loadImage("/src/main/resources/player/player1.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/player/player2.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/player/player3.png"));
        moveDownSprites.add(appObject.loadImage("/src/main/resources/player/player4.png"));

    }

    /**
     * Checks if the BombGuy crosses the path with any type of Enemy
     * If he does, we return true. If not, we return false
     * @return true or false
     */
    public boolean bombGuyMeetsEnemies(){
        for(RedEnemy r : myMap.getRedEnemyList()){
            if (r.getX_coord() == this.getX_coord() && r.getY_coord() == this.getY_coord()){
                return true;
            }
        }for(YellowEnemy y : myMap.getYellowEnemyList()){
            if (y.getX_coord() == this.getX_coord() && y.getY_coord() == this.getY_coord()){
                return true;
            }
        }return false;
    }
   
}


  
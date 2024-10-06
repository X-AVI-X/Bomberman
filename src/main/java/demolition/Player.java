package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Player {
    private int x_coord;
    private int y_coord;
    /** The PApplet object */
    PApplet appObject;
    /** Direction of the player. dafault set to Down */
    String direction;
    /** Map object */
    Map myMap;
    /** ArrayList that contains the sprites for the left movement */
    ArrayList<PImage> moveLeftSprites = new ArrayList<>();
    /** ArrayList that contains the sprites for the right movement */
    ArrayList<PImage> moveRightSprites= new ArrayList<>();
    /** ArrayList that contains the sprites for the vertical up direction */
    ArrayList<PImage> moveUpSprites = new ArrayList<>();
    /** ArrayList that contains the sprites for the vertical down direction */
    ArrayList<PImage> moveDownSprites = new ArrayList<>();
    /**Arraylist declared to contain the directions of a player */
    protected String[] directions = {"Up", "Right", "Down", "Left"};


  /**
     * Constructor for a Player requires a Map Object,
     * App Object of PApplet type, and it's initial coordinates (x and y)
     * @param mapObject
     * @param appObject
     * @param x
     * @param y
     */
    public Player(Map mapObject, PApplet appObject, int x, int y){
        myMap = mapObject;
        this.appObject=appObject;
        this.x_coord = x;
        this.y_coord = y;
        this.direction = "Down";

    }
     /**
     *Adds to the current player's x-coordinate to move it to the right
     */
    public void add_x_direction(){   
        this.x_coord += 32;
    }
     /**
     * Adds to the current player's y-coordinate to move it down
     */
    public void add_y_direction(){
        this.y_coord  += 32;
    }
     /**
     * Subtracts from the current player's y-coordinate to move it up
     */
    public void sub_y_direction(){
        this.y_coord -= 32;
    }
    /**
     * Subtracts from the current player's x-coordinate to move it to the left
     */
    public void sub_x_direction(){
        this.x_coord-=32;
    }

// string direction
// right x-32
// left +32
// up y+
// down y-

    /**Returns true if the player is blocked by walls (solid or broken) 
     * Returns false if the player's path is free
     * @param direction
     * @return true or false
     */
    public boolean collidesWithWall(String direction){  // check if they have collided with wall
        if (direction.equals("Left")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i)+32 == this.x_coord && myMap.solidWallCoords.get(i+1) == this.y_coord){
                    return true;
                }
            }
            for(int i = 0 ; i < myMap.brokenWallCoords.size(); i = i+2){
                if (myMap.brokenWallCoords.get(i)+32 == this.x_coord && myMap.brokenWallCoords.get(i+1) == this.y_coord){
                    return true;
                }
            }
        }
        else if (direction.equals("Right")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i)-32 == this.x_coord && myMap.solidWallCoords.get(i+1) == this.y_coord){
                    return true;
                }
            }

            for(int i = 0 ; i < myMap.brokenWallCoords.size(); i = i+2){
                if (myMap.brokenWallCoords.get(i)-32 == this.x_coord && myMap.brokenWallCoords.get(i+1)== this.y_coord){
                    return true;
                }
            }
        }
        else if (direction.equals("Up")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) == this.x_coord && myMap.solidWallCoords.get(i+1)+32 == this.y_coord){
                    return true;
                }
            }

            for(int i = 0 ; i < myMap.brokenWallCoords.size(); i = i+2){
                if (myMap.brokenWallCoords.get(i) == this.x_coord && myMap.brokenWallCoords.get(i+1)+32 == this.y_coord){
                    return true;
                }
            }
        }

        else if (direction.equals("Down")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) == this.x_coord && myMap.solidWallCoords.get(i+1) -32 == this.y_coord){
                    return true;
                }
            }

            for(int i = 0 ; i < myMap.brokenWallCoords.size(); i = i+2){
                if (myMap.brokenWallCoords.get(i) == this.x_coord && myMap.brokenWallCoords.get(i+1)-32 == this.y_coord){
                    return true;
                }
            }
        }
        
        return false;
    }

    

    // move method for both enemies ........................................
    /**
     * Taking the random direction a player is moving in
     * it moves the player in that specified direction
     * @param randomDirection
     */
    public void move(String randomDirection) {
        if(!collidesWithWall(randomDirection)){
            if (randomDirection.equals("Left")) {
                direction = "Left";
                this.sub_x_direction();
            } else if (randomDirection.equals("Right")) {
                direction = "Right";
                this.add_x_direction();
            } else if (randomDirection.equals("Down")) {
                direction = "Down";
                this.add_y_direction();
            } else if (randomDirection.equals("Up")) {
                direction = "Up";
                this.sub_y_direction();
            }
        }
      
    }
    /**
     * Draws the player in the direction they are moving
     * A new sprite is loaded after a number of frames to produce the animation 
     */
    public void render() {
     
        int x=getX_coord();
        int y=getY_coord()-32; 
        switch (this.direction) {
            case "Down":
                if (App.count == 48) {
                    App.count = 0;
                }
                if (App.count >= 0 && App.count <= 12) {
                    appObject.image(moveDownSprites.get(0), x, y);
                } else if (App.count >= 12 && App.count <= 24) {
                    appObject.image(moveDownSprites.get(1), x, y);
                } else if (App.count >= 24 && App.count <= 36) {
                    appObject.image(moveDownSprites.get(2), x, y);
                } else if (App.count >= 36 && App.count <= 48) {
                    appObject.image(moveDownSprites.get(3), x, y);
                }
                break;
            case "Left":
                if (App.count == 48) {
                    App.count = 0;
                }
                if (App.count >= 0 && App.count <= 12) {
                    appObject.image(moveLeftSprites.get(0), x, y);
                } else if (App.count >= 12 && App.count <= 24) {
                    appObject.image(moveLeftSprites.get(1), x, y);
                } else if (App.count >= 24 && App.count <= 36) {
                    appObject.image(moveLeftSprites.get(2), x, y);
                } else if (App.count >= 36 && App.count <= 48) {
                    appObject.image(moveLeftSprites.get(3), x, y);
                }
                break;
            case "Right":
                if (App.count == 48) {
                    App.count = 0;
                }
                if (App.count >= 0 && App.count <= 12) {
                    appObject.image(moveRightSprites.get(0), x, y);
                } else if (App.count >= 12 && App.count <= 24) {
                    appObject.image(moveRightSprites.get(1), x, y);
                } else if (App.count >= 24 && App.count <= 36) {
                    appObject.image(moveRightSprites.get(2), x, y);
                } else if (App.count >= 36 && App.count <= 48) {
                    appObject.image(moveRightSprites.get(3), x, y);
                }
                break;
            case "Up":
                if (App.count == 48) {
                    App.count = 0;
                }
                if (App.count >= 0 && App.count <= 12) {
                    appObject.image(moveUpSprites.get(0), x, y);
                } else if (App.count >= 12 && App.count <= 24) {
                    appObject.image(moveUpSprites.get(1), x, y);
                } else if (App.count >= 24 && App.count <= 36) {
                    appObject.image(moveUpSprites.get(2), x, y);
                } else if (App.count >= 36 && App.count <= 48) {
                    appObject.image(moveUpSprites.get(3), x, y);
                }
                break;
        }

    }
    /**
     * Returns x-coordinate of the player 
     * @return x_coord
     */
    public int getX_coord(){
        return this.x_coord;
    }
    /**
     * Returns the y-coordinate of the player
     * @return y_coord
     */
    public int getY_coord(){
        return this.y_coord;
    }
    /**
     * Sets the direction of the player
     * @param direction
     */
    public void setDirection(String direction){
        this.direction = direction;
    }
    /** Returns the direction of the player
     * @return direction */
    public String getDirection(){
       return this.direction;
    }



    public void setX_coord(int x){
        this.x_coord = x;
    }

    public void setY_coord(int y){
        this.y_coord = y;
    }

  
}
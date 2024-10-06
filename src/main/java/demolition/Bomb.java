package demolition;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.Iterator;

public class Bomb {
    /** Map object */
    public Map myMap;
    /** App object */
    public PApplet appObject;
    /** x-coordinate of the bomb */
    private int bombX;
    /** y-coordinate of the bomb */
    private int bombY;
    /** The relative path to bomb images */
    public String bombPath;
    /** The relative path for the explosion images */
    public String explosionPath;
    /** Bomb frame count */
    public int bombFrameCount;
    /** BombGuy object */
    public BombGuy bomberman;
    /** ArrayList containing the bombImgaes */
    public ArrayList<PImage> bombImages = new ArrayList<>();
    /** ArrayList containing explosionImages */
    public ArrayList<PImage> explosionImages = new ArrayList<>();
    /** Boolean to check if bombGuy is caught in bomb */
    public boolean isBombGuyInBomb;
    /** A counter for rendering when a bombGuy is caught in explosion*/
    public int callBombGuyFunc = 0; 
    /** App object */
    public App app; 

    /**
     * The Bomb constructor takes the map object, app object (PApplet type), bombGuy, and an instance of the app
     * @param mapObj
     * @param appObj
     * @param bomberman
     * @param app
     */
    public Bomb(Map mapObj, PApplet appObj, BombGuy bomberman, App app){
        myMap = mapObj;
        appObject =  appObj;
        this.bomberman = bomberman;
        this.bombFrameCount = 0;
        this.app = app;
      
            
    }
    /**
     * Takes the x-coordinate of the bomb and sets it
     * @param x
     */
    public void setBombX(int x){
        this.bombX = x;
        
    }
    /**
     * Takes the y-coordinate of the bomb and sets it
     * @param y
     */
    public void setBombY(int y){
        this.bombY = y;
        
    }
    /**
     * Returns the x coordinate of the bomb
     * @return this.bombX
     */
    public int getBombX(){
    return  this.bombX;
        
    }
    /**
     * Returns the y-coordinate of the bomb
     * @return bombY
     */
    public int getBombY(){
        return this.bombY; 
        
    }
    /**
     * Renders the bomb images in each frame
     */
    public void renderPlaceBomb(){  

        if(bombFrameCount <= 0 && bombFrameCount <= 15){
            appObject.image(bombImages.get(0), bombX, bombY);
        }
        else if(bombFrameCount <= 16 && bombFrameCount <= 30){
            appObject.image(bombImages.get(1), bombX, bombY);
        }
        else if(bombFrameCount <= 31 && bombFrameCount <= 45){
            appObject.image(bombImages.get(2), bombX, bombY);
        }
        else if(bombFrameCount <= 46 && bombFrameCount <= 60){
            appObject.image(bombImages.get(3), bombX, bombY);
        }
        else if(bombFrameCount <= 61 && bombFrameCount <= 75){
            appObject.image(bombImages.get(4), bombX, bombY);
        }
        else if(bombFrameCount <= 76 && bombFrameCount <= 90){
            appObject.image(bombImages.get(5), bombX, bombY);
        }
        else if(bombFrameCount <= 91 && bombFrameCount <= 105){
            appObject.image(bombImages.get(6), bombX, bombY);
        }
        else if(bombFrameCount <= 106 && bombFrameCount <= 120){
            appObject.image(bombImages.get(7), bombX, bombY);
        }
        else if(bombFrameCount >= 121 && bombFrameCount <= 150){
            renderExplosion();
            bombGuyCaughtInExplosion(bombX, bombY);

        }else if(bombFrameCount > 150){
            bombX = -1;
            bombY = -1;
        }
   
        bombFrameCount++;
    }

    /**
     * Loads the bomb images
     */
    public void loadBombImages(){
        bombPath = "/src/main/resources/bomb/";
        for(int i = 1; i <= 8 ; i++){
            bombImages.add(appObject.loadImage(bombPath + "bomb"+ i + ".png"));
        }

    }
    /**
     * Loads the explosion images
     */
    public void loadExplosionImages(){
        explosionPath = "/src/main/resources/explosion/";
        explosionImages.add(appObject.loadImage(explosionPath +"centre.png"));          // 0
        explosionImages.add(appObject.loadImage(explosionPath +"end_bottom.png"));         // 1
        explosionImages.add(appObject.loadImage(explosionPath +"end_top.png"));             // 2 
        explosionImages.add(appObject.loadImage(explosionPath +"end_left.png"));        // 3
        explosionImages.add(appObject.loadImage(explosionPath +"end_right.png"));       // 4
        explosionImages.add(appObject.loadImage(explosionPath +"horizontal.png"));      // 5
        explosionImages.add(appObject.loadImage(explosionPath +"vertical.png"));        // 6

    }
    /**
     * Method that renders the explosion and removes any player involved 
     */
    public void renderExplosion(){
       
        appObject.image(explosionImages.get(0), bombX, bombY);  // centre
        if(!explosionHinderedByWall("Right", bombX, bombY)){
            appObject.image(explosionImages.get(5), bombX + 32 , bombY); // horizontal right
            removeRedEnemy(bombX + 32 , bombY);
            removeYellowEnemy(bombX + 32 , bombY);
           
            removeBrokenWall(bombX + 32 , bombY);
            myMap.getEmptyWallCoords().add(bombX + 32);
            myMap.getEmptyWallCoords().add(bombY);

            
            if(!explosionHinderedByWall("Right End", bombX, bombY)){
                appObject.image(explosionImages.get(4), bombX + 64, bombY); // end right
                removeRedEnemy(bombX + 64, bombY);
                removeYellowEnemy(bombX + 64, bombY);
           
                removeBrokenWall(bombX + 64 , bombY);
                myMap.getEmptyWallCoords().add(bombX + 64);
                myMap.getEmptyWallCoords().add(bombY);
                
            }
        }
    
        if(!explosionHinderedByWall("Left", bombX, bombY)){
            appObject.image(explosionImages.get(5), bombX - 32, bombY); // hori.left
            removeRedEnemy(bombX - 32, bombY);
            removeYellowEnemy(bombX - 32, bombY);
        
            removeBrokenWall(bombX - 32 , bombY);
            myMap.getEmptyWallCoords().add(bombX -32);
            myMap.getEmptyWallCoords().add(bombY);
            if(!explosionHinderedByWall("Left End", bombX, bombY)){
                appObject.image(explosionImages.get(3), bombX - 64, bombY); // end left
                removeRedEnemy(bombX - 64, bombY);
                removeYellowEnemy(bombX - 64, bombY);
   
                removeBrokenWall(bombX -64 , bombY);
                myMap.getEmptyWallCoords().add(bombX - 64);
                myMap.getEmptyWallCoords().add(bombY);
            }
        }

        if(!explosionHinderedByWall("Vertical Top", bombX, bombY)){
            appObject.image(explosionImages.get(6), bombX, bombY - 32); // verti top
            removeRedEnemy(bombX, bombY - 32);
            removeYellowEnemy(bombX, bombY - 32);
       
            removeBrokenWall(bombX , bombY- 32);
            myMap.getEmptyWallCoords().add(bombX);
            myMap.getEmptyWallCoords().add(bombY- 32);
            if(!explosionHinderedByWall("End Top", bombX, bombY)){
                appObject.image(explosionImages.get(2), bombX, bombY - 64); // end top
                removeRedEnemy(bombX, bombY - 64);
                removeYellowEnemy(bombX, bombY - 64);
           
                removeBrokenWall(bombX , bombY- 64);
                myMap.getEmptyWallCoords().add(bombX);
                myMap.getEmptyWallCoords().add(bombY-64);
            }
        }
        if(!explosionHinderedByWall("Vertical Bottom", bombX, bombY)){
            appObject.image(explosionImages.get(6), bombX, bombY + 32); // verti bottom
            removeRedEnemy(bombX, bombY + 32);
            removeYellowEnemy(bombX, bombY + 32);
           
            removeBrokenWall(bombX , bombY + 32);
            myMap.getEmptyWallCoords().add(bombX);
            myMap.getEmptyWallCoords().add(bombY+32);
            if(!explosionHinderedByWall("End Bottom", bombX, bombY)){
                appObject.image(explosionImages.get(1), bombX, bombY + 64); // end bottom
                removeRedEnemy(bombX, bombY + 64);
                removeYellowEnemy(bombX, bombY + 64);
      
                removeBrokenWall(bombX, bombY + 64);
                myMap.getEmptyWallCoords().add(bombX);
                myMap.getEmptyWallCoords().add(bombY + 64);
            }
        }
         // when the enemy is in centre
        removeRedEnemy(bombX, bombY);
        removeYellowEnemy(bombX, bombY);
   
    }
    /**
     * If a bomb is placed near a broken wall, the broken wall will break
     * It takes the position where the bomb can have an effect
     * @param x
     * @param y
     */
    public void removeBrokenWall(int x, int y){
        for(int i = 0; i < myMap.brokenWallCoords.size(); i = i+2){
        if(myMap.brokenWallCoords.get(i) == x && myMap.brokenWallCoords.get(i+1) == y){
            myMap.brokenWallCoords.remove(i);
            myMap.brokenWallCoords.remove(i);
            break;
        }
        }
    }
    /**
     * Checks if the area surrounding the bomb has walls
     * If it does, the explosion will be limited
     * @param direction
     * @param x
     * @param y
     * @return true or false
     */
    public boolean explosionHinderedByWall(String direction, int x, int y){
        if(direction.equals("Right")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) - 32 == getBombX() && myMap.solidWallCoords.get(i+1) == getBombY()){
                return true;
                }
            }
        }
        
        if(direction.equals("Left")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) + 32  == getBombX() && myMap.solidWallCoords.get(i+1) == getBombY()){
                    return true;
                }
            }
        }

        if(direction.equals("Right End")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) - 64 == getBombX() && myMap.solidWallCoords.get(i+1) == getBombY()){
                    return true;
                }
            }
        }

        if(direction.equals("Left End")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) + 64 == getBombX()&& myMap.solidWallCoords.get(i+1) == getBombY()){
                    return true;
                }
            }
        }

        if(direction.equals("Vertical Top")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) == getBombX()&& myMap.solidWallCoords.get(i+1) + 32 == getBombY()){
                    return true;
                }
            }
        }

        if(direction.equals("Vertical Bottom")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) == getBombX()&& myMap.solidWallCoords.get(i+1) - 32 == getBombY()){
                    return true;
                }
            }
        }
        if(direction.equals("End Top")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) == getBombX() && myMap.solidWallCoords.get(i+1) + 64 == getBombY()){
                    return true;
                }
            }
        }
        if(direction.equals("End Bottom")){
            for(int i = 0 ; i < myMap.solidWallCoords.size(); i = i+2){
                if (myMap.solidWallCoords.get(i) == getBombX()&& myMap.solidWallCoords.get(i+1) - 64 == getBombY()){
                    return true;
                }
            }
        }
            return false;
    }
    /**
     * Takes the bomb's position and if it matches with the Red Enemy's position, enemy is removed
     * @param x
     * @param y
     */
    public void removeRedEnemy(int x, int y){
        Iterator<RedEnemy> itrRed = myMap.getRedEnemyList().iterator();
        while (itrRed.hasNext()) {
            RedEnemy enemyRed = itrRed.next();
            if (enemyRed.getX_coord() == x && enemyRed.getY_coord() == y) {
            itrRed.remove();
            }
        }
    }
    /**
     * Takes the Bomb position and if it matches with Yellow Enemy's position, enemy is removed
     * @param x
     * @param y
     */
    public void removeYellowEnemy(int x, int y){
        Iterator<YellowEnemy> itrYellow = myMap.getYellowEnemyList().iterator();
        while (itrYellow.hasNext()) {
            YellowEnemy enemyYellow = itrYellow.next();
            if (enemyYellow.getX_coord() == x && enemyYellow.getY_coord() == y) {
            itrYellow.remove();
            }
        }
    }
    /**
     * Takes the position of the bomb and returns if the coordinate matches with BombGuy's position
     * @param x
     * @param y
     * @return
     */
    public boolean bombGuyCaughtInExplosion(int x, int y){
        if (bomberman.getX_coord() == x && bomberman.getY_coord() == y){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
         
        else if (x + 32 == bomberman.getX_coord() && y == bomberman.getY_coord()){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
        else if (x - 32 == bomberman.getX_coord() && y == bomberman.getY_coord()){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
        else if (x + 64 == bomberman.getX_coord() && y == bomberman.getY_coord()){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
        else if (x - 64 == bomberman.getX_coord() && y == bomberman.getY_coord()){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
        else if (x == bomberman.getX_coord() && y + 32 == bomberman.getY_coord()){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
        else if (x == bomberman.getX_coord() && y - 32 == bomberman.getY_coord()){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
        else if (x == bomberman.getX_coord() && y+ 64 == bomberman.getY_coord()){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
        else if (x == bomberman.getX_coord() && y - 64 == bomberman.getY_coord()){
            if (callBombGuyFunc == 0)
            app.levelReset();
            callBombGuyFunc++;
            return true;
        }
        
        return false;
        
    }


}



package demolition;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;
import java.util.ArrayList;


public class App extends PApplet {
    /** ArrayList for holding the bomb objects */
    ArrayList<Bomb> bombList;
    /** BombGuy object */
    BombGuy bombGuy;

    /** boolean value to check if the key pressed by the user is released */
    boolean is_keyReleased = true;
    /** Count for the number of times enemy frame is called */
    public static int count = 0;
    /** Number of times enemies are going to be rendered */
    public static int enemyFrame = 0;
    /** Font to be displayerd on the screen */
    PFont font;
    /** Counter for level */
    public int levelCount;
    /** Boolean if game has been won */
    public boolean gameWon = false;
    /** Number of lives Bomb Guy has */
    public static int bombGuyLife;
    /** Screen width */
    public static final int WIDTH = 480;
    /** Screen height */
    public static final int HEIGHT = 480;
    /** Frame Rate per Second */
    public static final int FPS = 60;
    /** Timer */
    public static int time;
    /** ArrayList containing maps */
    public ArrayList<Map> mapList;
    /**
     * For setting the screen size
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }
    /**
     * Setup method for creating the objects
     */
    public void setup() {
        frameRate(FPS);
        Map.configFile = "config.json";
        mapList = new ArrayList<>();
        mapList.add(new Map(this));
        mapList.get(0).loadMapDrawing(mapList.get(0).mapLevels.get(Map.currentLevel));
        // mapList.get(Map.currentLevel).setConfigFile("config.json");   // can be used to set the config file to read from
       
        bombList = new ArrayList<>();
        bombGuy = new BombGuy(mapList.get(Map.currentLevel), this, mapList.get(Map.currentLevel).getBombGuyPositionX(), mapList.get(Map.currentLevel).getBombGuyPositionY()); 
        bombGuyLife = BombGuy.life;
        bombGuy.loadBombGuyImages();
        bombList.add(new Bomb(mapList.get(Map.currentLevel), this, bombGuy, this));
        bombList.get(0).loadBombImages();
        bombList.get(0).loadExplosionImages(); 
        font = createFont("PressStart2P-Regular.ttf",10);
        time= mapList.get(Map.currentLevel).timer;
      
    }
    /**
     * Draws the map and renders the players
     */
    public void draw() {
        background(239, 129, 0);

        if (mapList.get(Map.currentLevel).isLevelPassed(bombGuy.getX_coord(), bombGuy.getY_coord())){
            Map.currentLevel++;
            try{
                mapList.add(new Map(this));
                mapList.get(mapList.size()-1).loadMapDrawing(mapList.get(mapList.size()-1).mapLevels.get(Map.currentLevel));
                bombGuy = new BombGuy(mapList.get(Map.currentLevel), this, mapList.get(Map.currentLevel).getBombGuyPositionX(), mapList.get(Map.currentLevel).getBombGuyPositionY());
                bombGuy.loadBombGuyImages(); 
            }catch (Exception e){
               gameWon = true;
               Map.currentLevel --;
            }
        }  
        if (!mapList.get(Map.currentLevel).isTimeFinished() && !gameWon && bombGuyLife > 0) {
            if (mapList.get(Map.currentLevel).isMapValid()) {
                image(mapList.get(Map.currentLevel).getIconPlayer(), 128, 16);
                image(mapList.get(Map.currentLevel).getIconTime(), 256, 16);
                fill(0);
                textFont(font,20);
                text(bombGuyLife,192,42);
                text(mapList.get(Map.currentLevel).timer,312,42);
                mapList.get(Map.currentLevel).renderWalls();
            }if (bombGuy.bombGuyMeetsEnemies()){
                levelReset();   
            }
            if (enemyFrame == 60) {
                enemyFrame = 0;
                mapList.get(Map.currentLevel).timer--;
            }enemyFrame++;

            if (enemyFrame == 60) { 
                for(RedEnemy r : mapList.get(Map.currentLevel).getRedEnemyList()){
                    r.step();
                }for(YellowEnemy y : mapList.get(Map.currentLevel).getYellowEnemyList()){
                    y.step();
                }
            }for(RedEnemy r: mapList.get(Map.currentLevel).getRedEnemyList()){
                if (r.getX_coord()!= 0)
                    r.render();
            }for(YellowEnemy y: mapList.get(Map.currentLevel).getYellowEnemyList()){
                if (y.getX_coord()!= 0)
                    y.render();
            }for (Bomb b : bombList) {
                if(b.getBombX() != 0)
                    b.renderPlaceBomb();
            }if (bombGuy.getX_coord() != 0) {
                    bombGuy.render();
            }count++;
  
    }else if (mapList.get(Map.currentLevel).isTimeFinished() || bombGuyLife <=0){
            fill(0);
            textFont(font, 20);
            text("Game Over", 152,240);
        }
        else if (gameWon){
            fill(0);
            textFont(font, 20);
            text("You Win", 165,240);
        }
    }

    /**
     * Resets the level when the Bomb Guy loses life
     */
    public void levelReset(){
        mapList.add(new Map(this));
        mapList.get(mapList.size()-1).loadMapDrawing(mapList.get(mapList.size()-1).mapLevels.get(Map.currentLevel));
        mapList.remove(0);
        bombList = new ArrayList<>();
        bombGuy = new BombGuy(mapList.get(Map.currentLevel), this, mapList.get(Map.currentLevel).getBombGuyPositionX(), mapList.get(Map.currentLevel).getBombGuyPositionY());
        bombGuy.loadBombGuyImages(); 
        bombList.add(new Bomb(mapList.get(Map.currentLevel), this, bombGuy, this));
        bombList.get(bombList.size()-1).loadBombImages();
        bombList.get(bombList.size()-1).loadExplosionImages(); 
        time= mapList.get(Map.currentLevel).timer;
        bombGuyLife --;
    }
    /**
     * Reads the key presses and performs necessary actions
     *  
     */
    public void keyPressed(KeyEvent event) {
        if (is_keyReleased && event.getKeyCode() == 37) { //arrow left 37
            bombGuy.move("Left");
                is_keyReleased = false;
        }else if (is_keyReleased && event.getKeyCode() == 38) {   // arrow up	38
            bombGuy.move("Up");
                is_keyReleased = false;      
        }else if (is_keyReleased && event.getKeyCode() == 39) { // arrow right	39
            bombGuy.move("Right");
                is_keyReleased = false;
        }else if (is_keyReleased && event.getKeyCode() == 40) { // arrow down	40
           bombGuy.move("Down");
                is_keyReleased = false;   
        }else if (event.getKeyCode() == 32) {
            is_keyReleased = false;
            bombList.add(new Bomb(mapList.get(Map.currentLevel), this, bombGuy, this));
            bombList.get(bombList.size()-1).loadBombImages();
            bombList.get(bombList.size()-1).loadExplosionImages();
            int i = bombList.size()-1;
            bombList.get(i).setBombX(bombGuy.getX_coord());
            bombList.get(i).setBombY(bombGuy.getY_coord());         
        } 
        return;
    }
    /**
     * Takes the key event and sets Key released to true
     */
    public void keyReleased(KeyEvent event) {
        is_keyReleased = true;
    }
    /**
     * Main method to run the Application
     * @param args
     */
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
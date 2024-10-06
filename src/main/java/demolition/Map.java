package demolition;

import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;



public class Map{
    /**Solid wall image */
   private PImage solidWall;
    /**Broken wall image */
   private PImage brokenWall;
    /**Empty wall image */
   private PImage emptyWall;
    /**Goal wall image */
   private PImage goalWall;
    /**Player icon image */
   private PImage iconPlayer;
    /**Time icon image */
   private PImage iconTime;
    /**PApplet app object */
    public PApplet appObject;
    // newly added
     /** Config File */
    public static String configFile= "config.json";
    /** Timer */
    public int timer;
    /**Bomb Guy x-coordinate */
    private int bombGuyPositionX;
     /**Bomb Guy y-coordinate*/
    private int bombGuyPositionY;
     /** Red Enemy x-coordinate*/
    private int redEnemyPositionX;
     /**Red enemy y-coordinate */
    private int redEnemyPositionY;
     /**Yellow enemy x-coordinate */
    private int yellowEnemyPositionX;
     /** Yello enemy y-coordinate*/
    private int yellowEnemyPositionY;
     /**Level Passed*/
    public boolean levelPassed;
    /** Current level */
    public static int currentLevel = 0;
     /** ArrayList of map levels */
    public ArrayList<String> mapLevels = new ArrayList<>();
     /**ArrayList of map level times */
    public ArrayList<Integer> mapLevelTime = new ArrayList<>();
     /**ArrayList of Solid Wall coordinates */
    public ArrayList<Integer> solidWallCoords = new ArrayList<>();
     /**ArrayList of Broken Wall coordinates*/
    public ArrayList<Integer> brokenWallCoords = new ArrayList<>();
    /**ArrayList of Empty Wall coordinates */
    private ArrayList<Integer> emptyWallCoords = new ArrayList<>(); 
     /** ArrayList of Goal Wall coordinates*/
    public ArrayList<Integer> goalWallCoord = new ArrayList<>();  
    /** ArrayList of RedEnemy objects */
    private ArrayList<RedEnemy> redEnemyList = new ArrayList<>();
    /** ArrayList of YellowEnemy objects */
    private ArrayList<YellowEnemy> yellowEnemyList = new ArrayList<>();

    private int playerCount = 0;

    /**
     * Constructor of the Map takes the PApplet object
     * @param appObject
     */
    public Map(PApplet appObject){
        this.appObject = appObject;
        this.levelPassed = false;
        readConfigFile();    
       
        
        playerCoordinate(mapLevels.get(currentLevel));
        timer = mapLevelTime.get(currentLevel);
        
    }
    /**
     * Takes the Bomb Guy coordinate and matches with the Goal Tile to see if
     * Bomb Guy has passed it
     * @param x
     * @param y
     * @return true or false
     */
    public boolean isLevelPassed(int x, int y){
        if (goalWallCoord.get(0) == x && goalWallCoord.get(1) == y){
            levelPassed = true;
            return true;
        }
        return false;
    }
    /**
     * Checks if the BombGuy has completed all the levels on time
     * @return true or false
     */
    public boolean isGameWon(){
        if (mapLevels.size() == currentLevel){
            return true;
        }
        return false;
    }



    /**
     * Takes the level file as it's parameter and reads all the coordinates of the Walls and keeps them
     * stored in ArrayList
     * @param level
     */
    public void loadMapDrawing(String level){
    this.solidWall = appObject.loadImage( "/src/main/resources/wall/solid.png");
    this.brokenWall =appObject.loadImage("/src/main/resources/broken/broken.png");
    this.emptyWall = appObject.loadImage("/src/main/resources/empty/empty.png");
    this.goalWall= appObject.loadImage("/src/main/resources/goal/goal.png");
    this.iconPlayer= appObject.loadImage("/src/main/resources/icons/player.png");
    this.iconTime= appObject.loadImage("/src/main/resources/icons/clock.png");
     File f= new File(level);     //Creation of File Descriptor for input file
        try{
        FileReader fr=new FileReader(f);   //Creation of File Reader object
            BufferedReader brFile = new BufferedReader(fr);  //Creation of BufferedReader object
            int c = 0; 
            int x = 0;
            int y = 64;            
            while((c = brFile.read()) != -1){         //Read char by Char
                    char character = (char) c;          //converting integer to char
                    if (character == 'W'){
                        appObject.image(solidWall, x, y); 
                        solidWallCoords.add(x);         
                        solidWallCoords.add(y); 
                    }
                    else if (character == 'B'){
                        appObject.image(brokenWall, x,y);
                        brokenWallCoords.add(x);        
                        brokenWallCoords.add(y);   
                    }
                    else if (character == ' '|| character == 'P' || character == 'R' || character == 'Y'){
                        // appObject.image(emptyWall, x,y); 
                        emptyWallCoords.add(x);
                        emptyWallCoords.add(y);  
                    }
                    else if (character == 'G'){
                        // appObject.image(goalWall, x,y); 
                        goalWallCoord.add(x);
                        goalWallCoord.add(y);          
                    }
                    x += 32;
                    if (x == 480){
                        y += 32;
                        x = -64;
                    }  
            }
            
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    /**
     * Draws the images of the walls on the map
     */

    public void renderWalls(){
        for (int i = 0; i < solidWallCoords.size(); i = i +2){
            appObject.image(solidWall, solidWallCoords.get(i),solidWallCoords.get(i+1));
        }
        for (int i = 0; i < brokenWallCoords.size(); i = i +2){
            appObject.image(brokenWall, brokenWallCoords.get(i),brokenWallCoords.get(i+1));
        } 
        for (int i = 0; i < emptyWallCoords.size(); i = i +2){
            appObject.image(emptyWall, emptyWallCoords.get(i),emptyWallCoords.get(i+1));
        } 
        for (int i = 0; i < goalWallCoord.size(); i = i +2){
            appObject.image(goalWall, goalWallCoord.get(i),goalWallCoord.get(i+1));
        } 
    }

    /**
     * Takes the current level file and keeps the positions
     * of the players saved (Bomb GUy/ Red Enemy/ Yellow Enemy) 
     * @param level
     */
    public void playerCoordinate(String level){
        
        File f= new File(level);     //Creation of File Descriptor for input file
        try{
        FileReader fr=new FileReader(f);   //Creation of File Reader object
        
            BufferedReader brFile = new BufferedReader(fr);  //Creation of BufferedReader object
            int c = 0; 
            int x = 0;
            int y = 64;
            while((c = brFile.read()) != -1){         //Read char by Char
            
                    char character = (char) c;          //converting integer to char
                    if (character == 'P' && playerCount ==0){
                        this.bombGuyPositionX = x;
                        this.bombGuyPositionY  = y;
                        playerCount++;

                    }
                    if (character == 'R'){
                        redEnemyPositionX = x;
                        redEnemyPositionY  = y;
                        redEnemyList.add(new RedEnemy(this, appObject, redEnemyPositionX, redEnemyPositionY));
                        redEnemyList.get(redEnemyList.size()-1).loadRedEnemyImages();
                        
                    }
                    if (character == 'Y'){
                        yellowEnemyPositionX = x;
                        yellowEnemyPositionY = y;
                        yellowEnemyList.add(new YellowEnemy(this, appObject, yellowEnemyPositionX, yellowEnemyPositionY));
                        yellowEnemyList.get(yellowEnemyList.size()-1).loadYellowEnemyImages();
                         
                    }
                    x += 32;
                    if (x == 480){
                        y += 32;
                        x = -64;
                    }
            }
        }catch (Exception e){
          
        }
    }

    /**
     * Reads the config file and extracts information from it
     * @return Arraylist<String> configDetails
     */
    public ArrayList<String> readConfigFile() {
        ArrayList<String> configDetails = new ArrayList<String>();  
        JSONObject mapDetails = null;
        String lives;

        File jsonFile = new File(configFile);
        try {
            Scanner scan = new Scanner(jsonFile);
            StringBuffer sb = new StringBuffer();
            while (scan.hasNext()) {
                sb.append("" + scan.nextLine());
            }
            scan.close();
            mapDetails = JSONObject.parse(sb.toString());
            lives = mapDetails.get("lives").toString();
            configDetails.add(lives);
            JSONArray levelDetails = mapDetails.getJSONArray("levels");
            for(int i = 0; i < levelDetails.size(); i ++) {
                String path = levelDetails.getJSONObject(i).getString("path");
                mapLevels.add(path);
                int time = levelDetails.getJSONObject(i).getInt("time");
                mapLevelTime.add(time);
                configDetails.add(path);
                configDetails.add(Integer.toString(time));
            }

        }catch (Exception e){

        }
       return configDetails;
      
    }

    /**
     * Takes the First and Last line of the Map and checks if is completely bounded by Solid Walls
     * @param line
     * @return true or false
     */
    public boolean isFirstLastValid(String line) {
        String FIRSTLASTLINE = "WWWWWWWWWWWWWWW";
        if (line.equals(FIRSTLASTLINE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Takes the lines in between of the map and checks if it starts and ends with a Solid Wall
     * @param line
     * @return true or false
     */
    public boolean isMidValid(String line) {
        if (line.startsWith("W") && line.endsWith("W") && line.length() == 15){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Reads the map and checks if the map is valid
     * i.e: Map is bounded by Solid Walls
     * @return true or false
     */

    public boolean isMapValid(){
        boolean isFirstLineValid = false;
        boolean isMidLineValid = true;
        boolean isFirstLineFound = false;
        boolean isLastLineValid = false;
        boolean isPlayerFound=false;
        boolean isGoalFound=false;
        String line;
        int Wcount = 0;
        File myFile = new File(mapLevels.get(Map.currentLevel));
        try {
            Scanner levelFile = new Scanner(myFile);

            while (levelFile.hasNextLine()) {
                line = levelFile.nextLine();

                //first line validity starts ---------

                if (line.startsWith("W") && !isFirstLineFound) {
                    isFirstLineFound = true;
                    Wcount++;

                    isFirstLineValid = isFirstLastValid(line);
                    if (!isFirstLineValid){
                        return false;
                    }
                }
                //first line validity ends here --------

                // Mid and End line validity starts ------------
                else if(Wcount == 1){

                    if (!isPlayerFound){
                        isPlayerFound = line.contains("P");
                    }
                    if (!isGoalFound){
                        isGoalFound = line.contains("G");
                    }

                    isMidLineValid = isMidValid(line);

                    if(!isMidLineValid){
                        return false;
                    }
                    isLastLineValid = isFirstLastValid(line);

                    if(isLastLineValid){
                        Wcount++;
                    }
                }
                //Mid and End line validity ends -----------
            
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isPlayerFound && isGoalFound){
            return true;
        }else return false;
    }
    /**
     * Returns if the time has finished
     * @return true or false
     */
    public boolean isTimeFinished (){
        return this.timer <= 0;
    }

    /**
     * Returns the map current level
     * @return Map.currentLevel
     */
    public int getCurrentLevel(){
        return Map.currentLevel;
    }
    /**
     * Returns x-coordinate of Bomb Guy
     * @return bombGuyPositionX
     */
    public int getBombGuyPositionX(){
        return bombGuyPositionX;
    }

    /**
     * Returns y-coordinate of BombGuy
     * @return bombGuyPositionY
     */
    public int getBombGuyPositionY(){
        return bombGuyPositionY;
    }
    /**
     * Returns x-coordinate of Red Enemy
     * @return yellowEnemyPositionX
     */
    public int getRedEnemyPositionX(){
        return redEnemyPositionX;
    }
    /**
     * Returns y-coordinate of Red Enemy
     * @return redEnemyPositionY
     */
    public int getRedEnemyPositionY(){
        return redEnemyPositionY;
    }
    /**
     * Returns x-coordinate of Yellow Enemy
     * @return yellowEnemyPositionX
     */
    public int getYellowEnemyPositionX(){
        return yellowEnemyPositionX;
    }
    /**
     * Returns y-coordinate of Yellow Enemy
     * @return yellowEnemyPositionY
     */
    public int getYellowEnemyPositionY(){
        return yellowEnemyPositionY;
    }
    /**
     * Retuns SOlid Wall image
     * @return solidWall
     */
    public PImage getSolidWall(){
        return solidWall;
    }
    /**
     * Retuns Broken Wall image
     * @return brokenWall
     */
    public PImage getBrokenWall(){
        return brokenWall;
    }
    /**
     * Retuns Empty Wall image
     * @return emptyWall
     */
    public PImage getEmptyWall(){
        return emptyWall;
    }
    /**
     * Returns goal wall image
     * @return goalWall
     */
    public PImage getGoalWall(){
        return goalWall;
    }
    /**
     * Returns the player icon image
     * @return iconPlayer
     */
    public PImage getIconPlayer(){
        return iconPlayer;
    }
    /**
     * Returns the time icon image
     * @return iconTime
     */
    public PImage getIconTime(){
        return iconTime;
    }
    /**
     * Returns the ArrayList of redEnemy objects
     * @return redEnemyList
     */
    public ArrayList<RedEnemy> getRedEnemyList(){
        return redEnemyList;
    }
    /**
     * Returns the ArrayList of YellowEnemy objects
     * @return yellowEnemyList
     */
    public ArrayList<YellowEnemy> getYellowEnemyList(){
        return yellowEnemyList;
    }
    /**
     * Returns the ArrayList containing the empty tile coordinates
     * @return emmptyWallCoords
     */
    public ArrayList<Integer> getEmptyWallCoords(){
        return emptyWallCoords;
    }

//new addition
/**
 * Takes the file name as the parameter and sets
 * name of the file we will be loading our game from
 * @param fileName
 */
    public void setConfigFile(String fileName){
        configFile = fileName;
    }

}

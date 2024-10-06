package demolition;
import processing.core.PApplet;
// import processing.event.KeyEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class Tests2 {
    // Create an instance of your application
    App app = new App();
    Map myMap;
    PApplet pApp;
    BombGuy bombGuy;
    RedEnemy redEnemy;
    YellowEnemy yellowEnemy;
    @BeforeEach
    public void setup(){
        app.noLoop();
        PApplet.runSketch(new String[] {"App"}, app);
        // app.setup();
        app.delay(1000);
        myMap = new Map(new PApplet());
        pApp = new PApplet();
        // bombGuy = new BombGuy(myMap, app, myMap.getBombGuyPositionX(), myMap.getBombGuyPositionY());
        redEnemy = new RedEnemy(myMap, app, myMap.getRedEnemyPositionX(), myMap.getRedEnemyPositionY());
        yellowEnemy = new YellowEnemy (myMap, app, myMap.getYellowEnemyPositionX(), myMap.getYellowEnemyPositionY());

    }
     @Test
    public void bombGuyDoesNotWin(){
        Map.currentLevel = 1;
        assertFalse(myMap.isGameWon());
        Map.currentLevel = 0;
    }

    @Test
    public void bombGuyWinsGame(){
        Map.currentLevel = 2;
        assertTrue(myMap.isGameWon());
        Map.currentLevel = 0;
    }

    @Test
    public void levelResetTest(){
        app.levelReset();
        assertEquals(0, myMap.getCurrentLevel());
    }

    @Test
    public void gameOverTest(){
        App.bombGuyLife = 0;
        app.draw();
    }

    
    @Test
    public void gameWonTest(){
        app.gameWon = true;
        app.draw();
    }


    @Test
    public void redEnemyStepTest(){
        redEnemy.setDirection("Left");
        int initialCoord = redEnemy.getX_coord();
        redEnemy.step();
        assertEquals(initialCoord - 32 ,redEnemy.getX_coord());
    }
    
    @Test
    public void redEnemyStepTest2(){
        redEnemy.setDirection("Right");
        int initialCoord = redEnemy.getX_coord();
        redEnemy.step();
        assertEquals(initialCoord + 32 ,redEnemy.getX_coord());
    }

    @Test
    public void redEnemyStepTest3(){
        redEnemy.setDirection("Up");
        int initialCoord = redEnemy.getY_coord();
        redEnemy.step();
        assertEquals(initialCoord- 32 ,redEnemy.getY_coord());
    }

    @Test
    public void redEnemyStepTest4(){
        redEnemy.setDirection("Down");
        int initialCoord = redEnemy.getY_coord();
        redEnemy.step();
        assertEquals(initialCoord + 32 ,redEnemy.getY_coord());
    }

    @Test
    public void yellowEnemyStepTest(){
        yellowEnemy.setDirection("Left");
        int initialCoord = yellowEnemy.getX_coord();
        yellowEnemy.step();
        assertEquals(initialCoord - 32 ,yellowEnemy.getX_coord());
    }
    
    @Test
    public void yellowEnemyStepTest2(){
        yellowEnemy.setDirection("Right");
        int initialCoord = yellowEnemy.getX_coord();
        yellowEnemy.step();
        assertEquals(initialCoord + 32 ,yellowEnemy.getX_coord());
    }

    @Test
    public void yellowEnemyStepTest3(){
        yellowEnemy.setDirection("Up");
        int initialCoord = yellowEnemy.getY_coord();
        yellowEnemy.step();
        assertEquals(initialCoord- 32 ,yellowEnemy.getY_coord());
    }

    @Test
    public void yellowEnemyStepTest4(){
        yellowEnemy.setDirection("Down");
        int initialCoord = yellowEnemy.getY_coord();
        yellowEnemy.step();
        assertEquals(initialCoord + 32 ,yellowEnemy.getY_coord());
    }


    @Test
    public void explosionHinderedByWallTestRight(){
        Bomb bomb = new Bomb(myMap, pApp, bombGuy, app);
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(132);
        myMap.solidWallCoords.add(100);
        bomb.setBombX(100);
        bomb.setBombY(100);
        assertTrue(bomb.explosionHinderedByWall("Right", 100, 100));
    }

    @Test
    public void explosionHinderedByWallTestLeft(){
        Bomb bomb = new Bomb(myMap, pApp, bombGuy, app);
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(100);
        myMap.solidWallCoords.add(100);
        bomb.setBombX(132);
        bomb.setBombY(100);
        assertTrue(bomb.explosionHinderedByWall("Left", 132, 100));
    }

    @Test
    public void explosionHinderedByWallTestRightEnd(){
        Bomb bomb = new Bomb(myMap, pApp, bombGuy, app);
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(164);
        myMap.solidWallCoords.add(100);
        bomb.setBombX(100);
        bomb.setBombY(100);
        assertTrue(bomb.explosionHinderedByWall("Right End", 100, 100));
    }

    @Test
    public void explosionHinderedByWallTestLeftEnd(){
        Bomb bomb = new Bomb(myMap, pApp, bombGuy, app);
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(100);
        myMap.solidWallCoords.add(100);
        bomb.setBombX(164);
        bomb.setBombY(100);
        assertTrue(bomb.explosionHinderedByWall("Left End", 164, 100));
    }

    @Test
    public void explosionHinderedByWallTestVerticalTop(){
        Bomb bomb = new Bomb(myMap, pApp, bombGuy, app);
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(100);
        myMap.solidWallCoords.add(100);
        bomb.setBombX(100);
        bomb.setBombY(132);
        assertTrue(bomb.explosionHinderedByWall("Vertical Top", 100, 132));
    }
    
    @Test
    public void explosionHinderedByWallTestVerticalBottom(){
        Bomb bomb = new Bomb(myMap, pApp, bombGuy, app);
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(100);
        myMap.solidWallCoords.add(132);
        bomb.setBombX(100);
        bomb.setBombY(100);
        assertTrue(bomb.explosionHinderedByWall("Vertical Bottom", 100, 100));
    }

    @Test
    public void explosionHinderedByWallTestEndTop(){
        Bomb bomb = new Bomb(myMap, pApp, bombGuy, app);
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(100);
        myMap.solidWallCoords.add(100);
        bomb.setBombX(100);
        bomb.setBombY(164);
        assertTrue(bomb.explosionHinderedByWall("End Top", 100, 164));
    }

    @Test
    public void explosionHinderedByWallTestEndBottom(){
        Bomb bomb = new Bomb(myMap, pApp, bombGuy, app);
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(100);
        myMap.solidWallCoords.add(164);
        bomb.setBombX(100);
        bomb.setBombY(100);
        assertTrue(bomb.explosionHinderedByWall("End Bottom", 100, 100));
    }


    @Test
    public void yellowEnemyStepTest5(){
       myMap.solidWallCoords.clear();
       myMap.solidWallCoords.add(64);
       myMap.solidWallCoords.add(64);

       yellowEnemy.setDirection("Up");
       yellowEnemy.setX_coord(64);
       yellowEnemy.setY_coord(96);
      
       yellowEnemy.step();
       assertEquals(96,yellowEnemy.getY_coord());
    
    }

    @Test
    public void yellowEnemyDirection(){
        yellowEnemy.setDirection("Up");
        myMap.solidWallCoords.clear();
        myMap.solidWallCoords.add(64);
        myMap.solidWallCoords.add(64);
        myMap.solidWallCoords.add(96);
        myMap.solidWallCoords.add(64);

        yellowEnemy.setX_coord(64);
        yellowEnemy.setY_coord(96);
      
        yellowEnemy.clockwiseDirection("Up");
        assertEquals(96,yellowEnemy.getY_coord());
    
    }
    

 }

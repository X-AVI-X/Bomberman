package demolition;
import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;



public class MapTest {
    // Create an instance of your application
    App app = new App();
    Map mapObj;
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
        mapObj = new Map(new PApplet());
        pApp = new PApplet();
        bombGuy = new BombGuy(mapObj, app, mapObj.getBombGuyPositionX(), mapObj.getBombGuyPositionY());
        redEnemy = new RedEnemy(mapObj, app, mapObj.getRedEnemyPositionX(), mapObj.getRedEnemyPositionY());
        yellowEnemy = new YellowEnemy (mapObj, app, mapObj.getYellowEnemyPositionX(), mapObj.getYellowEnemyPositionY());
        bombGuy.loadBombGuyImages();

    }



    @Test
    public void mapImageTest(){  
        for(int i = 0; i < app.mapList.size(); i++){
            assertNotNull(app.mapList.get(i).getBrokenWall());
            assertNotNull(app.mapList.get(i).getSolidWall());
            assertNotNull(app.mapList.get(i).getEmptyWall());
            assertNotNull(app.mapList.get(i).getGoalWall());
        }
    }

    @Test
    public void timeNotFinished(){
        assertFalse(app.mapList.get(0).isTimeFinished());
    }

    @Test
    public void timeFinished(){
        app.mapList.get(0).timer = 0;
        assertTrue(app.mapList.get(0).isTimeFinished());
    }

    @Test
    public void mapValidityTest(){
        for(int i = 0; i < app.mapList.size(); i++){
            assertTrue(app.mapList.get(i).isMapValid());
        }
    }

 
    
    @Test
    public void emptyWallCoordsTest(){
        for(int i = 0; i < app.mapList.size() ; i ++)
        assertNotNull(app.mapList.get(i).getEmptyWallCoords());
    }

    @Test
    public void setConfigFIle(){
        app.mapList.get(0).setConfigFile("configX.json");
        assertEquals("configX.json", app.mapList.get(0).configFile);
    }

    @Test
    public void passALevelTest(){
        App newApp = new App();
        newApp.noLoop();
        PApplet.runSketch(new String[] {"App"}, newApp);
   
        newApp.delay(1000);
        newApp.bombGuy.setX_coord(416);
        newApp.bombGuy.setY_coord(416);
        newApp.draw();
    }

    @Test
    public void currentLevelTest(){
        assertEquals(1, app.mapList.get(0).getCurrentLevel());
    }

    @Test
    public void removeBrokenWallTest(){
        mapObj.brokenWallCoords.clear();
        Bomb newBomb = new Bomb(mapObj, pApp, bombGuy, app);
        mapObj.brokenWallCoords.add(100);
        mapObj.brokenWallCoords.add(100);

        newBomb.removeBrokenWall(100, 100);
        assertTrue(mapObj.brokenWallCoords.size() == 0);
    }

   
}


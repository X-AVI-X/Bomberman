package demolition;
import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BasicTest {
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
    public void creatingBombTest(){
        Bomb bomb1 = new Bomb(mapObj, pApp, bombGuy, app);
        assertNotNull(bomb1);
    }

    @Test
    public void creatingBombGuyTest(){
        BombGuy bombGuy = new BombGuy(mapObj, pApp, 32, 64);
        assertNotNull(bombGuy);

    }

    @Test
    public void creatingRedEnemyTest(){
        RedEnemy redEnemy = new RedEnemy(mapObj, pApp, 64,64);
        assertNotNull(redEnemy);

    }

    @Test
    public void creatingYellowEnemyTest(){
        YellowEnemy yellowEnemy = new YellowEnemy(mapObj, pApp, 64,64);
        assertNotNull(yellowEnemy);

    }
    @Test
    public void mapConstructorTest(){
        Map map = new Map(pApp);
        assertNotNull(map);
    }
    @Test 
    public void bombGuySetLivesTest(){
        assertEquals(3, BombGuy.life);
    }
    @Test
    public void bombGuyMeetsRedEnemyTest(){
        bombGuy.setX_coord(100);
        bombGuy.setY_coord(100);
        redEnemy.setX_coord(100);
        redEnemy.setY_coord(100);
        mapObj.getRedEnemyList().add(redEnemy);
        assertTrue(bombGuy.bombGuyMeetsEnemies());
    }
    @Test 
    public void bombGuyMeetsYellowEnemyTest(){
        bombGuy.setX_coord(120);
        bombGuy.setY_coord(120);
        yellowEnemy.setX_coord(120);
        yellowEnemy.setY_coord(120);
        mapObj.getYellowEnemyList().add(yellowEnemy);
        assertTrue(bombGuy.bombGuyMeetsEnemies());
    }
    @Test
    public void bombGuyNotMeetYellow(){
        bombGuy.setX_coord(100);
        bombGuy.setY_coord(90);
        yellowEnemy.setX_coord(120);
        yellowEnemy.setY_coord(120);
        mapObj.getYellowEnemyList().add(yellowEnemy);
        assertFalse(bombGuy.bombGuyMeetsEnemies());
    }

    @Test
    public void bombGuyNotMeetRed(){
        bombGuy.setX_coord(100);
        bombGuy.setY_coord(90);
        yellowEnemy.setX_coord(120);
        yellowEnemy.setY_coord(120);
        mapObj.getRedEnemyList().add(redEnemy);
        assertFalse(bombGuy.bombGuyMeetsEnemies());
    }

    @Test
    public void bombGuyNotPassesLevel1(){
        mapObj.goalWallCoord.clear();
        mapObj.goalWallCoord.add(416);
        mapObj.goalWallCoord.add(416);
        assertFalse(mapObj.isLevelPassed(400,400));
    }

    @Test
    public void bombGuyPassesLevel1(){
        mapObj.goalWallCoord.clear();
        mapObj.goalWallCoord.add(416);
        mapObj.goalWallCoord.add(416);
        assertTrue(mapObj.isLevelPassed(416,416));
    }

    @Test
    public void BombGuyMoves(){
        bombGuy.setX_coord(64);
        bombGuy.setY_coord(64);
        bombGuy.move("Left");
        assertEquals(32, bombGuy.getX_coord());

        bombGuy.move("Right");
        assertEquals(64, bombGuy.getX_coord());

        bombGuy.setX_coord(32);
        bombGuy.move("Down");
        assertEquals(96, bombGuy.getY_coord());

        bombGuy.move("Up");
        assertEquals(64, bombGuy.getY_coord());

        bombGuy.move("Up");
        assertNotEquals(64, bombGuy.getY_coord());
    }

    @Test
    public void bombGuyCollisionTest(){
        bombGuy.setX_coord(32);
        bombGuy.setY_coord(64);
        mapObj.solidWallCoords.clear();
        mapObj.solidWallCoords.add(32);
        mapObj.solidWallCoords.add(32);
        assertTrue(bombGuy.collidesWithWall("Up"));

        mapObj.solidWallCoords.clear();
        mapObj.solidWallCoords.add(32);
        mapObj.solidWallCoords.add(96);
        assertTrue(bombGuy.collidesWithWall("Down"));

        mapObj.solidWallCoords.clear();
        mapObj.solidWallCoords.add(64);
        mapObj.solidWallCoords.add(64);
        assertTrue(bombGuy.collidesWithWall("Right"));

        mapObj.solidWallCoords.clear();
        mapObj.solidWallCoords.add(0);
        mapObj.solidWallCoords.add(64);
        assertTrue(bombGuy.collidesWithWall("Left"));


        // for broken walls
        bombGuy.setX_coord(32);
        bombGuy.setY_coord(64);
        mapObj.brokenWallCoords.clear();
        mapObj.brokenWallCoords.add(32);
        mapObj.brokenWallCoords.add(32);
        assertTrue(bombGuy.collidesWithWall("Up"));

        mapObj.brokenWallCoords.clear();
        mapObj.brokenWallCoords.add(32);
        mapObj.brokenWallCoords.add(96);
        assertTrue(bombGuy.collidesWithWall("Down"));

        mapObj.brokenWallCoords.clear();
        mapObj.brokenWallCoords.add(64);
        mapObj.brokenWallCoords.add(64);
        assertTrue(bombGuy.collidesWithWall("Right"));

        mapObj.brokenWallCoords.clear();
        mapObj.brokenWallCoords.add(0);
        mapObj.brokenWallCoords.add(64);
        assertTrue(bombGuy.collidesWithWall("Left"));

        mapObj.brokenWallCoords.clear();
        mapObj.brokenWallCoords.add(64);
        mapObj.brokenWallCoords.add(64);
        assertTrue(bombGuy.collidesWithWall("Right"));
    }

    @Test
    public void renderPlayerTest(){
        bombGuy.setX_coord(96);
        bombGuy.setY_coord(64);
        bombGuy.setDirection("Left");
        bombGuy.render();
        bombGuy.setDirection("Right");
        bombGuy.render();
        bombGuy.setDirection("Up");
        bombGuy.render();
        bombGuy.setDirection("Down");
        // assert
        // bombGuy.render();
    }

    @Test
    public void PlayerSetDirectionTest(){
        redEnemy.setDirection("Right");
        assertEquals("Right", redEnemy.getDirection());
    }
    
    @Test
    public void bombSetXYTest(){
        Bomb bomb = new Bomb(mapObj, app, bombGuy, app);
        bomb.setBombX(32);
        bomb.setBombY(64);
        assertEquals(32, bomb.getBombX());
        assertEquals(64, bomb.getBombY());
    }

    @Test 
    public void bombGuyCaughtInExplosionTest(){
        BombGuy newBombGuy = new BombGuy(mapObj, app, mapObj.getBombGuyPositionX(), mapObj.getBombGuyPositionY());
        Bomb newBomb = new Bomb(mapObj, pApp, newBombGuy, app);
        newBombGuy.loadBombGuyImages();
        newBombGuy.setX_coord(100);
        newBombGuy.setY_coord(100);

        app.bombList.add(newBomb);
        assertFalse(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(90, 90));
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(100, 100)); // centre
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;

        app.bombList.add(newBomb);
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(68, 100)); // left
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;
        app.bombList.add(newBomb);
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(132, 100)); // right
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;

        app.bombList.add(newBomb);
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(100, 68)); // top
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;
        app.bombList.add(newBomb);
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(100, 132)); // bottom
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;


        app.bombList.add(newBomb);
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(36, 100)); // left end
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;
        app.bombList.add(newBomb);
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(164, 100)); // right end
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;

        app.bombList.add(newBomb);
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(100, 36)); // top end
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0; 
        app.bombList.add(newBomb);
        assertTrue(app.bombList.get(app.bombList.size()-1).bombGuyCaughtInExplosion(100, 164)); // bottom end
        app.bombList.get(app.bombList.size()-1).callBombGuyFunc = 0;
       
    }


}

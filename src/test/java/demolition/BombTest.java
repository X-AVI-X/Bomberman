package demolition;
import processing.core.PApplet;
import org.junit.jupiter.api.Test;




public class BombTest {
    Map mapObj;
    App app;
    BombGuy bombGuy;
    RedEnemy redEnemy;
    YellowEnemy yellowEnemy;
    PApplet pApp;

    @Test
    public void basicTest() {
        // Create an instance of your application
        App app = new App();

        // Set the program to not loop automatically
        // app.noLoop();

        // Set the path of the config file to use
        // app.setConfig("src/test/resources/config.json");

        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] {"App"}, app);

        // Call App.setup() to load in sprites
        // app.setup();

        // Set a 1 second delay to ensure all resources are loaded
        app.delay(1000);
        mapObj = new Map(new PApplet());
        pApp = new PApplet();
        bombGuy = new BombGuy(mapObj, app, mapObj.getBombGuyPositionX(), mapObj.getBombGuyPositionY());
        redEnemy = new RedEnemy(mapObj, app, mapObj.getRedEnemyPositionX(), mapObj.getRedEnemyPositionY());
        yellowEnemy = new YellowEnemy (mapObj, app, mapObj.getYellowEnemyPositionX(), mapObj.getYellowEnemyPositionY());
        bombGuy.loadBombGuyImages();
        app.bombList.add(new Bomb(app.mapList.get(0), app, app.bombGuy, app));
        app.bombList.get(0).loadBombImages();
        app.bombList.get(0).loadExplosionImages();
        for(int i = 0; i < 152 ; i ++){
            app.bombList.get(0).renderPlaceBomb();
        }
        app.bombList.get(0).renderExplosion();
        
    }

    
 

    
    
}


package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {
    
    @Test
    public void simpleTest() {
        assertEquals(480, App.HEIGHT);
    }

    @Test
    public void widthTest() {
        assertEquals(480, App.WIDTH);
    }

    @Test
    public void FPSTest() {
        assertEquals(60, App.FPS);
    }
}


import me.raven.Engine.Utils.Camera;
import me.raven.Engine.Utils.Window;
import me.raven.Sandbox.Managers.GameManager;
import me.raven.Sandbox.Managers.SceneManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameManagerTest {

    @Test
    @DisplayName("Making sure GameManager ands its essentials are initialized")
    public void TestGameManagerEssentialsInitialization() {
        GameManager gameManager = GameManager.get();
        assertNotNull(gameManager);
        assertNotNull(gameManager.getCamera());
        assertNotNull(gameManager.getSceneManager());
        assertNotNull(gameManager.getRenderer());
    }

    @Test
    public void TestGameManagerSingleton() {
        GameManager gameManager1 = GameManager.get();
        GameManager gameManager2 = GameManager.get();
        assertNotNull(gameManager1);
        assertNotNull(gameManager2);
        assert(gameManager1 == gameManager2);
    }

    @Test
    @DisplayName("Game window should be created in correct dimensions which requires a square aspect ratio")
    public void TestCameraSize() {
        GameManager gameManager = GameManager.get();
        Window window = gameManager.getWindow();
        assertEquals(window.getHeight(), window.getWidth()); // Ensure the window is square
        assertNotNull(window.getTitle()); // Title shouldn't be null
    }

    @Test
    public void TestScene() {
        GameManager gameManager = GameManager.get();
        assertNotNull(gameManager.getSceneManager());

        SceneManager sceneManager = gameManager.getSceneManager();
        sceneManager.createFirstScene();
        assertNotNull(sceneManager.getCurrent());
    }
}

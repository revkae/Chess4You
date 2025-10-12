import me.raven.Sandbox.Managers.GameManager;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Chess Integration Test Suite")
@SelectClasses({
        IntegrationChessTest.class,
        GameManagerTest.class,
        GameTest.class,
        ChessTest.class,
        ComprehensiveChessTest.class
})
public class GameTest {

}
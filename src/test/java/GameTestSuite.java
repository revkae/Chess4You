import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Core Functionality Test Suite")
@SelectClasses({
        ChessTest.class,
        GameManagerTest.class
})
public class GameTestSuite {
}

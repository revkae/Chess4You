import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Integration Test Suite")
@SelectClasses({
        IntegrationChessTest.class,
        ComprehensiveChessTest.class
})
public class IntegrationTestSuite {
}

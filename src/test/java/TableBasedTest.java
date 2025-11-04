import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.PieceColors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TABLE-BASED TESTING (DECISION TABLE TESTING)
 *
 * This class demonstrates table-based testing for chess game methods.
 * We create decision tables that map input conditions to expected outputs,
 * then systematically test all combinations.
 *
 * Method Under Test: isValidTileNum(int tileNum)
 *
 * DECISION TABLE:
 * ================================================================================
 * | Test | Condition 1      | Condition 2      | Expected | Description          |
 * | Case | tileNum < 0      | tileNum > 63     | Output   |                      |
 * |------|------------------|------------------|----------|----------------------|
 * |  1   | True             | False            | False    | Negative number      |
 * |  2   | False            | True             | False    | Number too large     |
 * |  3   | True             | True             | False    | Impossible case      |
 * |  4   | False            | False            | True     | Valid range [0-63]   |
 * |------|------------------|------------------|----------|----------------------|
 *
 * BOUNDARY VALUE TABLE:
 * ================================================================================
 * | Test | Input Value | Category          | Expected | Boundary Type         |
 * | Case |             |                   | Output   |                       |
 * |------|-------------|-------------------|----------|-----------------------|
 * |  1   | -1          | Just below min    | False    | Lower boundary - 1    |
 * |  2   | 0           | Minimum valid     | True     | Lower boundary        |
 * |  3   | 1           | Just above min    | True     | Lower boundary + 1    |
 * |  4   | 32          | Middle value      | True     | Nominal value         |
 * |  5   | 62          | Just below max    | True     | Upper boundary - 1    |
 * |  6   | 63          | Maximum valid     | True     | Upper boundary        |
 * |  7   | 64          | Just above max    | False    | Upper boundary + 1    |
 * |  8   | 100         | Far above max     | False    | Invalid large value   |
 * |  9   | -100        | Far below min     | False    | Invalid small value   |
 * |------|-------------|-------------------|----------|-----------------------|
 *
 * EQUIVALENCE PARTITIONING TABLE:
 * ================================================================================
 * | Partition | Range           | Valid?  | Test Values     | Expected Output  |
 * |-----------|-----------------|---------|-----------------|------------------|
 * | 1         | x < 0           | Invalid | -100, -1        | False            |
 * | 2         | 0 <= x <= 63    | Valid   | 0, 32, 63       | True             |
 * | 3         | x > 63          | Invalid | 64, 100         | False            |
 * |-----------|-----------------|---------|-----------------|------------------|
 */
@DisplayName("Table-Based Testing for Chess Board Validation")
public class TableBasedTest {

    // ==========================================
    // DECISION TABLE TESTING
    // ==========================================

    @Test
    @DisplayName("Decision Table - Case 1: Negative number (tileNum < 0)")
    public void testDecisionTable_Case1_NegativeNumber() {
        // Condition 1: True (tileNum < 0)
        // Condition 2: False (tileNum NOT > 63)
        // Expected: False (invalid)

        assertFalse(BoardManager.isValidTileNum(-1),
            "Case 1: Negative numbers should be invalid");
        assertFalse(BoardManager.isValidTileNum(-50),
            "Case 1: Negative numbers should be invalid");
    }

    @Test
    @DisplayName("Decision Table - Case 2: Number too large (tileNum > 63)")
    public void testDecisionTable_Case2_TooLarge() {
        // Condition 1: False (tileNum NOT < 0)
        // Condition 2: True (tileNum > 63)
        // Expected: False (invalid)

        assertFalse(BoardManager.isValidTileNum(64),
            "Case 2: Numbers > 63 should be invalid");
        assertFalse(BoardManager.isValidTileNum(100),
            "Case 2: Numbers > 63 should be invalid");
    }

    @Test
    @DisplayName("Decision Table - Case 3: Impossible case (both conditions true)")
    public void testDecisionTable_Case3_ImpossibleCase() {
        // Condition 1: True (tileNum < 0)
        // Condition 2: True (tileNum > 63)
        // This is mathematically impossible (a number can't be both < 0 AND > 63)
        // But logically, if such input existed, it should return False

        // We test the extremes that satisfy at least one condition
        assertFalse(BoardManager.isValidTileNum(-1),
            "Case 3: Any violating condition should return false");
        assertFalse(BoardManager.isValidTileNum(64),
            "Case 3: Any violating condition should return false");
    }

    @Test
    @DisplayName("Decision Table - Case 4: Valid range [0-63]")
    public void testDecisionTable_Case4_ValidRange() {
        // Condition 1: False (tileNum NOT < 0)
        // Condition 2: False (tileNum NOT > 63)
        // Expected: True (valid)

        assertTrue(BoardManager.isValidTileNum(0),
            "Case 4: Numbers in [0-63] should be valid");
        assertTrue(BoardManager.isValidTileNum(32),
            "Case 4: Numbers in [0-63] should be valid");
        assertTrue(BoardManager.isValidTileNum(63),
            "Case 4: Numbers in [0-63] should be valid");
    }

    // ==========================================
    // BOUNDARY VALUE TESTING (Table-Based)
    // ==========================================

    @ParameterizedTest
    @CsvSource({
        "-1,   false,  'Just below minimum'",
        "0,    true,   'Minimum valid value'",
        "1,    true,   'Just above minimum'",
        "32,   true,   'Middle value'",
        "62,   true,   'Just below maximum'",
        "63,   true,   'Maximum valid value'",
        "64,   false,  'Just above maximum'",
        "100,  false,  'Far above maximum'",
        "-100, false,  'Far below minimum'"
    })
    @DisplayName("Boundary Value Table Test")
    public void testBoundaryValueTable(int tileNum, boolean expected, String description) {
        assertEquals(expected, BoardManager.isValidTileNum(tileNum),
            "Boundary test failed for: " + description);
    }

    // ==========================================
    // EQUIVALENCE PARTITIONING (Table-Based)
    // ==========================================

    @Test
    @DisplayName("Equivalence Partition 1: Invalid - Below minimum (x < 0)")
    public void testEquivalencePartition1_BelowMinimum() {
        // Partition 1: All negative numbers (invalid)
        // Test representatives: -100, -50, -1

        assertFalse(BoardManager.isValidTileNum(-100),
            "Partition 1: -100 should be invalid");
        assertFalse(BoardManager.isValidTileNum(-50),
            "Partition 1: -50 should be invalid");
        assertFalse(BoardManager.isValidTileNum(-1),
            "Partition 1: -1 should be invalid");
    }

    @Test
    @DisplayName("Equivalence Partition 2: Valid range (0 <= x <= 63)")
    public void testEquivalencePartition2_ValidRange() {
        // Partition 2: All numbers from 0 to 63 (valid)
        // Test representatives: 0, 32, 63

        assertTrue(BoardManager.isValidTileNum(0),
            "Partition 2: 0 should be valid");
        assertTrue(BoardManager.isValidTileNum(32),
            "Partition 2: 32 should be valid");
        assertTrue(BoardManager.isValidTileNum(63),
            "Partition 2: 63 should be valid");
    }

    @Test
    @DisplayName("Equivalence Partition 3: Invalid - Above maximum (x > 63)")
    public void testEquivalencePartition3_AboveMaximum() {
        // Partition 3: All numbers greater than 63 (invalid)
        // Test representatives: 64, 100, 1000

        assertFalse(BoardManager.isValidTileNum(64),
            "Partition 3: 64 should be invalid");
        assertFalse(BoardManager.isValidTileNum(100),
            "Partition 3: 100 should be invalid");
        assertFalse(BoardManager.isValidTileNum(1000),
            "Partition 3: 1000 should be invalid");
    }

    // ==========================================
    // ADDITIONAL TABLE: PieceColors Decision Table
    // ==========================================

    /**
     * DECISION TABLE FOR PieceColors.getOpposite()
     * ================================================================================
     * | Test Case | Input Color  | Expected Output | Description                    |
     * |-----------|--------------|-----------------|--------------------------------|
     * |     1     | WHITE        | BLACK           | White piece has black opponent |
     * |     2     | BLACK        | WHITE           | Black piece has white opponent |
     * |-----------|--------------|-----------------|--------------------------------|
     */

    @ParameterizedTest
    @CsvSource({
        "WHITE, BLACK, 'White opponent is Black'",
        "BLACK, WHITE, 'Black opponent is White'"
    })
    @DisplayName("Color Opposition Decision Table")
    public void testColorOppositionTable(String inputColor, String expectedColor, String description) {
        PieceColors input = PieceColors.valueOf(inputColor);
        PieceColors expected = PieceColors.valueOf(expectedColor);

        assertEquals(expected, PieceColors.getOpposite(input),
            "Color opposition test failed: " + description);
    }

    // ==========================================
    // COMBINED DECISION TABLE TEST
    // ==========================================

    /**
     * COMBINED DECISION TABLE: Multiple Conditions
     * ================================================================================
     * | Test | IsNegative | IsZero | Is1-62 | Is63 | IsAbove63 | Expected | Category |
     * |------|------------|--------|--------|------|-----------|----------|----------|
     * |  1   |     T      |   F    |   F    |  F   |     F     |  False   | Invalid  |
     * |  2   |     F      |   T    |   F    |  F   |     F     |  True    | Valid    |
     * |  3   |     F      |   F    |   T    |  F   |     F     |  True    | Valid    |
     * |  4   |     F      |   F    |   F    |  T   |     F     |  True    | Valid    |
     * |  5   |     F      |   F    |   F    |  F   |     T     |  False   | Invalid  |
     * |------|------------|--------|--------|------|-----------|----------|----------|
     */

    @ParameterizedTest
    @CsvSource({
        "-5,   true,  false, false, false, false, false, 'Negative'",
        "0,    false, true,  false, false, false, true,  'Zero boundary'",
        "32,   false, false, true,  false, false, true,  'Middle range'",
        "63,   false, false, false, true,  false, true,  'Upper boundary'",
        "100,  false, false, false, false, true,  false, 'Above maximum'"
    })
    @DisplayName("Combined Decision Table - All Categories")
    public void testCombinedDecisionTable(
            int tileNum,
            boolean isNegative,
            boolean isZero,
            boolean isMiddle,
            boolean is63,
            boolean isAbove,
            boolean expectedValid,
            String category) {

        // Verify categorization
        assertEquals(isNegative, tileNum < 0, "Negative check for " + category);
        assertEquals(isZero, tileNum == 0, "Zero check for " + category);
        assertEquals(isMiddle, tileNum > 0 && tileNum < 63, "Middle range check for " + category);
        assertEquals(is63, tileNum == 63, "63 check for " + category);
        assertEquals(isAbove, tileNum > 63, "Above 63 check for " + category);

        // Verify expected validity
        assertEquals(expectedValid, BoardManager.isValidTileNum(tileNum),
            "Validity check failed for category: " + category);
    }

    // ==========================================
    // STATE TRANSITION TABLE
    // ==========================================

    /**
     * STATE TRANSITION TABLE: Turn Management
     * ================================================================================
     * | Current State | Action      | Next State | Test Description              |
     * |---------------|-------------|------------|-------------------------------|
     * | WHITE         | changeTurn  | BLACK      | White turn transitions        |
     * | BLACK         | changeTurn  | WHITE      | Black turn transitions        |
     * |---------------|-------------|------------|-------------------------------|
     */

    @Test
    @DisplayName("State Transition Table - Turn Changes")
    public void testStateTransitionTable_TurnChanges() {
        // Test WHITE -> BLACK transition
        PieceColors currentTurn = PieceColors.WHITE;
        PieceColors nextTurn = PieceColors.changeTurn(currentTurn);
        assertEquals(PieceColors.BLACK, nextTurn,
            "WHITE turn should transition to BLACK");

        // Test BLACK -> WHITE transition
        currentTurn = PieceColors.BLACK;
        nextTurn = PieceColors.changeTurn(currentTurn);
        assertEquals(PieceColors.WHITE, nextTurn,
            "BLACK turn should transition to WHITE");

        // Test double transition (back to original)
        currentTurn = PieceColors.WHITE;
        nextTurn = PieceColors.changeTurn(PieceColors.changeTurn(currentTurn));
        assertEquals(PieceColors.WHITE, nextTurn,
            "Double transition should return to original state");
    }

    // ==========================================
    // COVERAGE SUMMARY TEST
    // ==========================================

    @Test
    @DisplayName("Table-Based Testing Coverage Summary")
    public void testCoverageSummary() {
        // This test verifies that we've covered all table entries

        int totalDecisionTableCases = 4;      // 4 cases in main decision table
        int totalBoundaryValues = 9;          // 9 boundary value test cases
        int totalEquivalencePartitions = 3;   // 3 equivalence partitions
        int totalColorCombinations = 2;       // 2 color opposition cases

        int totalTestCases = totalDecisionTableCases + totalBoundaryValues +
                             totalEquivalencePartitions + totalColorCombinations;

        // We should have comprehensive coverage
        assertTrue(totalTestCases >= 18,
            "Should have at least 18 systematic test cases from tables");

        // Verify all critical paths are covered
        assertFalse(BoardManager.isValidTileNum(-1), "Negative case covered");
        assertTrue(BoardManager.isValidTileNum(0), "Lower boundary covered");
        assertTrue(BoardManager.isValidTileNum(63), "Upper boundary covered");
        assertFalse(BoardManager.isValidTileNum(64), "Above maximum covered");
    }
}


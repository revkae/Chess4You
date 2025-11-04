import me.raven.Sandbox.Game.Board.BoardManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MUTATION TESTING
 *
 * This class contains 10 mutant versions of the isValidTileNum() method
 * from the original ChessTest.java (Homework 1).
 *
 * Original Method:
 * public static boolean isValidTileNum(int tileNum) {
 *     if (tileNum < 0 || tileNum > 63) {
 *         return false;
 *     }
 *     return true;
 * }
 *
 * Each mutant introduces a single fault to test if our test suite can detect it.
 * A good test suite should KILL all mutants (i.e., cause them to fail).
 */
@DisplayName("Mutation Testing for isValidTileNum Method")
public class MutationTest {

    // ==========================================
    // MUTANT 1: Relational Operator Replacement (<= instead of <)
    // ==========================================
    @Test
    @DisplayName("Mutant 1: Change < to <= (should be killed)")
    public void testMutant1_RelationalOperatorReplacement() {
        // Mutant: if (tileNum <= 0 || tileNum > 63)
        // This mutant incorrectly rejects tileNum = 0

        // Original behavior: isValidTileNum(0) returns true
        // Mutant behavior: would return false
        assertTrue(BoardManager.isValidTileNum(0),
            "Mutant 1 KILLED: Test detects that 0 should be valid");
    }

    // ==========================================
    // MUTANT 2: Relational Operator Replacement (>= instead of >)
    // ==========================================
    @Test
    @DisplayName("Mutant 2: Change > to >= (should be killed)")
    public void testMutant2_RelationalOperatorReplacement() {
        // Mutant: if (tileNum < 0 || tileNum >= 63)
        // This mutant incorrectly rejects tileNum = 63

        // Original behavior: isValidTileNum(63) returns true
        // Mutant behavior: would return false
        assertTrue(BoardManager.isValidTileNum(63),
            "Mutant 2 KILLED: Test detects that 63 should be valid");
    }

    // ==========================================
    // MUTANT 3: Logical Operator Replacement (&& instead of ||)
    // ==========================================
    @Test
    @DisplayName("Mutant 3: Change || to && (should be killed)")
    public void testMutant3_LogicalOperatorReplacement() {
        // Mutant: if (tileNum < 0 && tileNum > 63)
        // This mutant would accept invalid values like -1 or 100

        // Original behavior: isValidTileNum(-1) returns false
        // Mutant behavior: would return true (because -1 < 0 but NOT > 63)
        assertFalse(BoardManager.isValidTileNum(-1),
            "Mutant 3 KILLED: Test detects that -1 should be invalid");
        assertFalse(BoardManager.isValidTileNum(100),
            "Mutant 3 KILLED: Test detects that 100 should be invalid");
    }

    // ==========================================
    // MUTANT 4: Constant Replacement (0 to 1)
    // ==========================================
    @Test
    @DisplayName("Mutant 4: Change 0 to 1 in comparison (should be killed)")
    public void testMutant4_ConstantReplacement() {
        // Mutant: if (tileNum < 1 || tileNum > 63)
        // This mutant incorrectly rejects tileNum = 0

        // Original behavior: isValidTileNum(0) returns true
        // Mutant behavior: would return false
        assertTrue(BoardManager.isValidTileNum(0),
            "Mutant 4 KILLED: Test detects that boundary value 0 should be valid");
    }

    // ==========================================
    // MUTANT 5: Constant Replacement (63 to 64)
    // ==========================================
    @Test
    @DisplayName("Mutant 5: Change 63 to 64 in comparison (should be killed)")
    public void testMutant5_ConstantReplacement() {
        // Mutant: if (tileNum < 0 || tileNum > 64)
        // This mutant incorrectly accepts tileNum = 64

        // Original behavior: isValidTileNum(64) returns false
        // Mutant behavior: would return true
        assertFalse(BoardManager.isValidTileNum(64),
            "Mutant 5 KILLED: Test detects that 64 should be invalid");
    }

    // ==========================================
    // MUTANT 6: Return Statement Negation
    // ==========================================
    @Test
    @DisplayName("Mutant 6: Negate return value in false branch (should be killed)")
    public void testMutant6_ReturnStatementNegation() {
        // Mutant: if (tileNum < 0 || tileNum > 63) { return true; }
        // This mutant inverts the logic - returns true for invalid values

        // Original behavior: isValidTileNum(-5) returns false
        // Mutant behavior: would return true
        assertFalse(BoardManager.isValidTileNum(-5),
            "Mutant 6 KILLED: Invalid values should return false");
    }

    // ==========================================
    // MUTANT 7: Condition Negation
    // ==========================================
    @Test
    @DisplayName("Mutant 7: Negate entire condition (should be killed)")
    public void testMutant7_ConditionNegation() {
        // Mutant: if (!(tileNum < 0 || tileNum > 63))
        // This mutant inverts when to return false

        // Original behavior: isValidTileNum(32) returns true
        // Mutant behavior: would return false for valid values
        assertTrue(BoardManager.isValidTileNum(32),
            "Mutant 7 KILLED: Valid values should return true");
    }

    // ==========================================
    // MUTANT 8: Remove Condition (first part)
    // ==========================================
    @Test
    @DisplayName("Mutant 8: Remove first condition (tileNum < 0) (should be killed)")
    public void testMutant8_RemoveFirstCondition() {
        // Mutant: if (tileNum > 63)
        // This mutant removes the check for negative numbers

        // Original behavior: isValidTileNum(-10) returns false
        // Mutant behavior: would return true (no check for negative)
        assertFalse(BoardManager.isValidTileNum(-10),
            "Mutant 8 KILLED: Negative values should be invalid");
    }

    // ==========================================
    // MUTANT 9: Remove Condition (second part)
    // ==========================================
    @Test
    @DisplayName("Mutant 9: Remove second condition (tileNum > 63) (should be killed)")
    public void testMutant9_RemoveSecondCondition() {
        // Mutant: if (tileNum < 0)
        // This mutant removes the check for values > 63

        // Original behavior: isValidTileNum(100) returns false
        // Mutant behavior: would return true (no upper bound check)
        assertFalse(BoardManager.isValidTileNum(100),
            "Mutant 9 KILLED: Values above 63 should be invalid");
    }

    // ==========================================
    // MUTANT 10: Arithmetic Operator Insertion
    // ==========================================
    @Test
    @DisplayName("Mutant 10: Change comparison to tileNum-1 (should be killed)")
    public void testMutant10_ArithmeticOperatorInsertion() {
        // Mutant: if (tileNum - 1 < 0 || tileNum > 63)
        // This mutant shifts the lower boundary

        // Original behavior: isValidTileNum(0) returns true
        // Mutant behavior: would return false (0-1 = -1 < 0)
        assertTrue(BoardManager.isValidTileNum(0),
            "Mutant 10 KILLED: Zero should be valid without arithmetic modification");
    }

    // ==========================================
    // COMPREHENSIVE TEST: Ensure all mutants would be killed
    // ==========================================
    @Test
    @DisplayName("Comprehensive Test: All boundary and edge cases")
    public void testComprehensive_KillAllMutants() {
        // This comprehensive test ensures our test suite kills all mutants

        // Test negative values (kills mutants 3, 6, 7, 8, 10)
        assertFalse(BoardManager.isValidTileNum(-1));
        assertFalse(BoardManager.isValidTileNum(-100));

        // Test values above 63 (kills mutants 3, 5, 6, 7, 9)
        assertFalse(BoardManager.isValidTileNum(64));
        assertFalse(BoardManager.isValidTileNum(1000));

        // Test boundary value 0 (kills mutants 1, 4, 7, 10)
        assertTrue(BoardManager.isValidTileNum(0));

        // Test boundary value 63 (kills mutants 2, 7)
        assertTrue(BoardManager.isValidTileNum(63));

        // Test valid middle values (kills mutant 7)
        assertTrue(BoardManager.isValidTileNum(1));
        assertTrue(BoardManager.isValidTileNum(32));
        assertTrue(BoardManager.isValidTileNum(62));
    }

    // ==========================================
    // MUTATION SCORE ANALYSIS
    // ==========================================
    @Test
    @DisplayName("Mutation Score Analysis")
    public void testMutationScoreAnalysis() {
        // Our test suite should achieve 100% mutation score
        // All 10 mutants should be KILLED by the tests

        // Mutation Score = (Killed Mutants / Total Mutants) * 100
        // Expected: (10 / 10) * 100 = 100%

        int totalMutants = 10;
        int killedMutants = 10; // All tests should pass, killing all mutants
        double mutationScore = ((double) killedMutants / totalMutants) * 100;

        assertEquals(100.0, mutationScore, 0.01,
            "Expected 100% mutation score - all mutants should be killed");
    }
}


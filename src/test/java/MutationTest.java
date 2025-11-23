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
 *
 * IMPORTANT: Each test compares the ORIGINAL method vs MUTANT method behavior.
 * If they differ, the mutant is KILLED (test passes).
 * If they behave the same, the mutant SURVIVED (test fails - bad!).
 */
@DisplayName("Mutation Testing for isValidTileNum Method")
public class MutationTest {

    // ==========================================
    // ORIGINAL METHOD (for reference)
    // ==========================================
    public static boolean original_isValidTileNum(int tileNum) {
        if (tileNum < 0 || tileNum > 63) {
            return false;
        }
        return true;
    }

    // ==========================================
    // MUTANT IMPLEMENTATIONS
    // ==========================================

    // Mutant 1: Change < to <=
    public static boolean mutant1_isValidTileNum(int tileNum) {
        if (tileNum <= 0 || tileNum > 63) {  // BUG: <= instead of <
            return false;
        }
        return true;
    }

    // Mutant 2: Change > to >=
    public static boolean mutant2_isValidTileNum(int tileNum) {
        if (tileNum < 0 || tileNum >= 63) {  // BUG: >= instead of >
            return false;
        }
        return true;
    }

    // Mutant 3: Change || to &&
    public static boolean mutant3_isValidTileNum(int tileNum) {
        if (tileNum < 0 && tileNum > 63) {  // BUG: && instead of ||
            return false;
        }
        return true;
    }

    // Mutant 4: Change 0 to 1
    public static boolean mutant4_isValidTileNum(int tileNum) {
        if (tileNum < 1 || tileNum > 63) {  // BUG: 1 instead of 0
            return false;
        }
        return true;
    }

    // Mutant 5: Change 63 to 64
    public static boolean mutant5_isValidTileNum(int tileNum) {
        if (tileNum < 0 || tileNum > 64) {  // BUG: 64 instead of 63
            return false;
        }
        return true;
    }

    // Mutant 6: Negate return value
    public static boolean mutant6_isValidTileNum(int tileNum) {
        if (tileNum < 0 || tileNum > 63) {
            return true;  // BUG: returns true instead of false
        }
        return true;
    }

    // Mutant 7: Negate entire condition
    public static boolean mutant7_isValidTileNum(int tileNum) {
        if (!(tileNum < 0 || tileNum > 63)) {  // BUG: negated condition
            return false;
        }
        return true;
    }

    // Mutant 8: Remove first condition
    public static boolean mutant8_isValidTileNum(int tileNum) {
        if (tileNum > 63) {  // BUG: removed tileNum < 0 check
            return false;
        }
        return true;
    }

    // Mutant 9: Remove second condition
    public static boolean mutant9_isValidTileNum(int tileNum) {
        if (tileNum < 0) {  // BUG: removed tileNum > 63 check
            return false;
        }
        return true;
    }

    // Mutant 10: Arithmetic operator insertion
    public static boolean mutant10_isValidTileNum(int tileNum) {
        if (tileNum - 1 < 0 || tileNum > 63) {  // BUG: tileNum - 1
            return false;
        }
        return true;
    }

    // ==========================================
    // MUTANT 1: Relational Operator Replacement (<= instead of <)
    // ==========================================
    @Test
    @DisplayName("Mutant 1: Change < to <= (should be killed)")
    public void testMutant1_RelationalOperatorReplacement() {
        // Mutant: if (tileNum <= 0 || tileNum > 63)
        // This mutant incorrectly rejects tileNum = 0

        // Test with value 0 - Original returns true, Mutant returns false
        boolean originalResult = original_isValidTileNum(0);
        boolean mutantResult = mutant1_isValidTileNum(0);

        assertTrue(originalResult, "Original: 0 should be valid");
        assertFalse(mutantResult, "Mutant: 0 is incorrectly invalid");
        assertNotEquals(originalResult, mutantResult, "MUTANT 1 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 2: Relational Operator Replacement (>= instead of >)
    // ==========================================
    @Test
    @DisplayName("Mutant 2: Change > to >= (should be killed)")
    public void testMutant2_RelationalOperatorReplacement() {
        // Mutant: if (tileNum < 0 || tileNum >= 63)
        // This mutant incorrectly rejects tileNum = 63

        boolean originalResult = original_isValidTileNum(63);
        boolean mutantResult = mutant2_isValidTileNum(63);

        assertTrue(originalResult, "Original: 63 should be valid");
        assertFalse(mutantResult, "Mutant: 63 is incorrectly invalid");
        assertNotEquals(originalResult, mutantResult, "MUTANT 2 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 3: Logical Operator Replacement (&& instead of ||)
    // ==========================================
    @Test
    @DisplayName("Mutant 3: Change || to && (should be killed)")
    public void testMutant3_LogicalOperatorReplacement() {
        // Mutant: if (tileNum < 0 && tileNum > 63)
        // This mutant would accept invalid values like -1 or 100

        // Test with -1
        boolean originalResult = original_isValidTileNum(-1);
        boolean mutantResult = mutant3_isValidTileNum(-1);

        assertFalse(originalResult, "Original: -1 should be invalid");
        assertTrue(mutantResult, "Mutant: -1 is incorrectly valid (no proper check)");
        assertNotEquals(originalResult, mutantResult, "MUTANT 3 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 4: Constant Replacement (0 to 1)
    // ==========================================
    @Test
    @DisplayName("Mutant 4: Change 0 to 1 in comparison (should be killed)")
    public void testMutant4_ConstantReplacement() {
        // Mutant: if (tileNum < 1 || tileNum > 63)
        // This mutant incorrectly rejects tileNum = 0

        boolean originalResult = original_isValidTileNum(0);
        boolean mutantResult = mutant4_isValidTileNum(0);

        assertTrue(originalResult, "Original: 0 should be valid");
        assertFalse(mutantResult, "Mutant: 0 is incorrectly invalid");
        assertNotEquals(originalResult, mutantResult, "MUTANT 4 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 5: Constant Replacement (63 to 64)
    // ==========================================
    @Test
    @DisplayName("Mutant 5: Change 63 to 64 in comparison (should be killed)")
    public void testMutant5_ConstantReplacement() {
        // Mutant: if (tileNum < 0 || tileNum > 64)
        // This mutant incorrectly accepts tileNum = 64

        boolean originalResult = original_isValidTileNum(64);
        boolean mutantResult = mutant5_isValidTileNum(64);

        assertFalse(originalResult, "Original: 64 should be invalid");
        assertTrue(mutantResult, "Mutant: 64 is incorrectly valid");
        assertNotEquals(originalResult, mutantResult, "MUTANT 5 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 6: Return Statement Negation
    // ==========================================
    @Test
    @DisplayName("Mutant 6: Negate return value in false branch (should be killed)")
    public void testMutant6_ReturnStatementNegation() {
        // Mutant: if (tileNum < 0 || tileNum > 63) { return true; }
        // This mutant inverts the logic - returns true for invalid values

        boolean originalResult = original_isValidTileNum(-5);
        boolean mutantResult = mutant6_isValidTileNum(-5);

        assertFalse(originalResult, "Original: -5 should be invalid");
        assertTrue(mutantResult, "Mutant: -5 incorrectly returns true");
        assertNotEquals(originalResult, mutantResult, "MUTANT 6 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 7: Condition Negation
    // ==========================================
    @Test
    @DisplayName("Mutant 7: Negate entire condition (should be killed)")
    public void testMutant7_ConditionNegation() {
        // Mutant: if (!(tileNum < 0 || tileNum > 63))
        // This mutant inverts when to return false

        boolean originalResult = original_isValidTileNum(32);
        boolean mutantResult = mutant7_isValidTileNum(32);

        assertTrue(originalResult, "Original: 32 should be valid");
        assertFalse(mutantResult, "Mutant: 32 is incorrectly invalid");
        assertNotEquals(originalResult, mutantResult, "MUTANT 7 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 8: Remove Condition (first part)
    // ==========================================
    @Test
    @DisplayName("Mutant 8: Remove first condition (tileNum < 0) (should be killed)")
    public void testMutant8_RemoveFirstCondition() {
        // Mutant: if (tileNum > 63)
        // This mutant removes the check for negative numbers

        boolean originalResult = original_isValidTileNum(-10);
        boolean mutantResult = mutant8_isValidTileNum(-10);

        assertFalse(originalResult, "Original: -10 should be invalid");
        assertTrue(mutantResult, "Mutant: -10 is incorrectly valid (no negative check)");
        assertNotEquals(originalResult, mutantResult, "MUTANT 8 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 9: Remove Condition (second part)
    // ==========================================
    @Test
    @DisplayName("Mutant 9: Remove second condition (tileNum > 63) (should be killed)")
    public void testMutant9_RemoveSecondCondition() {
        // Mutant: if (tileNum < 0)
        // This mutant removes the check for values > 63

        boolean originalResult = original_isValidTileNum(100);
        boolean mutantResult = mutant9_isValidTileNum(100);

        assertFalse(originalResult, "Original: 100 should be invalid");
        assertTrue(mutantResult, "Mutant: 100 is incorrectly valid (no upper bound check)");
        assertNotEquals(originalResult, mutantResult, "MUTANT 9 KILLED: Different behavior detected");
    }

    // ==========================================
    // MUTANT 10: Arithmetic Operator Insertion
    // ==========================================
    @Test
    @DisplayName("Mutant 10: Change comparison to tileNum-1 (should be killed)")
    public void testMutant10_ArithmeticOperatorInsertion() {
        // Mutant: if (tileNum - 1 < 0 || tileNum > 63)
        // This mutant shifts the lower boundary

        boolean originalResult = original_isValidTileNum(0);
        boolean mutantResult = mutant10_isValidTileNum(0);

        assertTrue(originalResult, "Original: 0 should be valid");
        assertFalse(mutantResult, "Mutant: 0 is incorrectly invalid (0-1 < 0)");
        assertNotEquals(originalResult, mutantResult, "MUTANT 10 KILLED: Different behavior detected");
    }

    // ==========================================
    // COMPREHENSIVE TEST: Ensure all mutants are killed
    // ==========================================
    @Test
    @DisplayName("Comprehensive Test: Verify all 10 mutants are killed")
    public void testComprehensive_KillAllMutants() {
        // This test verifies that each mutant behaves differently from the original
        int killedMutants = 0;

        // Mutant 1: Test with 0
        if (original_isValidTileNum(0) != mutant1_isValidTileNum(0)) killedMutants++;

        // Mutant 2: Test with 63
        if (original_isValidTileNum(63) != mutant2_isValidTileNum(63)) killedMutants++;

        // Mutant 3: Test with -1
        if (original_isValidTileNum(-1) != mutant3_isValidTileNum(-1)) killedMutants++;

        // Mutant 4: Test with 0
        if (original_isValidTileNum(0) != mutant4_isValidTileNum(0)) killedMutants++;

        // Mutant 5: Test with 64
        if (original_isValidTileNum(64) != mutant5_isValidTileNum(64)) killedMutants++;

        // Mutant 6: Test with -5
        if (original_isValidTileNum(-5) != mutant6_isValidTileNum(-5)) killedMutants++;

        // Mutant 7: Test with 32
        if (original_isValidTileNum(32) != mutant7_isValidTileNum(32)) killedMutants++;

        // Mutant 8: Test with -10
        if (original_isValidTileNum(-10) != mutant8_isValidTileNum(-10)) killedMutants++;

        // Mutant 9: Test with 100
        if (original_isValidTileNum(100) != mutant9_isValidTileNum(100)) killedMutants++;

        // Mutant 10: Test with 0
        if (original_isValidTileNum(0) != mutant10_isValidTileNum(0)) killedMutants++;

        assertEquals(10, killedMutants, "All 10 mutants should be killed by our test suite");
    }

    // ==========================================
    // MUTATION SCORE ANALYSIS
    // ==========================================
    @Test
    @DisplayName("Mutation Score Analysis: Calculate effectiveness")
    public void testMutationScoreAnalysis() {
        // Mutation Score = (Killed Mutants / Total Mutants) * 100
        // A score of 100% means our test suite is very effective

        int totalMutants = 10;
        int killedMutants = 0;

        // Count how many mutants are killed
        if (original_isValidTileNum(0) != mutant1_isValidTileNum(0)) killedMutants++;
        if (original_isValidTileNum(63) != mutant2_isValidTileNum(63)) killedMutants++;
        if (original_isValidTileNum(-1) != mutant3_isValidTileNum(-1)) killedMutants++;
        if (original_isValidTileNum(0) != mutant4_isValidTileNum(0)) killedMutants++;
        if (original_isValidTileNum(64) != mutant5_isValidTileNum(64)) killedMutants++;
        if (original_isValidTileNum(-5) != mutant6_isValidTileNum(-5)) killedMutants++;
        if (original_isValidTileNum(32) != mutant7_isValidTileNum(32)) killedMutants++;
        if (original_isValidTileNum(-10) != mutant8_isValidTileNum(-10)) killedMutants++;
        if (original_isValidTileNum(100) != mutant9_isValidTileNum(100)) killedMutants++;
        if (original_isValidTileNum(0) != mutant10_isValidTileNum(0)) killedMutants++;

        double mutationScore = ((double) killedMutants / totalMutants) * 100;

        System.out.println("========================================");
        System.out.println("MUTATION TESTING RESULTS");
        System.out.println("========================================");
        System.out.println("Total Mutants:  " + totalMutants);
        System.out.println("Killed Mutants: " + killedMutants);
        System.out.println("Mutation Score: " + mutationScore + "%");
        System.out.println("========================================");

        assertEquals(100.0, mutationScore, 0.01,
            "Expected 100% mutation score - all mutants should be killed");
    }
}


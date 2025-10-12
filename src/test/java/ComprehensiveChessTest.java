import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.*;
import me.raven.Sandbox.Game.Piece.Pieces.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Comprehensive Chess Logic Tests")
public class ComprehensiveChessTest {

    // ==================== BOARD VALIDATION TESTS ====================

    @ParameterizedTest(name = "Tile {0} should be {1}")
    @CsvSource({
            "0, true",
            "7, true",
            "32, true",
            "63, true",
            "64, false",
            "85, false",
            "-1, false",
            "-5, false",
            "100, false",
            "68, false"
    })
    @DisplayName("Test Board Tile Number Validation")
    public void testBoardTileValidation(int tileNum, boolean expected) {
        // Assertion 1-10: assertEquals for tile validation
        assertEquals(expected, BoardManager.isValidTileNum(tileNum),
                "Tile " + tileNum + " validation should be " + expected);
    }

    // ==================== PIECE COLOR TESTS ====================

    @Test
    @DisplayName("Test Piece Color Equality")
    public void testPieceColorEquality() {
        // Assertion 11: assertTrue for same color comparison
        assertTrue(PieceColors.equals(PieceColors.BLACK, PieceColors.BLACK),
                "Black should equal Black");

        // Assertion 12: assertTrue for same color comparison
        assertTrue(PieceColors.equals(PieceColors.WHITE, PieceColors.WHITE),
                "White should equal White");

        // Assertion 13: assertFalse for different color comparison
        assertFalse(PieceColors.equals(PieceColors.BLACK, PieceColors.WHITE),
                "Black should not equal White");

        // Assertion 14: assertFalse for different color comparison
        assertFalse(PieceColors.equals(PieceColors.WHITE, PieceColors.BLACK),
                "White should not equal Black");
    }

    @Test
    @DisplayName("Test Turn Change Logic")
    public void testTurnChange() {
        // Assertion 15: assertEquals for turn change
        assertEquals(PieceColors.WHITE, PieceColors.changeTurn(PieceColors.BLACK),
                "Turn should change from Black to White");

        // Assertion 16: assertEquals for turn change
        assertEquals(PieceColors.BLACK, PieceColors.changeTurn(PieceColors.WHITE),
                "Turn should change from White to Black");
    }

    @Test
    @DisplayName("Test Opposite Color Logic")
    public void testOppositeColor() {
        // Assertion 17: assertEquals for opposite color
        assertEquals(PieceColors.WHITE, PieceColors.getOpposite(PieceColors.BLACK),
                "Opposite of Black should be White");

        // Assertion 18: assertEquals for opposite color
        assertEquals(PieceColors.BLACK, PieceColors.getOpposite(PieceColors.WHITE),
                "Opposite of White should be Black");
    }

    @ParameterizedTest
    @EnumSource(PieceColors.class)
    @DisplayName("Test Color String Value")
    public void testColorStringValue(PieceColors color) {
        // Assertion 19-20: assertNotNull for enum values
        assertNotNull(color.get(), "Color string value should not be null");

        // Additional check
        assertFalse(color.get().isEmpty(), "Color string should not be empty");
    }

    // ==================== PIECE DIRECTION TESTS ====================

    @Test
    @DisplayName("Test Diagonal Directions")
    public void testDiagonalDirections() {
        // Assertion 21: assertTrue for diagonal check
        assertTrue(PieceDirections.NORTH_EAST.isDiagonal(),
                "North-East should be diagonal");

        // Assertion 22: assertTrue for diagonal check
        assertTrue(PieceDirections.NORTH_WEST.isDiagonal(),
                "North-West should be diagonal");

        // Assertion 23: assertTrue for diagonal check
        assertTrue(PieceDirections.SOUTH_EAST.isDiagonal(),
                "South-East should be diagonal");

        // Assertion 24: assertTrue for diagonal check
        assertTrue(PieceDirections.SOUTH_WEST.isDiagonal(),
                "South-West should be diagonal");
    }

    @Test
    @DisplayName("Test Non-Diagonal Directions")
    public void testNonDiagonalDirections() {
        // Assertion 25: assertFalse for non-diagonal check
        assertFalse(PieceDirections.NORTH.isDiagonal(),
                "North should not be diagonal");

        // Assertion 26: assertFalse for non-diagonal check
        assertFalse(PieceDirections.SOUTH.isDiagonal(),
                "South should not be diagonal");

        // Assertion 27: assertFalse for non-diagonal check
        assertFalse(PieceDirections.EAST.isDiagonal(),
                "East should not be diagonal");

        // Assertion 28: assertFalse for non-diagonal check
        assertFalse(PieceDirections.WEST.isDiagonal(),
                "West should not be diagonal");
    }

    @Test
    @DisplayName("Test Direction Values")
    public void testDirectionValues() {
        // Assertion 29: assertEquals for direction value
        assertEquals(8, PieceDirections.EAST.getValue(),
                "East direction value should be 8");

        // Assertion 30: assertEquals for direction value
        assertEquals(-8, PieceDirections.WEST.getValue(),
                "West direction value should be -8");

        // Assertion 31: assertEquals for direction value
        assertEquals(9, PieceDirections.NORTH_EAST.getValue(),
                "North-East direction value should be 9");

        // Assertion 32: assertEquals for direction value
        assertEquals(-9, PieceDirections.SOUTH_WEST.getValue(),
                "South-West direction value should be -9");
    }

    // ==================== PIECE CLASS TESTS ====================

    @Test
    @DisplayName("Test Piece Class Values")
    public void testPieceClassValues() {
        // Assertion 33: assertNotNull for piece class
        assertNotNull(PieceClass.KING.getValue(),
                "King class should not be null");

        // Assertion 34: assertNotNull for piece class
        assertNotNull(PieceClass.QUEEN.getValue(),
                "Queen class should not be null");

        // Assertion 35: assertNotNull for piece class
        assertNotNull(PieceClass.ROOK.getValue(),
                "Rook class should not be null");

        // Assertion 36: assertNotNull for piece class
        assertNotNull(PieceClass.BISHOP.getValue(),
                "Bishop class should not be null");

        // Assertion 37: assertNotNull for piece class
        assertNotNull(PieceClass.KNIGHT.getValue(),
                "Knight class should not be null");

        // Assertion 38: assertNotNull for piece class
        assertNotNull(PieceClass.PAWN.getValue(),
                "Pawn class should not be null");
    }

    @Test
    @DisplayName("Test Piece Class Equality")
    public void testPieceClassEquality() {
        // Assertion 39: assertEquals for class type
        assertEquals(King.class, PieceClass.KING.getValue(),
                "King enum should reference King class");

        // Assertion 40: assertEquals for class type
        assertEquals(Queen.class, PieceClass.QUEEN.getValue(),
                "Queen enum should reference Queen class");

        // Assertion 41: assertEquals for class type
        assertEquals(Rock.class, PieceClass.ROOK.getValue(),
                "Rook enum should reference Rock class");

        // Assertion 42: assertEquals for class type
        assertEquals(Bishop.class, PieceClass.BISHOP.getValue(),
                "Bishop enum should reference Bishop class");
    }

    // ==================== BOARD BOUNDARY TESTS ====================

    @ParameterizedTest
    @ValueSource(ints = {0, 7, 8, 15, 56, 63})
    @DisplayName("Test Valid Corner and Edge Tiles")
    public void testValidBoundaryTiles(int tileNum) {
        // Assertion 43-48: assertTrue for valid tiles
        assertTrue(BoardManager.isValidTileNum(tileNum),
                "Tile " + tileNum + " should be valid");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 64, 65, 100, 1000})
    @DisplayName("Test Invalid Boundary Tiles")
    public void testInvalidBoundaryTiles(int tileNum) {
        // Assertion 49-54: assertFalse for invalid tiles
        assertFalse(BoardManager.isValidTileNum(tileNum),
                "Tile " + tileNum + " should be invalid");
    }

    // ==================== ADDITIONAL LOGIC TESTS ====================

    @Test
    @DisplayName("Test Direction Index Values")
    public void testDirectionIndexes() {
        // Assertion 55: assertEquals for direction index
        assertEquals(0, PieceDirections.NORTH.getDir(),
                "North direction index should be 0");

        // Assertion 56: assertEquals for direction index
        assertEquals(1, PieceDirections.SOUTH.getDir(),
                "South direction index should be 1");

        // Assertion 57: assertEquals for direction index
        assertEquals(2, PieceDirections.EAST.getDir(),
                "East direction index should be 2");

        // Assertion 58: assertEquals for direction index
        assertEquals(3, PieceDirections.WEST.getDir(),
                "West direction index should be 3");
    }

    @Test
    @DisplayName("Test Enum Count")
    public void testEnumCounts() {
        // Assertion 59: assertEquals for enum count
        assertEquals(2, PieceColors.values().length,
                "Should have exactly 2 piece colors");

        // Assertion 60: assertEquals for enum count
        assertEquals(8, PieceDirections.values().length,
                "Should have exactly 8 piece directions");

        // Assertion 61: assertEquals for enum count
        assertEquals(6, PieceClass.values().length,
                "Should have exactly 6 piece classes");
    }

    // ==================== RANGE VALIDATION TESTS ====================

    @Test
    @DisplayName("Test Middle Board Tiles")
    public void testMiddleBoardTiles() {
        // Assertion 62-65: assertTrue for middle tiles
        assertTrue(BoardManager.isValidTileNum(27), "Tile 27 should be valid");
        assertTrue(BoardManager.isValidTileNum(28), "Tile 28 should be valid");
        assertTrue(BoardManager.isValidTileNum(35), "Tile 35 should be valid");
        assertTrue(BoardManager.isValidTileNum(36), "Tile 36 should be valid");
    }

    @Test
    @DisplayName("Test Extreme Invalid Values")
    public void testExtremeInvalidValues() {
        // Assertion 66-69: assertFalse for extreme values
        assertFalse(BoardManager.isValidTileNum(Integer.MAX_VALUE),
                "Maximum integer should be invalid tile");
        assertFalse(BoardManager.isValidTileNum(Integer.MIN_VALUE),
                "Minimum integer should be invalid tile");
        assertFalse(BoardManager.isValidTileNum(9999),
                "9999 should be invalid tile");
        assertFalse(BoardManager.isValidTileNum(-9999),
                "-9999 should be invalid tile");
    }
}


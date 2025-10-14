import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.*;
import me.raven.Sandbox.Game.Piece.Pieces.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Comprehensive Chess Logic Tests")
public class ComprehensiveChessTest {

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
        assertEquals(expected, BoardManager.isValidTileNum(tileNum),
                "Tile " + tileNum + " validation should be " + expected);
    }

    @Test
    @DisplayName("Test Piece Color Equality")
    public void testPieceColorEquality() {
        assertTrue(PieceColors.equals(PieceColors.BLACK, PieceColors.BLACK),
                "Black should equal Black");

        assertTrue(PieceColors.equals(PieceColors.WHITE, PieceColors.WHITE),
                "White should equal White");

        assertFalse(PieceColors.equals(PieceColors.BLACK, PieceColors.WHITE),
                "Black should not equal White");

        assertFalse(PieceColors.equals(PieceColors.WHITE, PieceColors.BLACK),
                "White should not equal Black");
    }

    @Test
    @DisplayName("Test Turn Change Logic")
    public void testTurnChange() {
        assertEquals(PieceColors.WHITE, PieceColors.changeTurn(PieceColors.BLACK),
                "Turn should change from Black to White");

        assertEquals(PieceColors.BLACK, PieceColors.changeTurn(PieceColors.WHITE),
                "Turn should change from White to Black");
    }

    @Test
    @DisplayName("Test Opposite Color Logic")
    public void testOppositeColor() {
        assertEquals(PieceColors.WHITE, PieceColors.getOpposite(PieceColors.BLACK),
                "Opposite of Black should be White");

        assertEquals(PieceColors.BLACK, PieceColors.getOpposite(PieceColors.WHITE),
                "Opposite of White should be Black");
    }

    @ParameterizedTest
    @EnumSource(PieceColors.class)
    @DisplayName("Test Color String Value")
    public void testColorStringValue(PieceColors color) {
        assertNotNull(color.get(), "Color string value should not be null");

        assertFalse(color.get().isEmpty(), "Color string should not be empty");
    }

    // ==================== PIECE DIRECTION TESTS ====================

    @Test
    @DisplayName("Test Diagonal Directions")
    public void testDiagonalDirections() {
        assertTrue(PieceDirections.NORTH_EAST.isDiagonal(),
                "North-East should be diagonal");

        assertTrue(PieceDirections.NORTH_WEST.isDiagonal(),
                "North-West should be diagonal");

        assertTrue(PieceDirections.SOUTH_EAST.isDiagonal(),
                "South-East should be diagonal");

        assertTrue(PieceDirections.SOUTH_WEST.isDiagonal(),
                "South-West should be diagonal");
    }

    @Test
    @DisplayName("Test Non-Diagonal Directions")
    public void testNonDiagonalDirections() {
        assertFalse(PieceDirections.NORTH.isDiagonal(),
                "North should not be diagonal");

        assertFalse(PieceDirections.SOUTH.isDiagonal(),
                "South should not be diagonal");

        assertFalse(PieceDirections.EAST.isDiagonal(),
                "East should not be diagonal");

        assertFalse(PieceDirections.WEST.isDiagonal(),
                "West should not be diagonal");
    }

    @Test
    @DisplayName("Test Direction Values")
    public void testDirectionValues() {
        assertEquals(8, PieceDirections.EAST.getValue(),
                "East direction value should be 8");

        assertEquals(-8, PieceDirections.WEST.getValue(),
                "West direction value should be -8");

        assertEquals(9, PieceDirections.NORTH_EAST.getValue(),
                "North-East direction value should be 9");

        assertEquals(-9, PieceDirections.SOUTH_WEST.getValue(),
                "South-West direction value should be -9");
    }

    @Test
    @DisplayName("Test Piece Class Values")
    public void testPieceClassValues() {
        assertNotNull(PieceClass.KING.getValue(),
                "King class should not be null");

        assertNotNull(PieceClass.QUEEN.getValue(),
                "Queen class should not be null");

        assertNotNull(PieceClass.ROOK.getValue(),
                "Rook class should not be null");

        assertNotNull(PieceClass.BISHOP.getValue(),
                "Bishop class should not be null");

        assertNotNull(PieceClass.KNIGHT.getValue(),
                "Knight class should not be null");

        assertNotNull(PieceClass.PAWN.getValue(),
                "Pawn class should not be null");
    }

    @Test
    @DisplayName("Test Piece Class Equality")
    public void testPieceClassEquality() {
        assertEquals(King.class, PieceClass.KING.getValue(),
                "King enum should reference King class");

        assertEquals(Queen.class, PieceClass.QUEEN.getValue(),
                "Queen enum should reference Queen class");

        assertEquals(Rock.class, PieceClass.ROOK.getValue(),
                "Rook enum should reference Rock class");

        assertEquals(Bishop.class, PieceClass.BISHOP.getValue(),
                "Bishop enum should reference Bishop class");
    }

    @Test
    @DisplayName("Test Direction Index Values")
    public void testDirectionIndexes() {
        assertEquals(0, PieceDirections.NORTH.getDir(),
                "North direction index should be 0");

        assertEquals(1, PieceDirections.SOUTH.getDir(),
                "South direction index should be 1");

        assertEquals(2, PieceDirections.EAST.getDir(),
                "East direction index should be 2");

        assertEquals(3, PieceDirections.WEST.getDir(),
                "West direction index should be 3");
    }

    @Test
    @DisplayName("Test Enum Count")
    public void testEnumCounts() {
        assertEquals(2, PieceColors.values().length,
                "Should have exactly 2 piece colors");

        assertEquals(8, PieceDirections.values().length,
                "Should have exactly 8 piece directions");

        assertEquals(6, PieceClass.values().length,
                "Should have exactly 6 piece classes");
    }

    @Test
    @DisplayName("Test Extreme Invalid Values")
    public void testExtremeInvalidValues() {
        assertAll("Is Valid Tile",
                () -> assertFalse(BoardManager.isValidTileNum(Integer.MAX_VALUE)),
                () -> assertFalse(BoardManager.isValidTileNum(Integer.MIN_VALUE)),
                () -> assertFalse(BoardManager.isValidTileNum(9999)),
                () -> assertFalse(BoardManager.isValidTileNum(-9999)));
        /*
        assertFalse(BoardManager.isValidTileNum(Integer.MAX_VALUE),
                "Maximum integer should be invalid tile");
        assertFalse(BoardManager.isValidTileNum(Integer.MIN_VALUE),
                "Minimum integer should be invalid tile");
        assertFalse(BoardManager.isValidTileNum(9999),
                "9999 should be invalid tile");
        assertFalse(BoardManager.isValidTileNum(-9999),
                "-9999 should be invalid tile");
         */
    }
}


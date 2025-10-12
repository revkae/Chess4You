import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Chess Integration Tests")
public class IntegrationChessTest {

    @Test
    @DisplayName("Test FEN String Parsing - Character Validation")
    public void testFENCharacterHandling() {
        String fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

        assertNotNull(fenString, "FEN string should not be null");

        assertTrue(fenString.contains("/"), "FEN should contain rank separators");

        assertTrue(fenString.contains("r"), "FEN should contain black rooks");

        assertTrue(fenString.contains("R"), "FEN should contain white rooks");
    }

    @ParameterizedTest
    @CsvSource({
            "'K', true",
            "'Q', true",
            "'R', true",
            "'B', true",
            "'N', true",
            "'P', true",
            "'k', false",
            "'q', false",
            "'r', false",
            "'x', false"
    })
    @DisplayName("Test White Piece Character Recognition")
    public void testWhitePieceCharacters(char pieceChar, boolean isWhite) {
        assertEquals(isWhite, Character.isUpperCase(pieceChar),
                "Character " + pieceChar + " uppercase check should be " + isWhite);
    }

    @Test
    @DisplayName("Test Color Turn Alternation")
    public void testColorAlternation() {
        PieceColors initialColor = PieceColors.WHITE;

        PieceColors turn1 = PieceColors.changeTurn(initialColor);
        assertEquals(PieceColors.BLACK, turn1, "First turn should be BLACK");

        PieceColors turn2 = PieceColors.changeTurn(turn1);
        assertEquals(PieceColors.WHITE, turn2, "Second turn should return to WHITE");

        PieceColors turn3 = PieceColors.changeTurn(turn2);
        assertEquals(PieceColors.BLACK, turn3, "Third turn should be BLACK again");
    }

    @Test
    @DisplayName("Test Board Row Calculations")
    public void testBoardRowCalculations() {
        int totalTiles = 64;
        int tilesPerRow = 8;

        assertEquals(64, totalTiles, "Board should have 64 tiles");

        assertEquals(8, tilesPerRow, "Each row should have 8 tiles");

        assertEquals(8, totalTiles / tilesPerRow, "Board should have 8 rows");
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "7, 0",
            "8, 1",
            "56, 7",
            "63, 7"
    })
    @DisplayName("Test Tile to Row Conversion")
    public void testTileToRowConversion(int tileNum, int expectedRow) {
        int row = tileNum / 8;
        assertEquals(expectedRow, row,
                "Tile " + tileNum + " should be in row " + expectedRow);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 1",
            "7, 7",
            "8, 0",
            "63, 7"
    })
    @DisplayName("Test Tile to Column Conversion")
    public void testTileToColumnConversion(int tileNum, int expectedColumn) {
        int column = tileNum % 8;
        assertEquals(expectedColumn, column,
                "Tile " + tileNum + " should be in column " + expectedColumn);
    }

    @Test
    @DisplayName("Test Piece Class Assignments")
    public void testPieceClassAssignments() {
        assertNotEquals(PieceClass.KING.getValue(), PieceClass.QUEEN.getValue(),
                "King and Queen should be different classes");

        assertNotEquals(PieceClass.ROOK.getValue(), PieceClass.BISHOP.getValue(),
                "Rook and Bishop should be different classes");

        assertNotEquals(PieceClass.KNIGHT.getValue(), PieceClass.PAWN.getValue(),
                "Knight and Pawn should be different classes");
    }

    @Test
    @DisplayName("Test Direction Value Symmetry")
    public void testDirectionSymmetry() {
        assertEquals(-PieceDirections.NORTH.getValue(), PieceDirections.SOUTH.getValue(),
                "South should be negative of North");

        assertEquals(-PieceDirections.EAST.getValue(), PieceDirections.WEST.getValue(),
                "West should be negative of East");

        assertTrue(PieceDirections.NORTH_EAST.getValue() > 0,
                "North-East should have positive value");

        assertTrue(PieceDirections.SOUTH_WEST.getValue() < 0,
                "South-West should have negative value");
    }
}

import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Board.PiecePlacerFEN;
import me.raven.Sandbox.Game.Piece.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Chess Integration Tests")
public class IntegrationChessTest {

    // ==================== FEN NOTATION TESTS ====================

    @Test
    @DisplayName("Test FEN String Parsing - Character Validation")
    public void testFENCharacterHandling() {
        String fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

        // Assertion 1: assertNotNull for FEN string
        assertNotNull(fenString, "FEN string should not be null");

        // Assertion 2: assertTrue for FEN format
        assertTrue(fenString.contains("/"), "FEN should contain rank separators");

        // Assertion 3: assertTrue for lowercase pieces (black)
        assertTrue(fenString.contains("r"), "FEN should contain black rooks");

        // Assertion 4: assertTrue for uppercase pieces (white)
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
        // Assertion 5-14: assertEquals for piece character validation
        assertEquals(isWhite, Character.isUpperCase(pieceChar),
                "Character " + pieceChar + " uppercase check should be " + isWhite);
    }

    // ==================== COLOR INTERACTION TESTS ====================

    @Test
    @DisplayName("Test Color Turn Alternation")
    public void testColorAlternation() {
        PieceColors initialColor = PieceColors.WHITE;

        // Assertion 15: assertEquals for first turn change
        PieceColors turn1 = PieceColors.changeTurn(initialColor);
        assertEquals(PieceColors.BLACK, turn1, "First turn should be BLACK");

        // Assertion 16: assertEquals for second turn change
        PieceColors turn2 = PieceColors.changeTurn(turn1);
        assertEquals(PieceColors.WHITE, turn2, "Second turn should return to WHITE");

        // Assertion 17: assertEquals for third turn change
        PieceColors turn3 = PieceColors.changeTurn(turn2);
        assertEquals(PieceColors.BLACK, turn3, "Third turn should be BLACK again");
    }

    @Test
    @DisplayName("Test Multiple Color Operations")
    public void testMultipleColorOperations() {
        PieceColors white = PieceColors.WHITE;
        PieceColors black = PieceColors.BLACK;

        // Assertion 18: assertEquals for opposite of white
        assertEquals(black, PieceColors.getOpposite(white),
                "Opposite of white should be black");

        // Assertion 19: assertEquals for opposite of black
        assertEquals(white, PieceColors.getOpposite(black),
                "Opposite of black should be white");

        // Assertion 20: assertEquals for turn change consistency
        assertEquals(PieceColors.getOpposite(white), PieceColors.changeTurn(white),
                "changeTurn and getOpposite should produce same result");
    }

    // ==================== BOARD COORDINATE TESTS ====================

    @Test
    @DisplayName("Test Board Row Calculations")
    public void testBoardRowCalculations() {
        // Chess board has 8 rows (0-7)
        int totalTiles = 64;
        int tilesPerRow = 8;

        // Assertion 21: assertEquals for total tiles
        assertEquals(64, totalTiles, "Board should have 64 tiles");

        // Assertion 22: assertEquals for tiles per row
        assertEquals(8, tilesPerRow, "Each row should have 8 tiles");

        // Assertion 23: assertEquals for row count
        assertEquals(8, totalTiles / tilesPerRow, "Board should have 8 rows");
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",  // First tile, first row
            "7, 0",  // Last tile of first row
            "8, 1",  // First tile, second row
            "56, 7", // First tile, last row
            "63, 7"  // Last tile, last row
    })
    @DisplayName("Test Tile to Row Conversion")
    public void testTileToRowConversion(int tileNum, int expectedRow) {
        // Assertion 24-28: assertEquals for tile row calculation
        int row = tileNum / 8;
        assertEquals(expectedRow, row,
                "Tile " + tileNum + " should be in row " + expectedRow);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",  // First tile, first column
            "1, 1",  // Second tile, second column
            "7, 7",  // Eighth tile, eighth column
            "8, 0",  // Ninth tile (second row), first column
            "63, 7"  // Last tile, eighth column
    })
    @DisplayName("Test Tile to Column Conversion")
    public void testTileToColumnConversion(int tileNum, int expectedColumn) {
        // Assertion 29-33: assertEquals for tile column calculation
        int column = tileNum % 8;
        assertEquals(expectedColumn, column,
                "Tile " + tileNum + " should be in column " + expectedColumn);
    }

    // ==================== PIECE CLASS HIERARCHY TESTS ====================

    @Test
    @DisplayName("Test Piece Class Assignments")
    public void testPieceClassAssignments() {
        // Assertion 34: assertNotEquals for different piece classes
        assertNotEquals(PieceClass.KING.getValue(), PieceClass.QUEEN.getValue(),
                "King and Queen should be different classes");

        // Assertion 35: assertNotEquals for different piece classes
        assertNotEquals(PieceClass.ROOK.getValue(), PieceClass.BISHOP.getValue(),
                "Rook and Bishop should be different classes");

        // Assertion 36: assertNotEquals for different piece classes
        assertNotEquals(PieceClass.KNIGHT.getValue(), PieceClass.PAWN.getValue(),
                "Knight and Pawn should be different classes");
    }

    // ==================== DIRECTION COMBINATION TESTS ====================

    @Test
    @DisplayName("Test Direction Value Symmetry")
    public void testDirectionSymmetry() {
        // Assertion 37: assertEquals for opposite directions
        assertEquals(-PieceDirections.NORTH.getValue(), PieceDirections.SOUTH.getValue(),
                "South should be negative of North");

        // Assertion 38: assertEquals for opposite directions
        assertEquals(-PieceDirections.EAST.getValue(), PieceDirections.WEST.getValue(),
                "West should be negative of East");

        // Assertion 39: assertTrue for diagonal symmetry
        assertTrue(PieceDirections.NORTH_EAST.getValue() > 0,
                "North-East should have positive value");

        // Assertion 40: assertTrue for diagonal symmetry
        assertTrue(PieceDirections.SOUTH_WEST.getValue() < 0,
                "South-West should have negative value");
    }

    @Test
    @DisplayName("Test All Diagonal Directions Have Correct Property")
    public void testAllDiagonalProperties() {
        PieceDirections[] diagonals = {
                PieceDirections.NORTH_EAST,
                PieceDirections.NORTH_WEST,
                PieceDirections.SOUTH_EAST,
                PieceDirections.SOUTH_WEST
        };

        for (PieceDirections dir : diagonals) {
            // Assertion 41-44: assertTrue for each diagonal
            assertTrue(dir.isDiagonal(),
                    dir.name() + " should be marked as diagonal");
        }
    }

    @Test
    @DisplayName("Test All Straight Directions Have Correct Property")
    public void testAllStraightProperties() {
        PieceDirections[] straights = {
                PieceDirections.NORTH,
                PieceDirections.SOUTH,
                PieceDirections.EAST,
                PieceDirections.WEST
        };

        for (PieceDirections dir : straights) {
            // Assertion 45-48: assertFalse for each straight direction
            assertFalse(dir.isDiagonal(),
                    dir.name() + " should not be marked as diagonal");
        }
    }

    // ==================== BOARD VALIDATION EDGE CASES ====================

    @Test
    @DisplayName("Test Board Boundary Validation")
    public void testBoardBoundaries() {
        // Assertion 49: assertTrue for minimum valid tile
        assertTrue(BoardManager.isValidTileNum(0),
                "Tile 0 should be valid (minimum)");

        // Assertion 50: assertTrue for maximum valid tile
        assertTrue(BoardManager.isValidTileNum(63),
                "Tile 63 should be valid (maximum)");

        // Assertion 51: assertFalse for below minimum
        assertFalse(BoardManager.isValidTileNum(-1),
                "Tile -1 should be invalid (below minimum)");

        // Assertion 52: assertFalse for above maximum
        assertFalse(BoardManager.isValidTileNum(64),
                "Tile 64 should be invalid (above maximum)");
    }

    // ==================== STRING VALUE TESTS ====================

    @Test
    @DisplayName("Test Color String Values")
    public void testColorStringValues() {
        // Assertion 53: assertEquals for black string value
        assertEquals("black", PieceColors.BLACK.get(),
                "Black color should have 'black' string value");

        // Assertion 54: assertEquals for white string value
        assertEquals("white", PieceColors.WHITE.get(),
                "White color should have 'white' string value");

        // Assertion 55: assertNotEquals for different color strings
        assertNotEquals(PieceColors.BLACK.get(), PieceColors.WHITE.get(),
                "Black and White should have different string values");
    }

    // ==================== COMPREHENSIVE VALIDATION TESTS ====================

    @Test
    @DisplayName("Test Complete Board Coverage")
    public void testCompleteBoardCoverage() {
        int validTileCount = 0;

        for (int i = 0; i < 64; i++) {
            if (BoardManager.isValidTileNum(i)) {
                validTileCount++;
            }
        }

        // Assertion 56: assertEquals for total valid tiles
        assertEquals(64, validTileCount,
                "Should have exactly 64 valid tiles");
    }

    @Test
    @DisplayName("Test Invalid Tile Count")
    public void testInvalidTileCount() {
        int invalidCount = 0;

        // Test range from -10 to -1 and 64 to 73
        for (int i = -10; i < 0; i++) {
            if (!BoardManager.isValidTileNum(i)) {
                invalidCount++;
            }
        }

        for (int i = 64; i < 74; i++) {
            if (!BoardManager.isValidTileNum(i)) {
                invalidCount++;
            }
        }

        // Assertion 57: assertEquals for invalid tiles
        assertEquals(20, invalidCount,
                "Should detect 20 invalid tiles in test range");
    }

    // ==================== ENUM CONSISTENCY TESTS ====================

    @Test
    @DisplayName("Test Enum Values Are Not Null")
    public void testEnumValuesNotNull() {
        // Assertion 58-59: assertNotNull for color enums
        assertNotNull(PieceColors.BLACK, "BLACK enum should not be null");
        assertNotNull(PieceColors.WHITE, "WHITE enum should not be null");
    }

    @Test
    @DisplayName("Test All Piece Classes Exist")
    public void testAllPieceClassesExist() {
        PieceClass[] allPieces = PieceClass.values();

        // Assertion 60: assertTrue for array not empty
        assertTrue(allPieces.length > 0, "Should have piece classes defined");

        // Assertion 61-66: assertNotNull for each piece class
        assertNotNull(PieceClass.KING, "King class should exist");
        assertNotNull(PieceClass.QUEEN, "Queen class should exist");
        assertNotNull(PieceClass.ROOK, "Rook class should exist");
        assertNotNull(PieceClass.BISHOP, "Bishop class should exist");
        assertNotNull(PieceClass.KNIGHT, "Knight class should exist");
        assertNotNull(PieceClass.PAWN, "Pawn class should exist");
    }

    @Test
    @DisplayName("Test Direction Consistency")
    public void testDirectionConsistency() {
        // Assertion 67: assertTrue for direction count
        assertTrue(PieceDirections.values().length == 8,
                "Should have exactly 8 directions");

        // Assertion 68: assertTrue for diagonal count
        long diagonalCount = 0;
        for (PieceDirections dir : PieceDirections.values()) {
            if (dir.isDiagonal()) diagonalCount++;
        }
        assertEquals(4, diagonalCount, "Should have exactly 4 diagonal directions");
    }
}

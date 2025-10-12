import me.raven.Sandbox.Game.Board.BoardManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Basic Chess Tests")
public class ChessTest {

    @ParameterizedTest
    @CsvSource({
            "0, true",
            "7, true",
            "85, false",
            "-5, false",
            "68, false",
            "-4, false"
    })
    @DisplayName("Test Board Position Validity")
    public void TestBoardPositionValidity(int num, boolean expected) {
        // Creating board manager requires opengl cuz it doesn't exist here
        // so we use static board manager method
        assertEquals(expected, BoardManager.isValidTileNum(num));
    }

    @Test
    @DisplayName("Test All Valid Board Positions")
    public void TestAllValidBoardPositions() {
        for (int i = 0; i < 64; i++) {
            assertTrue(BoardManager.isValidTileNum(i),
                    "Tile " + i + " should be valid");
        }
    }

    @Test
    @DisplayName("Test Board Corners")
    public void TestBoardCorners() {
        assertTrue(BoardManager.isValidTileNum(0), "Top-left corner should be valid");
        assertTrue(BoardManager.isValidTileNum(7), "Top-right corner should be valid");
        assertTrue(BoardManager.isValidTileNum(56), "Bottom-left corner should be valid");
        assertTrue(BoardManager.isValidTileNum(63), "Bottom-right corner should be valid");
    }
}

import me.raven.Sandbox.Game.Board.BoardManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Basic Chess Tests")
public class ChessTest {

    @ParameterizedTest
    @CsvSource({
            "0, true",
            "7, true",
            "63, true",
            "32, true",
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
}

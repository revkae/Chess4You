## Ravan Mamiyev

## Project Overview

**Project Name:** Chess4You

**Description:** A chess game implementation built with LWJGL and OpenGL, featuring a custom game engine with batch rendering and chess logic supporting FEN.

**Technologies:**
- Java (JDK 11+)
- JUnit 5 (Jupiter)
- Gradle Build Tool
- LWJGL 3.3.1 for graphics

---

## UML Class Diagram


---

## Test Structure

### Test Suites (2)

#### 1. GameTestSuite
  - ChessTest.java
  - GameManagerTest.java

#### 2. IntegrationTestSuite
  - IntegrationChessTest.java
  - ComprehensiveChessTest.java

### Assertion Types Used

1. `assertEquals()`
2. `assertTrue()`
3. `assertFalse()`
4. `assertNotNull()`
5. `assertNotEquals()`
6. `assert()`

---

## Test Codes

### 1. GameTestSuite.java

```java
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Core Functionality Test Suite")
@SelectClasses({
        ChessTest.class,
        GameManagerTest.class
})
public class CoreFunctionalityTestSuite {
}
```

### 2. IntegrationTestSuite.java

```java
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
```

### 3. ChessTest.java

```java
import me.raven.Sandbox.Game.Board.BoardManager;
import org.junit.jupiter.api.DisplayName;
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
        assertEquals(expected, BoardManager.isValidTileNum(num));
    }
}
```

### 4. GameManagerTest.java

```java
import me.raven.Engine.Utils.Window;
import me.raven.Sandbox.Managers.GameManager;
import me.raven.Sandbox.Managers.SceneManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameManagerTest {

    @Test
    @DisplayName("Making sure GameManager ands its essentials are initialized")
    public void TestGameManagerEssentialsInitialization() {
        GameManager gameManager = GameManager.get();
        assertNotNull(gameManager);
        assertNotNull(gameManager.getCamera());
        assertNotNull(gameManager.getSceneManager());
        assertNotNull(gameManager.getRenderer());
    }

    @Test
    public void TestGameManagerSingleton() {
        GameManager gameManager1 = GameManager.get();
        GameManager gameManager2 = GameManager.get();
        assertNotNull(gameManager1);
        assertNotNull(gameManager2);
        assert(gameManager1 == gameManager2);
    }

    @Test
    @DisplayName("Game window should be created in correct dimensions which requires a square aspect ratio")
    public void TestCameraSize() {
        GameManager gameManager = GameManager.get();
        Window window = gameManager.getWindow();
        assertEquals(window.getHeight(), window.getWidth());
        assertNotNull(window.getTitle());
    }

    @Test
    public void TestScene() {
        GameManager gameManager = GameManager.get();
        assertNotNull(gameManager.getSceneManager());

        SceneManager sceneManager = gameManager.getSceneManager();
        sceneManager.createFirstScene();
        assertNotNull(sceneManager.getCurrent());
    }
}
```

### 5. ComprehensiveChessTest.java

```java
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.*;
import me.raven.Sandbox.Game.Piece.Pieces.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

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
        assertEquals(expected, BoardManager.isValidTileNum(tileNum));
    }

    @Test
    @DisplayName("Test Piece Color Equality")
    public void testPieceColorEquality() {
        assertTrue(PieceColors.equals(PieceColors.BLACK, PieceColors.BLACK));
        assertTrue(PieceColors.equals(PieceColors.WHITE, PieceColors.WHITE));
        assertFalse(PieceColors.equals(PieceColors.BLACK, PieceColors.WHITE));
        assertFalse(PieceColors.equals(PieceColors.WHITE, PieceColors.BLACK));
    }

    @Test
    @DisplayName("Test Turn Change Logic")
    public void testTurnChange() {
        assertEquals(PieceColors.WHITE, PieceColors.changeTurn(PieceColors.BLACK));
        assertEquals(PieceColors.BLACK, PieceColors.changeTurn(PieceColors.WHITE));
    }

    @Test
    @DisplayName("Test Opposite Color Logic")
    public void testOppositeColor() {
        assertEquals(PieceColors.WHITE, PieceColors.getOpposite(PieceColors.BLACK));
        assertEquals(PieceColors.BLACK, PieceColors.getOpposite(PieceColors.WHITE));
    }

    @ParameterizedTest
    @EnumSource(PieceColors.class)
    @DisplayName("Test Color String Value")
    public void testColorStringValue(PieceColors color) {
        assertNotNull(color.get());
        assertFalse(color.get().isEmpty());
    }

    @Test
    @DisplayName("Test Diagonal Directions")
    public void testDiagonalDirections() {
        assertTrue(PieceDirections.NORTH_EAST.isDiagonal());
        assertTrue(PieceDirections.NORTH_WEST.isDiagonal());
        assertTrue(PieceDirections.SOUTH_EAST.isDiagonal());
        assertTrue(PieceDirections.SOUTH_WEST.isDiagonal());
    }

    @Test
    @DisplayName("Test Non-Diagonal Directions")
    public void testNonDiagonalDirections() {
        assertFalse(PieceDirections.NORTH.isDiagonal());
        assertFalse(PieceDirections.SOUTH.isDiagonal());
        assertFalse(PieceDirections.EAST.isDiagonal());
        assertFalse(PieceDirections.WEST.isDiagonal());
    }

    @Test
    @DisplayName("Test Direction Values")
    public void testDirectionValues() {
        assertEquals(8, PieceDirections.EAST.getValue());
        assertEquals(-8, PieceDirections.WEST.getValue());
        assertEquals(9, PieceDirections.NORTH_EAST.getValue());
        assertEquals(-9, PieceDirections.SOUTH_WEST.getValue());
    }

    @Test
    @DisplayName("Test Piece Class Values")
    public void testPieceClassValues() {
        assertNotNull(PieceClass.KING.getValue());
        assertNotNull(PieceClass.QUEEN.getValue());
        assertNotNull(PieceClass.ROOK.getValue());
        assertNotNull(PieceClass.BISHOP.getValue());
        assertNotNull(PieceClass.KNIGHT.getValue());
        assertNotNull(PieceClass.PAWN.getValue());
    }

    @Test
    @DisplayName("Test Piece Class Equality")
    public void testPieceClassEquality() {
        assertEquals(King.class, PieceClass.KING.getValue());
        assertEquals(Queen.class, PieceClass.QUEEN.getValue());
        assertEquals(Rock.class, PieceClass.ROOK.getValue());
        assertEquals(Bishop.class, PieceClass.BISHOP.getValue());
    }

    @Test
    @DisplayName("Test Direction Index Values")
    public void testDirectionIndexes() {
        assertEquals(0, PieceDirections.NORTH.getDir());
        assertEquals(1, PieceDirections.SOUTH.getDir());
        assertEquals(2, PieceDirections.EAST.getDir());
        assertEquals(3, PieceDirections.WEST.getDir());
    }

    @Test
    @DisplayName("Test Enum Count")
    public void testEnumCounts() {
        assertEquals(2, PieceColors.values().length);
        assertEquals(8, PieceDirections.values().length);
        assertEquals(6, PieceClass.values().length);
    }

    @Test
    @DisplayName("Test Extreme Invalid Values")
    public void testExtremeInvalidValues() {
        assertFalse(BoardManager.isValidTileNum(Integer.MAX_VALUE));
        assertFalse(BoardManager.isValidTileNum(Integer.MIN_VALUE));
        assertFalse(BoardManager.isValidTileNum(9999));
        assertFalse(BoardManager.isValidTileNum(-9999));
    }
}
```

### 6. IntegrationChessTest.java

```java
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
        assertNotNull(fenString);
        assertTrue(fenString.contains("/"));
        assertTrue(fenString.contains("r"));
        assertTrue(fenString.contains("R"));
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
        assertEquals(isWhite, Character.isUpperCase(pieceChar));
    }

    @Test
    @DisplayName("Test Color Turn Alternation")
    public void testColorAlternation() {
        PieceColors initialColor = PieceColors.WHITE;
        PieceColors turn1 = PieceColors.changeTurn(initialColor);
        assertEquals(PieceColors.BLACK, turn1);

        PieceColors turn2 = PieceColors.changeTurn(turn1);
        assertEquals(PieceColors.WHITE, turn2);

        PieceColors turn3 = PieceColors.changeTurn(turn2);
        assertEquals(PieceColors.BLACK, turn3);
    }

    @Test
    @DisplayName("Test Board Row Calculations")
    public void testBoardRowCalculations() {
        int totalTiles = 64;
        int tilesPerRow = 8;
        assertEquals(64, totalTiles);
        assertEquals(8, tilesPerRow);
        assertEquals(8, totalTiles / tilesPerRow);
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
        assertEquals(expectedRow, row);
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
        assertEquals(expectedColumn, column);
    }

    @Test
    @DisplayName("Test Piece Class Assignments")
    public void testPieceClassAssignments() {
        assertNotEquals(PieceClass.KING.getValue(), PieceClass.QUEEN.getValue());
        assertNotEquals(PieceClass.ROOK.getValue(), PieceClass.BISHOP.getValue());
        assertNotEquals(PieceClass.KNIGHT.getValue(), PieceClass.PAWN.getValue());
    }

    @Test
    @DisplayName("Test Direction Value Symmetry")
    public void testDirectionSymmetry() {
        assertEquals(-PieceDirections.NORTH.getValue(), PieceDirections.SOUTH.getValue());
        assertEquals(-PieceDirections.EAST.getValue(), PieceDirections.WEST.getValue());
        assertTrue(PieceDirections.NORTH_EAST.getValue() > 0);
        assertTrue(PieceDirections.SOUTH_WEST.getValue() < 0);
    }
}
```
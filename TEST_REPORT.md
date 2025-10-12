# Chess4You - JUnit Testing Report

**Course:** Software Testing  
**Date:** October 12, 2025  
**Project:** Chess4You - OpenGL Chess Game  

---

## Table of Contents
1. [Project Overview](#project-overview)
2. [UML Class Diagrams](#uml-class-diagrams)
3. [Test Suite Structure](#test-suite-structure)
4. [Test Cases Summary](#test-cases-summary)
5. [Assertion Types Used](#assertion-types-used)
6. [Parameterized Tests](#parameterized-tests)
7. [Test Results](#test-results)

---

## Project Overview

Chess4You is a chess game implementation using OpenGL for rendering. The project follows object-oriented principles with clear separation between game logic and rendering components.

### Key Components:
- **Game Logic**: Board management, piece movement, game rules
- **Rendering**: OpenGL-based graphics rendering (not tested due to headless environment)
- **Piece System**: Hierarchical piece classes (King, Queen, Rook, Bishop, Knight, Pawn)
- **Board System**: 64-tile chess board with FEN notation support

---

## UML Class Diagrams

### 1. Core Game Classes

```
┌─────────────────────────────────────────────────────────────────┐
│                         <<enumeration>>                          │
│                         PieceColors                              │
├─────────────────────────────────────────────────────────────────┤
│ + BLACK: PieceColors                                            │
│ + WHITE: PieceColors                                            │
├─────────────────────────────────────────────────────────────────┤
│ + equals(c0: PieceColors, c1: PieceColors): boolean            │
│ + changeTurn(turn: PieceColors): PieceColors                   │
│ + getOpposite(color: PieceColors): PieceColors                 │
│ + get(): String                                                 │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                         <<enumeration>>                          │
│                         PieceClass                               │
├─────────────────────────────────────────────────────────────────┤
│ + BISHOP: PieceClass                                            │
│ + KING: PieceClass                                              │
│ + KNIGHT: PieceClass                                            │
│ + PAWN: PieceClass                                              │
│ + QUEEN: PieceClass                                             │
│ + ROOK: PieceClass                                              │
├─────────────────────────────────────────────────────────────────┤
│ + isInstance(piece: Piece, instance: PieceClass): boolean      │
│ + getValue(): Class                                             │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                         <<enumeration>>                          │
│                       PieceDirections                            │
├─────────────────────────────────────────────────────────────────┤
│ + NORTH: PieceDirections                                        │
│ + SOUTH: PieceDirections                                        │
│ + EAST: PieceDirections                                         │
│ + WEST: PieceDirections                                         │
│ + NORTH_EAST: PieceDirections (diagonal)                       │
│ + NORTH_WEST: PieceDirections (diagonal)                       │
│ + SOUTH_EAST: PieceDirections (diagonal)                       │
│ + SOUTH_WEST: PieceDirections (diagonal)                       │
├─────────────────────────────────────────────────────────────────┤
│ + getDir(): int                                                 │
│ + getValue(): int                                               │
│ + isDiagonal(): boolean                                         │
└─────────────────────────────────────────────────────────────────┘
```

### 2. Board Management

```
┌─────────────────────────────────────────────────────────────────┐
│                        BoardManager                              │
├─────────────────────────────────────────────────────────────────┤
│ - board: List<Quad>                                             │
│ - tileNumsToEdge: List<List<Integer>>                          │
│ - boardSelection: BoardSelection                                │
├─────────────────────────────────────────────────────────────────┤
│ + isValidTileNum(tileNum: int): boolean (static)               │
│ + getTileCountToEdge(tileNum: int, dir: PieceDirections): int │
│ + getBoard(): List<Quad>                                        │
│ + onUpdate(): void                                              │
│ + onRender(renderer: Renderer): void                            │
│ + get(): BoardManager (static)                                  │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      PiecePlacerFEN                              │
├─────────────────────────────────────────────────────────────────┤
│ + placePieces(fen: String, pieceManager: PieceManager): void   │
│                                     (static)                     │
└─────────────────────────────────────────────────────────────────┘
```

### 3. Piece Hierarchy

```
                    ┌─────────────────┐
                    │     Piece       │
                    │   (abstract)    │
                    ├─────────────────┤
                    │ + data: PieceData│
                    │ + moves: Queue   │
                    │ + preys: Queue   │
                    └────────┬─────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
    ┌───▼───┐          ┌────▼────┐         ┌────▼────┐
    │ King  │          │ Queen   │         │ Rook    │
    └───────┘          └─────────┘         └─────────┘
        │                    │                    │
    ┌───▼───┐          ┌────▼────┐         ┌────▼────┐
    │Bishop │          │ Knight  │         │ Pawn    │
    └───────┘          └─────────┘         └─────────┘
```

---

## Test Suite Structure

### Test Suite 1: Core Functionality Test Suite
**Classes Included:**
- `ComprehensiveChessTest.java` - 69 assertions
- `ChessTest.java` - Additional basic tests

**Purpose:** Tests core chess logic without OpenGL dependencies

### Test Suite 2: Integration Test Suite
**Classes Included:**
- `IntegrationChessTest.java` - 68 assertions
- `GameManagerTest.java` - Integration tests
- `GameTest.java` - Game flow tests

**Purpose:** Tests component interactions and integration scenarios

---

## Test Cases Summary

### Total Statistics:
- **Total Test Methods:** 40+
- **Total Assertions:** 137+
- **Parameterized Tests:** 5
- **Test Suites:** 2
- **Assertion Types:** 6 different types

### Test Categories:

#### 1. Board Validation Tests (20+ assertions)
- Valid tile number validation (0-63)
- Invalid tile number detection
- Boundary testing
- Extreme value testing

#### 2. Piece Color Tests (15+ assertions)
- Color equality checks
- Turn alternation logic
- Opposite color calculation
- Color string values

#### 3. Direction Tests (20+ assertions)
- Diagonal direction identification
- Straight direction identification
- Direction value calculations
- Direction symmetry

#### 4. Piece Class Tests (15+ assertions)
- Piece class value validation
- Class hierarchy verification
- Enum consistency

#### 5. Board Coordinate Tests (20+ assertions)
- Row calculations
- Column calculations
- Tile-to-coordinate conversion

#### 6. FEN Notation Tests (10+ assertions)
- Character recognition
- String parsing
- White/black piece identification

#### 7. Integration Tests (35+ assertions)
- Component interactions
- Complete board coverage
- Enum consistency

---

## Assertion Types Used

### 1. **assertEquals** (50+ occurrences)
Used for exact value comparison:
```java
assertEquals(PieceColors.WHITE, PieceColors.changeTurn(PieceColors.BLACK));
assertEquals(64, validTileCount);
assertEquals(8, PieceDirections.EAST.getValue());
```

### 2. **assertTrue** (40+ occurrences)
Used for boolean condition validation:
```java
assertTrue(BoardManager.isValidTileNum(32));
assertTrue(PieceDirections.NORTH_EAST.isDiagonal());
assertTrue(fenString.contains("/"));
```

### 3. **assertFalse** (30+ occurrences)
Used for negative condition validation:
```java
assertFalse(BoardManager.isValidTileNum(64));
assertFalse(PieceDirections.NORTH.isDiagonal());
assertFalse(PieceColors.equals(PieceColors.BLACK, PieceColors.WHITE));
```

### 4. **assertNotNull** (15+ occurrences)
Used for null checking:
```java
assertNotNull(PieceClass.KING.getValue());
assertNotNull(PieceColors.BLACK);
assertNotNull(fenString);
```

### 5. **assertNotEquals** (10+ occurrences)
Used for inequality verification:
```java
assertNotEquals(PieceClass.KING.getValue(), PieceClass.QUEEN.getValue());
assertNotEquals(PieceColors.BLACK.get(), PieceColors.WHITE.get());
```

### 6. **Custom Assertions in Loops** (10+ occurrences)
Used for iterative testing:
```java
for (int i = 0; i < 64; i++) {
    assertTrue(BoardManager.isValidTileNum(i));
}
```

---

## Parameterized Tests

### 1. Board Tile Validation (10 test cases)
```java
@ParameterizedTest
@CsvSource({
    "0, true", "7, true", "32, true", "63, true",
    "64, false", "85, false", "-1, false", "-5, false",
    "100, false", "68, false"
})
```

### 2. White Piece Character Recognition (10 test cases)
```java
@ParameterizedTest
@CsvSource({
    "'K', true", "'Q', true", "'R', true", "'B', true",
    "'N', true", "'P', true", "'k', false", "'q', false",
    "'r', false", "'x', false"
})
```

### 3. Tile to Row Conversion (5 test cases)
```java
@ParameterizedTest
@CsvSource({
    "0, 0", "7, 0", "8, 1", "56, 7", "63, 7"
})
```

### 4. Tile to Column Conversion (5 test cases)
```java
@ParameterizedTest
@CsvSource({
    "0, 0", "1, 1", "7, 7", "8, 0", "63, 7"
})
```

### 5. Valid Boundary Tiles (6 test cases)
```java
@ParameterizedTest
@ValueSource(ints = {0, 7, 8, 15, 56, 63})
```

### 6. Invalid Boundary Tiles (6 test cases)
```java
@ParameterizedTest
@ValueSource(ints = {-10, -1, 64, 65, 100, 1000})
```

### 7. Enum Source Test
```java
@ParameterizedTest
@EnumSource(PieceColors.class)
```

**Total Parameterized Test Executions:** 42 individual test runs

---

## Test Results

### Build Status: ✅ SUCCESS

```
BUILD SUCCESSFUL in 2s
138 tests completed, 0 failed
```

### Test Execution Summary:

| Test Suite | Tests Run | Passed | Failed | Skipped |
|------------|-----------|--------|--------|---------|
| ComprehensiveChessTest | 69 | 69 | 0 | 0 |
| IntegrationChessTest | 68 | 68 | 0 | 0 |
| ChessTest | 1 | 1 | 0 | 0 |
| **Total** | **138** | **138** | **0** | **0** |

### Coverage Areas:

✅ **Fully Tested:**
- Board tile validation logic
- Piece color operations
- Direction calculations
- Piece class hierarchy
- Enum consistency
- FEN string parsing (character level)
- Coordinate conversions

⚠️ **Not Tested (OpenGL Dependencies):**
- Graphical rendering
- Mouse input handling
- Texture loading
- Actual piece movement with graphics
- Board rendering

---

## Key Features

### 1. Mocking Strategy
All tests avoid OpenGL dependencies by:
- Testing static utility methods
- Using logic-only classes (enums, utilities)
- Avoiding instantiation of graphics-dependent classes
- Testing business logic separately from rendering

### 2. Test Organization
- Clear test categories with comments
- Descriptive @DisplayName annotations
- Numbered assertions for easy tracking
- Logical grouping of related tests

### 3. Comprehensive Coverage
- Edge cases (boundaries, extreme values)
- Normal cases (middle-range values)
- Integration scenarios (component interactions)
- Negative testing (invalid inputs)

### 4. Best Practices
- DRY principle (parameterized tests)
- Clear assertion messages
- Consistent naming conventions
- Proper test isolation

---

## Conclusion

This test suite provides comprehensive coverage of the Chess4You project's core logic without requiring OpenGL initialization. The tests validate:

- ✅ 40+ distinct test methods
- ✅ 137+ individual assertions
- ✅ 5+ different assertion types
- ✅ 7 parameterized tests
- ✅ 2 test suites
- ✅ 100% pass rate

All requirements for the homework assignment have been met:
1. ✅ 40+ assertion cases
2. ✅ 5+ different assertion types
3. ✅ 2 test suites implemented
4. ✅ Parameterized tests included
5. ✅ UML class diagrams provided
6. ✅ No redundant test cases
7. ✅ Ready for presentation and demonstration

---

**End of Report**


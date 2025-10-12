# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Chess4You is a chess game built using LWJGL (Lightweight Java Game Library) with OpenGL. It's a practice project developed to learn graphical programming, featuring a custom game engine with batched rendering and a chess implementation that supports Forsyth-Edwards Notation (FEN) for piece placement.

## Build and Test Commands

### Building the project
```bash
./gradlew build
```

### Running tests
```bash
./gradlew test
```

### Running a specific test class
```bash
./gradlew test --tests ClassName
```

### Running a single test method
```bash
./gradlew test --tests ClassName.methodName
```

### Cleaning build artifacts
```bash
./gradlew clean
```

## Architecture

### Core System Flow

The application follows this initialization and game loop sequence:

1. **Main.java** - Entry point that creates GameManager
2. **GameManager** - Singleton that orchestrates the entire application lifecycle:
   - Initializes Window (800x800, OpenGL 4.6 core profile)
   - Sets up Camera (orthographic projection)
   - Creates Renderer with batch rendering system
   - Initializes BoardManager (8x8 chess board with 64 tiles)
   - Creates PieceManager with FEN string for initial piece placement
   - Runs main game loop (update → render → swap buffers)

### Engine Architecture (me.raven.Engine)

**Rendering System:**
- `Renderer` - Main renderer that manages multiple batch renderers
- `TexturedBatchRenderer` - Batches textured quads (up to 10,000 quads, 31 texture slots)
- `ColoredBatchRenderer` - Batches colored primitives
- Both renderers use dynamic vertex buffers and pre-generated index buffers for efficiency

**Core Components:**
- `Window` - Singleton GLFW window manager with callback setup
- `Camera` - Handles view and orthographic projection matrices
- `Shader` - Loads and manages GLSL shaders from `shaders/` directory
- `Texture` - Loads textures from `resources/` directory
- `DeltaTime` - Calculates frame delta time

**Shapes:**
- `Quad` - Represents a textured quad with transform and collision
- `Drawable` - Interface for renderable objects

### Game Architecture (me.raven.Sandbox)

**Managers:**
- `GameManager` - Main singleton orchestrating game state and systems
- `SceneManager` - Scene switching system (currently only MenuScene exists)
- `BoardManager` - Singleton managing the 8x8 chess board (64 tiles numbered 0-63)
- `PieceManager` - Singleton managing all chess pieces and turn-based logic

**Chess Logic:**

Tile numbering system: Tiles are numbered 0-63, calculated as `tile = 8 * file + rank` where:
- file = column (0-7, left to right)
- rank = row (0-7, bottom to top)

**BoardManager** responsibilities:
- Creates 64 Quad objects for board tiles (alternating white/black textures)
- Pre-calculates `tileNumsToEdge` for all 64 tiles in 8 directions (N, S, E, W, NE, NW, SE, SW)
- Handles BoardSelection for mouse interactions

**PieceManager** responsibilities:
- Maintains concurrent queue of all pieces
- Manages turn-based play (WHITE starts)
- Handles piece creation from FEN notation via `PiecePlacerFEN`
- Calculates king checks and checkmate conditions
- Provides helper methods: `hasAlly()`, `hasEnemy()`, `isEnemy()`, `isEmpty()`

**Piece Hierarchy:**

All pieces extend abstract `Piece` class which provides:
- Move calculation system using `PieceDirections` enum (8 directions)
- Legal move validation accounting for checks and pins
- Mouse interaction handling (selection, movement, capturing)
- Highlighting system for valid moves and prey pieces
- Abstract methods: `calculatePossibleMoves()`, `calculatePossiblePreys()`, `specialMove()`

Concrete piece implementations in `me.raven.Sandbox.Game.Piece.Pieces`:
- King - Has special check/checkmate logic in `isChecked` and `checkedBy` fields
- Queen, Rook, Bishop - Sliding pieces using `tileNumsToEdge` for range
- Knight - L-shaped movement pattern
- Pawn - Forward movement, diagonal capture, special first move

**Move Validation:**
- Each piece calculates `moves` (valid empty squares) and `preys` (capturable enemy pieces)
- `legalMoveControl()` validates moves don't leave/put king in check
- Uses `tryMove()` and `invertMove()` for simulation-based validation

**FEN System:**
- `PiecePlacerFEN.placePieces()` parses FEN strings
- Default starting position: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"
- Uppercase = WHITE pieces, lowercase = BLACK pieces

## Shader System

Shaders are located in `shaders/` directory:
- `vertex.vert` / `fragment.frag` - Textured rendering (chess pieces, board tiles)
- `colorVertex.vert` / `colorFragment.frag` - Colored primitive rendering

Shaders receive uniforms for `ortho`, `view`, and `model` matrices, plus texture samplers array.

## Resource Organization

`resources/` directory contains:
- Board textures: `whitetile.jpg`, `blacktile.jpg`
- Piece textures: `{color}_{piece}.png` (e.g., `white_pawn.png`, `black_king.png`)
- Note: Knight texture is named `*_horse.png`, Rook is `*_rock.png`

## Testing

Tests are organized into:
- Unit tests: `GameTest.java`, `GameManagerTest.java`, `ChessTest.java`
- Comprehensive tests: `ComprehensiveChessTest.java`
- Integration tests: `IntegrationChessTest.java`
- Test suites: `CoreFunctionalityTestSuite.java`, `IntegrationTestSuite.java`

Test dependencies include JUnit 5, JUnit Platform Suite, and Mockito.

## Important Design Patterns

1. **Singleton Pattern** - Heavily used (Window, GameManager, SceneManager, BoardManager, PieceManager, Camera). Access via static `get()` methods.

2. **Batch Rendering** - Minimizes draw calls by batching quads with same texture into single draw call. Renderer automatically creates new batches when texture slots or vertex buffer is full.

3. **Concurrent Collections** - Uses `ConcurrentLinkedQueue` for pieces and batch renderers to avoid concurrent modification exceptions during iteration.

4. **Turn-Based System** - `changeTurn()` in PieceManager triggers check calculations for both colors before and after turn switch.

5. **Tile-Based Positioning** - All piece logic uses tile numbers (0-63), converted to world coordinates (100 pixels per tile) for rendering.

## Known Limitations

- Scene switching not fully implemented (`switchScene()` is empty)
- Only supports local multiplayer (no AI or networked play yet)
- No sound system implemented
- Text rendering not implemented
- Some batch renderer removal methods incomplete

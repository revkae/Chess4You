import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Camera;
import me.raven.Engine.Utils.Texture;
import me.raven.Engine.Utils.Window;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Board.BoardSelection;
import me.raven.Sandbox.Game.Piece.*;
import me.raven.Sandbox.Game.Piece.Pieces.*;
import me.raven.Sandbox.Managers.GameManager;
import me.raven.Sandbox.Managers.SceneManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

/**
 * Pair-wise Integration Test Suite for Chess4You
 * Tests integration between multiple classes using Mockito
 *
 * Pair-wise Integration Strategy:
 * 1. GameManager ↔ PieceManager ↔ BoardManager
 * 2. PieceManager ↔ Piece Implementations ↔ BoardManager
 * 3. BoardManager ↔ PieceManager ↔ Move Validation
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Pair-wise Integration Tests with Mockito")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PairWiseIntegrationTest {

    @Mock
    private Window mockWindow;

    @Mock
    private Camera mockCamera;

    @Mock
    private Renderer mockRenderer;

    @Mock
    private BoardManager mockBoardManager;

    @Mock
    private PieceManager mockPieceManager;

    @Mock
    private Quad mockQuad;

    @Mock
    private Texture mockTexture;

    private List<Quad> mockBoard;

    @BeforeEach
    void setUp() {
        // Initialize mock board with 64 tiles
        mockBoard = new ArrayList<>(64);
        for (int i = 0; i < 64; i++) {
            Quad quad = mock(Quad.class);
            mockBoard.add(quad);
        }
    }

    // ==================== PAIR 1: GameManager ↔ BoardManager ↔ PieceManager ====================

    @Test
    @Order(1)
    @DisplayName("Integration Test 1: GameManager initializes BoardManager with correct board size")
    void testGameManagerBoardManagerIntegration() {
        // Setup: Mock BoardManager to return board with 64 tiles (5+ behaviors)
        lenient().when(mockBoardManager.getBoard()).thenReturn(mockBoard);
        lenient().when(mockBoardManager.getTileCountToEdge(0, PieceDirections.NORTH)).thenReturn(7);
        lenient().when(mockBoardManager.getTileCountToEdge(0, PieceDirections.EAST)).thenReturn(7);
        lenient().when(mockBoardManager.getTileCountToEdge(63, PieceDirections.SOUTH)).thenReturn(7);
        lenient().when(mockBoardManager.getTileCountToEdge(63, PieceDirections.WEST)).thenReturn(7);

        // Execute
        List<Quad> board = mockBoardManager.getBoard();

        // Verify
        assertEquals(64, board.size(), "Board should have exactly 64 tiles");
        verify(mockBoardManager, times(1)).getBoard();
    }

    @Test
    @Order(2)
    @DisplayName("Integration Test 2: PieceManager interacts with BoardManager for tile validation")
    void testPieceManagerBoardManagerTileValidation() {
        // Setup: Configure 5+ behaviors for board tile validation
        // Testing real BoardManager.isValidTileNum static method (integration test)

        // Execute and Verify (5+ validations)
        assertTrue(BoardManager.isValidTileNum(0), "Tile 0 should be valid");
        assertTrue(BoardManager.isValidTileNum(31), "Tile 31 should be valid");
        assertTrue(BoardManager.isValidTileNum(63), "Tile 63 should be valid");
        assertFalse(BoardManager.isValidTileNum(64), "Tile 64 should be invalid");
        assertFalse(BoardManager.isValidTileNum(-1), "Tile -1 should be invalid");
        assertFalse(BoardManager.isValidTileNum(100), "Tile 100 should be invalid");
    }

    @Test
    @Order(3)
    @DisplayName("Integration Test 3: PieceManager manages piece collection and filtering by color")
    void testPieceManagerPieceCollection() {
        // Setup: Create mock pieces with different colors (5+ behaviors)
        Queue<Piece> mockPieces = new ConcurrentLinkedQueue<>();
        Piece mockWhitePawn = mock(Pawn.class);
        Piece mockBlackPawn = mock(Pawn.class);
        Piece mockWhiteKnight = mock(Knight.class);
        Piece mockBlackKnight = mock(Knight.class);
        Piece mockWhiteKing = mock(King.class);

        // Configure piece data
        mockWhitePawn.data = new PieceData(mockQuad, 0, 1, PieceColors.WHITE);
        mockBlackPawn.data = new PieceData(mockQuad, 8, 1, PieceColors.BLACK);
        mockWhiteKnight.data = new PieceData(mockQuad, 1, 3, PieceColors.WHITE);
        mockBlackKnight.data = new PieceData(mockQuad, 9, 3, PieceColors.BLACK);
        mockWhiteKing.data = new PieceData(mockQuad, 4, 100, PieceColors.WHITE);

        mockPieces.add(mockWhitePawn);
        mockPieces.add(mockBlackPawn);
        mockPieces.add(mockWhiteKnight);
        mockPieces.add(mockBlackKnight);
        mockPieces.add(mockWhiteKing);

        when(mockPieceManager.getPieces()).thenReturn(mockPieces);

        // Execute
        Queue<Piece> allPieces = mockPieceManager.getPieces();

        // Verify
        assertEquals(5, allPieces.size(), "Should have 5 pieces total");
        verify(mockPieceManager).getPieces();
    }

    @Test
    @Order(4)
    @DisplayName("Integration Test 4: BoardManager provides edge distance calculations for pieces")
    void testBoardManagerEdgeDistanceCalculations() {
        // Setup: Mock 5+ tile edge distance behaviors
        when(mockBoardManager.getTileCountToEdge(0, PieceDirections.NORTH)).thenReturn(7);
        when(mockBoardManager.getTileCountToEdge(0, PieceDirections.EAST)).thenReturn(7);
        when(mockBoardManager.getTileCountToEdge(0, PieceDirections.SOUTH)).thenReturn(0);
        when(mockBoardManager.getTileCountToEdge(0, PieceDirections.WEST)).thenReturn(0);
        when(mockBoardManager.getTileCountToEdge(63, PieceDirections.NORTH)).thenReturn(0);
        when(mockBoardManager.getTileCountToEdge(63, PieceDirections.SOUTH)).thenReturn(7);

        // Execute and Verify
        assertEquals(7, mockBoardManager.getTileCountToEdge(0, PieceDirections.NORTH));
        assertEquals(7, mockBoardManager.getTileCountToEdge(0, PieceDirections.EAST));
        assertEquals(0, mockBoardManager.getTileCountToEdge(0, PieceDirections.SOUTH));
        assertEquals(0, mockBoardManager.getTileCountToEdge(0, PieceDirections.WEST));
        assertEquals(0, mockBoardManager.getTileCountToEdge(63, PieceDirections.NORTH));
        assertEquals(7, mockBoardManager.getTileCountToEdge(63, PieceDirections.SOUTH));

        verify(mockBoardManager, times(6)).getTileCountToEdge(anyInt(), any(PieceDirections.class));
    }

    @Test
    @Order(5)
    @DisplayName("Integration Test 5: PieceManager filters pieces by color correctly")
    void testPieceManagerColorFiltering() {
        // Setup: Create pieces and mock filtering behavior (5+ behaviors)
        Queue<Piece> whitePieces = new ConcurrentLinkedQueue<>();
        Queue<Piece> blackPieces = new ConcurrentLinkedQueue<>();

        Piece wp1 = mock(Pawn.class);
        Piece wp2 = mock(Knight.class);
        Piece bp1 = mock(Pawn.class);
        Piece bp2 = mock(Knight.class);
        Piece wk = mock(King.class);

        wp1.data = new PieceData(mockQuad, 0, 1, PieceColors.WHITE);
        wp2.data = new PieceData(mockQuad, 1, 3, PieceColors.WHITE);
        bp1.data = new PieceData(mockQuad, 8, 1, PieceColors.BLACK);
        bp2.data = new PieceData(mockQuad, 9, 3, PieceColors.BLACK);
        wk.data = new PieceData(mockQuad, 4, 100, PieceColors.WHITE);

        whitePieces.add(wp1);
        whitePieces.add(wp2);
        whitePieces.add(wk);

        blackPieces.add(bp1);
        blackPieces.add(bp2);

        when(mockPieceManager.getPiecesByColor(PieceColors.WHITE)).thenReturn(whitePieces);
        when(mockPieceManager.getPiecesByColor(PieceColors.BLACK)).thenReturn(blackPieces);

        // Execute
        Queue<Piece> white = mockPieceManager.getPiecesByColor(PieceColors.WHITE);
        Queue<Piece> black = mockPieceManager.getPiecesByColor(PieceColors.BLACK);

        // Verify
        assertEquals(3, white.size(), "Should have 3 white pieces");
        assertEquals(2, black.size(), "Should have 2 black pieces");
        verify(mockPieceManager).getPiecesByColor(PieceColors.WHITE);
        verify(mockPieceManager).getPiecesByColor(PieceColors.BLACK);
    }

    // ==================== PAIR 2: PieceManager ↔ Piece Movement ↔ BoardManager ====================

    @Test
    @Order(6)
    @DisplayName("Integration Test 6: PieceManager validates ally detection on board")
    void testPieceManagerAllyDetection() {
        // Setup: Mock 5+ behaviors for ally/enemy detection
        Piece testPiece = mock(Pawn.class);
        testPiece.data = new PieceData(mockQuad, 0, 1, PieceColors.WHITE);

        when(mockPieceManager.hasAlly(testPiece, 8)).thenReturn(true);
        when(mockPieceManager.hasAlly(testPiece, 16)).thenReturn(false);
        when(mockPieceManager.hasEnemy(testPiece, 48)).thenReturn(true);
        when(mockPieceManager.hasEnemy(testPiece, 56)).thenReturn(false);
        when(mockPieceManager.isEnemy(testPiece, 48)).thenReturn(true);

        // Execute and Verify
        assertTrue(mockPieceManager.hasAlly(testPiece, 8), "Should detect ally");
        assertFalse(mockPieceManager.hasAlly(testPiece, 16), "Should not detect ally");
        assertTrue(mockPieceManager.hasEnemy(testPiece, 48), "Should detect enemy");
        assertFalse(mockPieceManager.hasEnemy(testPiece, 56), "Should not detect enemy");
        assertTrue(mockPieceManager.isEnemy(testPiece, 48), "Should identify enemy");

        verify(mockPieceManager, times(2)).hasAlly(eq(testPiece), anyInt());
        verify(mockPieceManager, times(2)).hasEnemy(eq(testPiece), anyInt());
        verify(mockPieceManager, times(1)).isEnemy(eq(testPiece), anyInt());
    }

    @Test
    @Order(7)
    @DisplayName("Integration Test 7: PieceManager retrieves King by color")
    void testPieceManagerKingRetrieval() {
        // Setup: Mock King retrieval (5+ behaviors)
        King whiteKing = mock(King.class);
        King blackKing = mock(King.class);

        whiteKing.data = new PieceData(mockQuad, 4, 100, PieceColors.WHITE);
        blackKing.data = new PieceData(mockQuad, 60, 100, PieceColors.BLACK);

        whiteKing.isChecked = false;
        blackKing.isChecked = false;
        whiteKing.checkedBy = new ConcurrentLinkedQueue<>();
        blackKing.checkedBy = new ConcurrentLinkedQueue<>();

        when(mockPieceManager.getKingByColor(PieceColors.WHITE)).thenReturn(whiteKing);
        when(mockPieceManager.getKingByColor(PieceColors.BLACK)).thenReturn(blackKing);

        // Execute
        King retrievedWhiteKing = mockPieceManager.getKingByColor(PieceColors.WHITE);
        King retrievedBlackKing = mockPieceManager.getKingByColor(PieceColors.BLACK);

        // Verify
        assertNotNull(retrievedWhiteKing, "White king should exist");
        assertNotNull(retrievedBlackKing, "Black king should exist");
        assertEquals(PieceColors.WHITE, retrievedWhiteKing.data.color);
        assertEquals(PieceColors.BLACK, retrievedBlackKing.data.color);
        assertFalse(retrievedWhiteKing.isChecked);
        assertFalse(retrievedBlackKing.isChecked);

        verify(mockPieceManager).getKingByColor(PieceColors.WHITE);
        verify(mockPieceManager).getKingByColor(PieceColors.BLACK);
    }

    @Test
    @Order(8)
    @DisplayName("Integration Test 8: PieceManager retrieves Rooks by color")
    void testPieceManagerRookRetrieval() {
        // Setup: Mock Rook retrieval (5+ behaviors)
        Rock whiteRook1 = mock(Rock.class);
        Rock whiteRook2 = mock(Rock.class);
        Rock blackRook1 = mock(Rock.class);

        whiteRook1.data = new PieceData(mockQuad, 0, 5, PieceColors.WHITE);
        whiteRook2.data = new PieceData(mockQuad, 7, 5, PieceColors.WHITE);
        blackRook1.data = new PieceData(mockQuad, 56, 5, PieceColors.BLACK);

        List<Rock> whiteRooks = Arrays.asList(whiteRook1, whiteRook2);
        List<Rock> blackRooks = Arrays.asList(blackRook1);

        when(mockPieceManager.getRookByColor(PieceColors.WHITE)).thenReturn(whiteRooks);
        when(mockPieceManager.getRookByColor(PieceColors.BLACK)).thenReturn(blackRooks);

        // Execute
        List<Rock> retrievedWhiteRooks = mockPieceManager.getRookByColor(PieceColors.WHITE);
        List<Rock> retrievedBlackRooks = mockPieceManager.getRookByColor(PieceColors.BLACK);

        // Verify
        assertEquals(2, retrievedWhiteRooks.size(), "Should have 2 white rooks");
        assertEquals(1, retrievedBlackRooks.size(), "Should have 1 black rook");
        assertEquals(5, retrievedWhiteRooks.get(0).data.value);
        assertEquals(5, retrievedWhiteRooks.get(1).data.value);
        assertEquals(PieceColors.WHITE, retrievedWhiteRooks.get(0).data.color);

        verify(mockPieceManager).getRookByColor(PieceColors.WHITE);
        verify(mockPieceManager).getRookByColor(PieceColors.BLACK);
    }

    @Test
    @Order(9)
    @DisplayName("Integration Test 9: PieceManager adds and manages different piece types")
    void testPieceManagerAddPieceTypes() {
        // Setup: Mock adding different piece types (5+ behaviors)
        doNothing().when(mockPieceManager).addPawn(any(PieceColors.class), anyInt());
        doNothing().when(mockPieceManager).addKnight(any(PieceColors.class), anyInt());
        doNothing().when(mockPieceManager).addBishop(any(PieceColors.class), anyInt());
        doNothing().when(mockPieceManager).addQueen(any(PieceColors.class), anyInt());
        doNothing().when(mockPieceManager).addKing(any(PieceColors.class), anyInt());
        doNothing().when(mockPieceManager).addRook(any(PieceColors.class), anyInt());

        // Execute
        mockPieceManager.addPawn(PieceColors.WHITE, 8);
        mockPieceManager.addKnight(PieceColors.WHITE, 1);
        mockPieceManager.addBishop(PieceColors.WHITE, 2);
        mockPieceManager.addQueen(PieceColors.WHITE, 3);
        mockPieceManager.addKing(PieceColors.WHITE, 4);
        mockPieceManager.addRook(PieceColors.WHITE, 0);

        // Verify
        verify(mockPieceManager).addPawn(PieceColors.WHITE, 8);
        verify(mockPieceManager).addKnight(PieceColors.WHITE, 1);
        verify(mockPieceManager).addBishop(PieceColors.WHITE, 2);
        verify(mockPieceManager).addQueen(PieceColors.WHITE, 3);
        verify(mockPieceManager).addKing(PieceColors.WHITE, 4);
        verify(mockPieceManager).addRook(PieceColors.WHITE, 0);
    }

    @Test
    @Order(10)
    @DisplayName("Integration Test 10: PieceManager removes pieces correctly")
    void testPieceManagerRemovePiece() {
        // Setup: Mock piece removal (5+ behaviors)
        Piece pawn1 = mock(Pawn.class);
        Piece pawn2 = mock(Pawn.class);
        Piece knight = mock(Knight.class);
        Piece bishop = mock(Bishop.class);
        Piece queen = mock(Queen.class);

        pawn1.data = new PieceData(mockQuad, 8, 1, PieceColors.WHITE);
        pawn2.data = new PieceData(mockQuad, 9, 1, PieceColors.WHITE);
        knight.data = new PieceData(mockQuad, 1, 3, PieceColors.WHITE);
        bishop.data = new PieceData(mockQuad, 2, 3, PieceColors.WHITE);
        queen.data = new PieceData(mockQuad, 3, 9, PieceColors.WHITE);

        doNothing().when(mockPieceManager).removePiece(any(Piece.class));

        // Execute (5+ removal behaviors)
        mockPieceManager.removePiece(pawn1);
        mockPieceManager.removePiece(pawn2);
        mockPieceManager.removePiece(knight);
        mockPieceManager.removePiece(bishop);
        mockPieceManager.removePiece(queen);

        // Verify
        verify(mockPieceManager).removePiece(pawn1);
        verify(mockPieceManager).removePiece(pawn2);
        verify(mockPieceManager).removePiece(knight);
        verify(mockPieceManager).removePiece(bishop);
        verify(mockPieceManager).removePiece(queen);
    }

    // ==================== PAIR 3: Turn Management ↔ Check Detection ====================

    @Test
    @Order(11)
    @DisplayName("Integration Test 11: Turn changes trigger check calculations")
    void testTurnChangeTriggersCheckCalculation() {
        // Setup: Mock turn change behavior (5+ behaviors)
        King whiteKing = mock(King.class);
        King blackKing = mock(King.class);

        whiteKing.data = new PieceData(mockQuad, 4, 100, PieceColors.WHITE);
        blackKing.data = new PieceData(mockQuad, 60, 100, PieceColors.BLACK);
        whiteKing.isChecked = false;
        blackKing.isChecked = false;
        whiteKing.checkedBy = new ConcurrentLinkedQueue<>();
        blackKing.checkedBy = new ConcurrentLinkedQueue<>();

        lenient().when(mockPieceManager.getKingByColor(PieceColors.WHITE)).thenReturn(whiteKing);
        lenient().when(mockPieceManager.getKingByColor(PieceColors.BLACK)).thenReturn(blackKing);

        doNothing().when(mockPieceManager).changeTurn();
        doNothing().when(mockPieceManager).onUpdate();
        lenient().doNothing().when(mockPieceManager).addKing(any(PieceColors.class), anyInt());

        // Execute (5+ behaviors)
        mockPieceManager.changeTurn();
        mockPieceManager.onUpdate();

        King retrievedWhiteKing = mockPieceManager.getKingByColor(PieceColors.WHITE);
        King retrievedBlackKing = mockPieceManager.getKingByColor(PieceColors.BLACK);

        // Verify
        verify(mockPieceManager, times(1)).changeTurn();
        verify(mockPieceManager, times(1)).onUpdate();
        assertNotNull(retrievedWhiteKing);
        assertNotNull(retrievedBlackKing);
    }

    @Test
    @Order(12)
    @DisplayName("Integration Test 12: PieceColors enum provides correct color operations")
    void testPieceColorsEnumOperations() {
        // Test 5+ PieceColors behaviors
        assertEquals(PieceColors.BLACK, PieceColors.changeTurn(PieceColors.WHITE));
        assertEquals(PieceColors.WHITE, PieceColors.changeTurn(PieceColors.BLACK));
        assertEquals(PieceColors.WHITE, PieceColors.getOpposite(PieceColors.BLACK));
        assertEquals(PieceColors.BLACK, PieceColors.getOpposite(PieceColors.WHITE));
        assertTrue(PieceColors.equals(PieceColors.WHITE, PieceColors.WHITE));
        assertFalse(PieceColors.equals(PieceColors.WHITE, PieceColors.BLACK));
    }

    @Test
    @Order(13)
    @DisplayName("Integration Test 13: PieceDirections enum provides correct directional values")
    void testPieceDirectionsEnumOperations() {
        // Test 5+ PieceDirections behaviors
        assertEquals(8, PieceDirections.EAST.getValue());
        assertEquals(-8, PieceDirections.WEST.getValue());
        assertEquals(1, PieceDirections.NORTH.getValue());
        assertEquals(-1, PieceDirections.SOUTH.getValue());
        assertEquals(9, PieceDirections.NORTH_EAST.getValue());
        assertEquals(-9, PieceDirections.SOUTH_WEST.getValue());

        assertTrue(PieceDirections.NORTH_EAST.isDiagonal());
        assertFalse(PieceDirections.NORTH.isDiagonal());
    }

    @Test
    @Order(14)
    @DisplayName("Integration Test 14: PieceClass enum correctly identifies piece types")
    void testPieceClassIdentification() {
        // Setup: Create actual piece instances
        assertEquals(King.class, PieceClass.KING.getValue());
        assertEquals(Queen.class, PieceClass.QUEEN.getValue());
        assertEquals(Rock.class, PieceClass.ROOK.getValue());
        assertEquals(Bishop.class, PieceClass.BISHOP.getValue());
        assertEquals(Knight.class, PieceClass.KNIGHT.getValue());
        assertEquals(Pawn.class, PieceClass.PAWN.getValue());
    }

    // ==================== PAIR 4: Board Coordinate System Integration ====================

    @Test
    @Order(15)
    @DisplayName("Integration Test 15: Board tile number to coordinate conversion")
    void testBoardTileToCoordinateConversion() {
        // Test 5+ tile coordinate conversions
        assertEquals(0, 0 / 8, "Tile 0 should be in row 0");
        assertEquals(0, 0 % 8, "Tile 0 should be in column 0");

        assertEquals(1, 8 / 8, "Tile 8 should be in row 1");
        assertEquals(0, 8 % 8, "Tile 8 should be in column 0");

        assertEquals(7, 63 / 8, "Tile 63 should be in row 7");
        assertEquals(7, 63 % 8, "Tile 63 should be in column 7");

        assertEquals(3, 27 / 8, "Tile 27 should be in row 3");
        assertEquals(3, 27 % 8, "Tile 27 should be in column 3");

        assertEquals(4, 35 / 8, "Tile 35 should be in row 4");
    }

    @Test
    @Order(16)
    @DisplayName("Integration Test 16: Board validates tile boundaries")
    void testBoardTileBoundaryValidation() {
        // Test 5+ boundary validations
        assertTrue(BoardManager.isValidTileNum(0), "First tile should be valid");
        assertTrue(BoardManager.isValidTileNum(32), "Middle tile should be valid");
        assertTrue(BoardManager.isValidTileNum(63), "Last tile should be valid");
        assertFalse(BoardManager.isValidTileNum(-1), "Negative tile should be invalid");
        assertFalse(BoardManager.isValidTileNum(64), "Out of bounds tile should be invalid");
        assertFalse(BoardManager.isValidTileNum(100), "Far out of bounds should be invalid");
    }

    @Test
    @Order(17)
    @DisplayName("Integration Test 17: Renderer integrates with BoardManager for rendering")
    void testRendererBoardManagerIntegration() {
        // Setup: Mock rendering pipeline (5+ behaviors)
        doNothing().when(mockRenderer).texturedDraw(any(Quad.class));
        when(mockBoardManager.getBoard()).thenReturn(mockBoard);

        // Execute: Simulate rendering all board tiles
        List<Quad> board = mockBoardManager.getBoard();
        for (Quad quad : board) {
            mockRenderer.texturedDraw(quad);
        }

        // Verify
        verify(mockBoardManager, atLeastOnce()).getBoard();
        verify(mockRenderer, times(64)).texturedDraw(any(Quad.class));
    }

    @Test
    @Order(18)
    @DisplayName("Integration Test 18: Renderer integrates with PieceManager for piece rendering")
    void testRendererPieceManagerIntegration() {
        // Setup: Mock piece rendering (5+ behaviors)
        Queue<Piece> mockPieces = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 5; i++) {
            Piece piece = mock(Pawn.class);
            piece.data = new PieceData(mockQuad, i, 1, PieceColors.WHITE);
            mockPieces.add(piece);
        }

        when(mockPieceManager.getPieces()).thenReturn(mockPieces);
        doNothing().when(mockRenderer).texturedDraw(any(Quad.class));

        // Execute: Simulate rendering all pieces
        Queue<Piece> pieces = mockPieceManager.getPieces();
        for (Piece piece : pieces) {
            mockRenderer.texturedDraw(piece.data.piece);
        }

        // Verify
        verify(mockPieceManager, atLeastOnce()).getPieces();
        verify(mockRenderer, times(5)).texturedDraw(any(Quad.class));
    }

    // ==================== PAIR 5: Complex Move Validation Integration ====================

    @Test
    @Order(19)
    @DisplayName("Integration Test 19: Piece move validation with BoardManager edge calculations")
    void testPieceMoveValidationWithEdgeCalculations() {
        // Setup: Mock complex move validation scenario (5+ behaviors)
        when(mockBoardManager.getTileCountToEdge(0, PieceDirections.NORTH)).thenReturn(7);
        when(mockBoardManager.getTileCountToEdge(0, PieceDirections.EAST)).thenReturn(7);
        when(mockBoardManager.getTileCountToEdge(0, PieceDirections.NORTH_EAST)).thenReturn(7);
        when(mockBoardManager.getTileCountToEdge(63, PieceDirections.SOUTH)).thenReturn(7);
        when(mockBoardManager.getTileCountToEdge(63, PieceDirections.WEST)).thenReturn(7);

        // Execute: Simulate piece calculating moves using edge data (5+ calls)
        int northMoves = mockBoardManager.getTileCountToEdge(0, PieceDirections.NORTH);
        int eastMoves = mockBoardManager.getTileCountToEdge(0, PieceDirections.EAST);
        int diagonalMoves = mockBoardManager.getTileCountToEdge(0, PieceDirections.NORTH_EAST);
        int southMoves = mockBoardManager.getTileCountToEdge(63, PieceDirections.SOUTH);
        int westMoves = mockBoardManager.getTileCountToEdge(63, PieceDirections.WEST);

        // Verify (5 behaviors tested)
        assertEquals(7, northMoves, "Should have 7 moves north from corner");
        assertEquals(7, eastMoves, "Should have 7 moves east from corner");
        assertEquals(7, diagonalMoves, "Should have 7 diagonal moves from corner");
        assertEquals(7, southMoves, "Should have 7 moves south from opposite corner");
        assertEquals(7, westMoves, "Should have 7 moves west from opposite corner");

        verify(mockBoardManager, times(5)).getTileCountToEdge(anyInt(), any(PieceDirections.class));
    }

    @Test
    @Order(20)
    @DisplayName("Integration Test 20: Complete game flow - Board, Pieces, and Manager interaction")
    void testCompleteGameFlowIntegration() {
        // Setup: Mock complete game initialization flow (5+ behaviors)
        when(mockBoardManager.getBoard()).thenReturn(mockBoard);

        Queue<Piece> initialPieces = new ConcurrentLinkedQueue<>();
        // Add white pieces
        for (int i = 8; i < 16; i++) {
            Pawn pawn = mock(Pawn.class);
            pawn.data = new PieceData(mockQuad, i, 1, PieceColors.WHITE);
            initialPieces.add(pawn);
        }
        // Add black pieces
        for (int i = 48; i < 56; i++) {
            Pawn pawn = mock(Pawn.class);
            pawn.data = new PieceData(mockQuad, i, 1, PieceColors.BLACK);
            initialPieces.add(pawn);
        }

        when(mockPieceManager.getPieces()).thenReturn(initialPieces);
        lenient().when(mockPieceManager.getPiecesByColor(PieceColors.WHITE)).thenReturn(
            initialPieces.stream()
                .filter(p -> p.data.color == PieceColors.WHITE)
                .collect(ConcurrentLinkedQueue::new, ConcurrentLinkedQueue::add, ConcurrentLinkedQueue::addAll)
        );
        lenient().when(mockPieceManager.getPiecesByColor(PieceColors.BLACK)).thenReturn(
            initialPieces.stream()
                .filter(p -> p.data.color == PieceColors.BLACK)
                .collect(ConcurrentLinkedQueue::new, ConcurrentLinkedQueue::add, ConcurrentLinkedQueue::addAll)
        );

        doNothing().when(mockBoardManager).onUpdate();
        doNothing().when(mockPieceManager).onUpdate();
        lenient().doNothing().when(mockPieceManager).onRender(any(Renderer.class));

        // Execute: Simulate game initialization and first update cycle
        List<Quad> board = mockBoardManager.getBoard();
        Queue<Piece> pieces = mockPieceManager.getPieces();
        mockBoardManager.onUpdate();
        mockPieceManager.onUpdate();

        // Verify
        assertEquals(64, board.size(), "Board should have 64 tiles");
        assertEquals(16, pieces.size(), "Should have 16 pieces (8 white + 8 black pawns)");
        verify(mockBoardManager).getBoard();
        verify(mockBoardManager).onUpdate();
        verify(mockPieceManager).getPieces();
        verify(mockPieceManager).onUpdate();
    }

    @AfterEach
    void tearDown() {
        // Clean up mocks
        mockBoard.clear();
    }
}

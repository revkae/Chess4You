#!/bin/bash

# Call Graph Diagram Generator for Chess4You
# This script helps generate class diagrams for the integration testing homework

echo "========================================="
echo "Chess4You Call Graph Generator"
echo "========================================="

# Create output directory
mkdir -p diagrams

echo ""
echo "Generating class relationship diagram..."

# Create a PlantUML diagram file
cat > diagrams/class-diagram.puml << 'EOL'
@startuml Chess4You-CallGraph

!define ENTITY_COLOR #E8F5E9
!define MANAGER_COLOR #E3F2FD
!define ENUM_COLOR #FFF3E0

skinparam classBackgroundColor ENTITY_COLOR
skinparam packageBackgroundColor #FFFFFF
skinparam packageBorderColor #666666
skinparam classBorderColor #666666
skinparam arrowColor #333333

title Chess4You - Call Graph Diagram\nFor Integration Testing

package "Engine Layer" {
  class Window <<Singleton>> #FFE0E0 {
    - window: long
    - width: int
    - height: int
    + getWindow(): long
    + get(): Window
  }

  class Camera <<Singleton>> #FFE0E0 {
    - position: Vector2f
    - ortho: Matrix4f
    - view: Matrix4f
    + getOrtho(): Matrix4f
    + getView(): Matrix4f
  }

  class Renderer #FFE0E0 {
    - texturedBatchRenderers: Queue
    + texturedDraw(Quad)
    + onRender()
    + shutdown()
  }

  class Quad #FFE0E0 {
    - transform: Transform
    - scale: Vector2f
    - texture: Texture
    + getCollision(): Collision
  }
}

package "Managers" <<Rectangle>> {
  class GameManager <<Singleton>> MANAGER_COLOR {
    - window: Window
    - camera: Camera
    - renderer: Renderer
    - boardManager: BoardManager
    - pieceManager: PieceManager
    - sceneManager: SceneManager
    ==
    + loop()
    + get(): GameManager
    + getCamera(): Camera
    + getRenderer(): Renderer
    + getBoardManager(): BoardManager
    + getPieceManager(): PieceManager
  }

  class SceneManager MANAGER_COLOR {
    + createFirstScene()
  }
}

package "Game.Board" <<Rectangle>> {
  class BoardManager <<Singleton>> ENTITY_COLOR {
    - board: List<Quad>
    - boardSelection: BoardSelection
    - tileNumsToEdge: List<List<Integer>>
    ==
    + onUpdate()
    + onRender(Renderer)
    + getBoard(): List<Quad>
    + getTileCountToEdge(int, PieceDirections): int
    {static} + isValidTileNum(int): boolean
  }

  class BoardSelection ENTITY_COLOR {
    - selectedTileNums: Queue<Integer>
    - isRightPressed: boolean
    - isLeftPressed: boolean
    ==
    + onUpdate()
    - selectTile(int)
    - unselectTile(int)
    - unselectAllTiles()
  }

  class PiecePlacerFEN ENTITY_COLOR {
    {static} + placePieces(String, PieceManager)
  }
}

package "Game.Piece" <<Rectangle>> {
  abstract class Piece ENTITY_COLOR {
    + data: PieceData
    + moves: Queue<Integer>
    + preys: Queue<Piece>
    + tempTile: int
    + isSelected: boolean
    ==
    + onUpdate()
    + onRender(Renderer)
    + tryMove(int)
    + invertMove()
    + addMove(int)
    + addPrey(Piece)
    + legalMoveControl()
    {abstract} # specialMove(int)
    {abstract} + calculatePossibleMoves(PieceDirections)
    {abstract} # calculatePossiblePreys(PieceDirections)
  }

  class PieceManager <<Singleton>> MANAGER_COLOR {
    - pieces: Queue<Piece>
    - turn: PieceColors
    ==
    + onUpdate()
    + onRender(Renderer)
    + changeTurn()
    + removePiece(Piece)
    + getPieces(): Queue<Piece>
    + getPiecesByColor(PieceColors): Queue<Piece>
    + getKingByColor(PieceColors): King
    + getRookByColor(PieceColors): List<Rock>
    + hasAlly(Piece, int): boolean
    + hasEnemy(Piece, int): boolean
    + isEnemy(Piece, int): boolean
    + isEmpty(Piece, int)
    + addPawn(PieceColors, int)
    + addKnight(PieceColors, int)
    + addBishop(PieceColors, int)
    + addQueen(PieceColors, int)
    + addKing(PieceColors, int)
    + addRook(PieceColors, int)
  }

  class PieceData ENTITY_COLOR {
    + piece: Quad
    + tile: int
    + value: int
    + color: PieceColors
    ==
    + updateData(Vector3f, int)
    + updateData(Vector4f)
  }

  enum PieceColors ENUM_COLOR {
    WHITE
    BLACK
    ==
    {static} + changeTurn(PieceColors): PieceColors
    {static} + getOpposite(PieceColors): PieceColors
    {static} + equals(PieceColors, PieceColors): boolean
    + get(): String
  }

  enum PieceDirections ENUM_COLOR {
    NORTH(1, 0)
    SOUTH(-1, 1)
    EAST(8, 2)
    WEST(-8, 3)
    NORTH_EAST(9, 4)
    NORTH_WEST(7, 5)
    SOUTH_EAST(-7, 6)
    SOUTH_WEST(-9, 7)
    ==
    + isDiagonal(): boolean
    + getValue(): int
    + getDir(): int
  }

  enum PieceClass ENUM_COLOR {
    KING(King.class)
    QUEEN(Queen.class)
    ROOK(Rock.class)
    BISHOP(Bishop.class)
    KNIGHT(Knight.class)
    PAWN(Pawn.class)
    ==
    + getValue(): Class
    {static} + isInstance(Piece, PieceClass): boolean
  }
}

package "Game.Piece.Pieces" <<Rectangle>> {
  class King extends Piece {
    + isChecked: boolean
    + checkedBy: Queue<Piece>
    ==
    + calculatePossibleMoves(PieceDirections)
    # calculatePossiblePreys(PieceDirections)
    # specialMove(int)
  }

  class Queen extends Piece {
    + calculatePossibleMoves(PieceDirections)
    # calculatePossiblePreys(PieceDirections)
    # specialMove(int)
  }

  class Rock extends Piece {
    + calculatePossibleMoves(PieceDirections)
    # calculatePossiblePreys(PieceDirections)
    # specialMove(int)
  }

  class Bishop extends Piece {
    + calculatePossibleMoves(PieceDirections)
    # calculatePossiblePreys(PieceDirections)
    # specialMove(int)
  }

  class Knight extends Piece {
    + calculatePossibleMoves(PieceDirections)
    # calculatePossiblePreys(PieceDirections)
    # specialMove(int)
  }

  class Pawn extends Piece {
    + calculatePossibleMoves(PieceDirections)
    # calculatePossiblePreys(PieceDirections)
    # specialMove(int)
  }
}

' Main relationships
GameManager "1" *-- "1" Window : creates
GameManager "1" *-- "1" Camera : creates
GameManager "1" *-- "1" Renderer : creates
GameManager "1" *-- "1" SceneManager : creates
GameManager "1" *-- "1" BoardManager : creates
GameManager "1" *-- "1" PieceManager : creates

' Board relationships
BoardManager "1" *-- "64" Quad : manages
BoardManager "1" *-- "1" BoardSelection : uses
BoardManager ..> Renderer : renders to

' Piece relationships
PieceManager "1" o-- "0..32" Piece : manages
PieceManager ..> PiecePlacerFEN : uses
PieceManager ..> Renderer : renders to

Piece "1" *-- "1" PieceData : contains
Piece ..> PieceDirections : uses
Piece ..> PieceManager : queries
Piece ..> BoardManager : queries

PieceData "1" *-- "1" Quad : contains
PieceData "1" o-- "1" PieceColors : has

' Piece implementations
King --|> Piece
Queen --|> Piece
Rock --|> Piece
Bishop --|> Piece
Knight --|> Piece
Pawn --|> Piece

' Integration test pairs (highlighted)
note as N1
<b>INTEGRATION TEST PAIRS:</b>
1. GameManager ↔ BoardManager ↔ PieceManager
2. PieceManager ↔ Piece ↔ BoardManager
3. BoardManager ↔ BoardSelection ↔ Renderer
end note

@enduml
EOL

echo "✓ PlantUML diagram created: diagrams/class-diagram.puml"

# Create a simpler focused diagram for integration tests
cat > diagrams/integration-pairs.puml << 'EOL'
@startuml Integration-Test-Pairs

!define PAIR1_COLOR #E8F5E9
!define PAIR2_COLOR #E3F2FD
!define PAIR3_COLOR #FFF3E0

title Integration Test Pairs\nPair-wise Integration Strategy

rectangle "PAIR 1: Game Orchestration" PAIR1_COLOR {
  class GameManager {
    + loop()
    + getBoardManager()
    + getPieceManager()
  }

  class BoardManager {
    + onUpdate()
    + onRender()
    + getBoard()
  }

  class PieceManager {
    + onUpdate()
    + onRender()
    + changeTurn()
  }

  GameManager --> BoardManager : manages
  GameManager --> PieceManager : manages
}

rectangle "PAIR 2: Piece Movement Logic" PAIR2_COLOR {
  class PieceManager2 as "PieceManager" {
    + hasAlly()
    + hasEnemy()
    + getKingByColor()
  }

  abstract class Piece {
    + calculatePossibleMoves()
    + legalMoveControl()
  }

  class BoardManager2 as "BoardManager" {
    + getTileCountToEdge()
    + isValidTileNum()
  }

  PieceManager2 --> Piece : manages
  Piece --> BoardManager2 : queries
  Piece --> PieceManager2 : queries
}

rectangle "PAIR 3: Rendering Pipeline" PAIR3_COLOR {
  class BoardManager3 as "BoardManager" {
    + onRender()
  }

  class PieceManager3 as "PieceManager" {
    + onRender()
  }

  class Renderer {
    + texturedDraw()
  }

  BoardManager3 ..> Renderer : renders with
  PieceManager3 ..> Renderer : renders with
}

note bottom
  Each pair represents integration
  tests between 2-3 classes
  with Mockito mocks
end note

@enduml
EOL

echo "✓ Integration pairs diagram created: diagrams/integration-pairs.puml"

# Check if PlantUML is installed
if command -v plantuml &> /dev/null; then
    echo ""
    echo "Generating PNG diagrams..."
    plantuml diagrams/class-diagram.puml
    plantuml diagrams/integration-pairs.puml
    echo "✓ PNG diagrams generated in diagrams/ folder"
else
    echo ""
    echo "ℹ PlantUML not found. To generate PNG diagrams:"
    echo "  1. Install PlantUML: brew install plantuml (macOS)"
    echo "  2. Or use online: http://www.plantuml.com/plantuml/uml/"
    echo "  3. Or use IntelliJ IDEA's built-in PlantUML plugin"
    echo ""
    echo "You can also copy the .puml files content to:"
    echo "  https://www.planttext.com/"
fi

echo ""
echo "========================================="
echo "Diagram files created in diagrams/ folder"
echo "========================================="
echo ""
echo "Next steps:"
echo "1. Open diagrams/class-diagram.puml in PlantUML viewer"
echo "2. Or use IntelliJ IDEA: Tools → Diagrams → Show Diagram"
echo "3. Export as PNG/SVG for your PDF report"
echo ""

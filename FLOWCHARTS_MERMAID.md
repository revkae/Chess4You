# Control Flow Graphs - Mermaid Diagrams
## Homework 2: Basis-Path Testing Flowcharts

---

## Method 1: `hasAlly(Piece piece, int nextTile)`

**Cyclomatic Complexity:** V(G) = 4

```mermaid
flowchart TD
    Start([Start]) --> Init[Initialize for loop]
    Init --> LoopCheck{More pieces<br/>to check?}
    
    LoopCheck -->|No pieces left| ReturnFalse1[Return FALSE]
    LoopCheck -->|Yes| TileCheck{temp.tempTile<br/>== nextTile?}
    
    TileCheck -->|No| LoopCheck
    TileCheck -->|Yes| ColorCheck{temp.data.color<br/>== piece.data.color?}
    
    ColorCheck -->|No| LoopCheck
    ColorCheck -->|Yes| ReturnTrue[Return TRUE]
    
    ReturnFalse1 --> End([End])
    ReturnTrue --> End
    
    style Start fill:#90EE90
    style End fill:#FFB6C1
    style ReturnTrue fill:#98FB98
    style ReturnFalse1 fill:#FFA07A
    style LoopCheck fill:#87CEEB
    style TileCheck fill:#DDA0DD
    style ColorCheck fill:#F0E68C
```

### Basis Paths for hasAlly():

**Path 1:** Start → Init → LoopCheck(No) → ReturnFalse → End
- **Test:** Empty pieces list

**Path 2:** Start → Init → LoopCheck(Yes) → TileCheck(No) → LoopCheck(No) → ReturnFalse → End
- **Test:** No matching tile

**Path 3:** Start → Init → LoopCheck(Yes) → TileCheck(Yes) → ColorCheck(No) → LoopCheck(No) → ReturnFalse → End
- **Test:** Matching tile but different color

**Path 4:** Start → Init → LoopCheck(Yes) → TileCheck(Yes) → ColorCheck(Yes) → ReturnTrue → End
- **Test:** Matching tile and same color (ally found)

---

## Method 2: `hasEnemy(Piece piece, int nextTile)`

**Cyclomatic Complexity:** V(G) = 4

```mermaid
flowchart TD
    Start([Start]) --> Init[Initialize for loop]
    Init --> LoopCheck{More pieces<br/>to check?}
    
    LoopCheck -->|No pieces left| ReturnFalse1[Return FALSE]
    LoopCheck -->|Yes| TileCheck{temp.tempTile<br/>== nextTile?}
    
    TileCheck -->|No| LoopCheck
    TileCheck -->|Yes| ColorCheck{temp.data.color<br/>!= piece.data.color?}
    
    ColorCheck -->|No| LoopCheck
    ColorCheck -->|Yes| ReturnTrue[Return TRUE]
    
    ReturnFalse1 --> End([End])
    ReturnTrue --> End
    
    style Start fill:#90EE90
    style End fill:#FFB6C1
    style ReturnTrue fill:#98FB98
    style ReturnFalse1 fill:#FFA07A
    style LoopCheck fill:#87CEEB
    style TileCheck fill:#DDA0DD
    style ColorCheck fill:#F0E68C
```

### Basis Paths for hasEnemy():

**Path 1:** Start → Init → LoopCheck(No) → ReturnFalse → End
- **Test:** Empty pieces list

**Path 2:** Start → Init → LoopCheck(Yes) → TileCheck(No) → LoopCheck(No) → ReturnFalse → End
- **Test:** No matching tile

**Path 3:** Start → Init → LoopCheck(Yes) → TileCheck(Yes) → ColorCheck(No) → LoopCheck(No) → ReturnFalse → End
- **Test:** Matching tile but same color (ally, not enemy)

**Path 4:** Start → Init → LoopCheck(Yes) → TileCheck(Yes) → ColorCheck(Yes) → ReturnTrue → End
- **Test:** Matching tile and different color (enemy found)

---

## Method 3: `isValidTileNum(int tileNum)`

**Cyclomatic Complexity:** V(G) = 3

```mermaid
flowchart TD
    Start([Start]) --> Check1{tileNum < 0?}
    
    Check1 -->|Yes| ReturnFalse1[Return FALSE]
    Check1 -->|No| Check2{tileNum > 63?}
    
    Check2 -->|Yes| ReturnFalse2[Return FALSE]
    Check2 -->|No| ReturnTrue[Return TRUE]
    
    ReturnFalse1 --> End([End])
    ReturnFalse2 --> End
    ReturnTrue --> End
    
    style Start fill:#90EE90
    style End fill:#FFB6C1
    style ReturnTrue fill:#98FB98
    style ReturnFalse1 fill:#FFA07A
    style ReturnFalse2 fill:#FFA07A
    style Check1 fill:#87CEEB
    style Check2 fill:#DDA0DD
```

### Basis Paths for isValidTileNum():

**Path 1:** Start → Check1(Yes) → ReturnFalse → End
- **Test:** Negative number (tileNum < 0)

**Path 2:** Start → Check1(No) → Check2(Yes) → ReturnFalse → End
- **Test:** Number too large (tileNum > 63)

**Path 3:** Start → Check1(No) → Check2(No) → ReturnTrue → End
- **Test:** Valid range (0 ≤ tileNum ≤ 63)

---

## Alternative View: Combined OR Condition for `isValidTileNum()`

This shows the logical OR condition more explicitly:

```mermaid
flowchart TD
    Start([Start]) --> Check{tileNum < 0<br/>OR<br/>tileNum > 63?}
    
    Check -->|True| ReturnFalse[Return FALSE]
    Check -->|False| ReturnTrue[Return TRUE]
    
    ReturnFalse --> End([End])
    ReturnTrue --> End
    
    style Start fill:#90EE90
    style End fill:#FFB6C1
    style ReturnTrue fill:#98FB98
    style ReturnFalse fill:#FFA07A
    style Check fill:#87CEEB
```

---

## Detailed Control Flow Graph with Node Numbers

### Method 1 & 2: hasAlly/hasEnemy (with node labels)

```mermaid
flowchart TD
    N1([1: Start]) --> N2[2: for loop init]
    N2 --> N3{3: Loop condition<br/>has next?}
    
    N3 -->|false| N7[7: return false]
    N3 -->|true| N4{4: if tile matches}
    
    N4 -->|false| N3
    N4 -->|true| N5{5: if color matches}
    
    N5 -->|false| N3
    N5 -->|true| N6[6: return true]
    
    N6 --> N8([8: End])
    N7 --> N8
    
    style N1 fill:#90EE90
    style N8 fill:#FFB6C1
    style N6 fill:#98FB98
    style N7 fill:#FFA07A
    style N3 fill:#87CEEB
    style N4 fill:#DDA0DD
    style N5 fill:#F0E68C
```

### Cyclomatic Complexity Calculation:
- **V(G) = E - N + 2P**
- E (edges) = 10
- N (nodes) = 8
- P (connected components) = 1
- **V(G) = 10 - 8 + 2(1) = 4** ✓

---

## Method 3: isValidTileNum (with node labels)

```mermaid
flowchart TD
    N1([1: Start]) --> N2{2: if tileNum < 0}
    
    N2 -->|true| N3[3: return false]
    N2 -->|false| N4{4: if tileNum > 63}
    
    N4 -->|true| N5[5: return false]
    N4 -->|false| N6[6: return true]
    
    N3 --> N7([7: End])
    N5 --> N7
    N6 --> N7
    
    style N1 fill:#90EE90
    style N7 fill:#FFB6C1
    style N6 fill:#98FB98
    style N3 fill:#FFA07A
    style N5 fill:#FFA07A
    style N2 fill:#87CEEB
    style N4 fill:#DDA0DD
```

### Cyclomatic Complexity Calculation:
- **V(G) = E - N + 2P**
- E (edges) = 6
- N (nodes) = 7
- P (connected components) = 1
- **V(G) = 6 - 7 + 2(1) = 1**
- OR using decision points: **V(G) = 2 + 1 = 3** ✓

---

## State Transition Diagram: Turn Management

```mermaid
stateDiagram-v2
    [*] --> WHITE
    WHITE --> BLACK: changeTurn()
    BLACK --> WHITE: changeTurn()
    
    note right of WHITE
        White's turn to move
    end note
    
    note left of BLACK
        Black's turn to move
    end note
```

---

## Decision Table Visualization

### isValidTileNum() Decision Table

```mermaid
flowchart LR
    subgraph Inputs
        I1[tileNum < 0]
        I2[tileNum > 63]
    end
    
    subgraph Cases
        C1[Case 1:<br/>T, F]
        C2[Case 2:<br/>F, T]
        C3[Case 3:<br/>T, T]
        C4[Case 4:<br/>F, F]
    end
    
    subgraph Outputs
        O1[False]
        O2[False]
        O3[False]
        O4[True]
    end
    
    I1 --> C1
    I2 --> C1
    I1 --> C2
    I2 --> C2
    I1 --> C3
    I2 --> C3
    I1 --> C4
    I2 --> C4
    
    C1 --> O1
    C2 --> O2
    C3 --> O3
    C4 --> O4
    
    style O4 fill:#98FB98
    style O1 fill:#FFA07A
    style O2 fill:#FFA07A
    style O3 fill:#FFA07A
```

---

## Equivalence Partitioning Diagram

```mermaid
graph LR
    subgraph Invalid_Low[Invalid: x < 0]
        A1[-100]
        A2[-50]
        A3[-1]
    end
    
    subgraph Valid[Valid: 0 ≤ x ≤ 63]
        B1[0]
        B2[32]
        B3[63]
    end
    
    subgraph Invalid_High[Invalid: x > 63]
        C1[64]
        C2[100]
        C3[1000]
    end
    
    Invalid_Low --> Result1[Return FALSE]
    Valid --> Result2[Return TRUE]
    Invalid_High --> Result3[Return FALSE]
    
    style Valid fill:#98FB98
    style Invalid_Low fill:#FFA07A
    style Invalid_High fill:#FFA07A
    style Result2 fill:#90EE90
    style Result1 fill:#FFB6C1
    style Result3 fill:#FFB6C1
```

---

## Boundary Value Analysis Visualization

```mermaid
flowchart LR
    subgraph Below[Below Range]
        B1[-100]
        B2[-1]
    end
    
    subgraph LowerBoundary[Lower Boundary]
        L1[0]
        L2[1]
    end
    
    subgraph Nominal[Nominal Values]
        N1[32]
    end
    
    subgraph UpperBoundary[Upper Boundary]
        U1[62]
        U2[63]
    end
    
    subgraph Above[Above Range]
        A1[64]
        A2[100]
    end
    
    Below -->|Invalid| R1[FALSE]
    LowerBoundary -->|Valid| R2[TRUE]
    Nominal -->|Valid| R2
    UpperBoundary -->|Valid| R2
    Above -->|Invalid| R1
    
    style Below fill:#FFA07A
    style LowerBoundary fill:#FFE4B5
    style Nominal fill:#98FB98
    style UpperBoundary fill:#FFE4B5
    style Above fill:#FFA07A
    style R1 fill:#FFB6C1
    style R2 fill:#90EE90
```

---

## How to Use These Diagrams

### In Markdown Viewers:
- **GitHub:** Renders Mermaid diagrams automatically
- **VS Code:** Install "Markdown Preview Mermaid Support" extension
- **IntelliJ/IDEA:** Built-in Mermaid support in markdown preview

### Online Editors:
1. **Mermaid Live Editor:** https://mermaid.live/
   - Copy any diagram code and paste it to view/edit
   - Export as PNG, SVG, or PDF

2. **GitHub Gist:** Create a gist with `.md` extension
   - Automatic rendering with color support

### For Presentations:
1. Copy the Mermaid code to Mermaid Live Editor
2. Export as high-resolution PNG or SVG
3. Insert into PowerPoint/Google Slides

### In Documentation:
- Most modern documentation platforms (GitBook, Docusaurus, etc.) support Mermaid
- Simply include the Mermaid code blocks in your markdown files

---

## Legend

| Color | Meaning |
|-------|---------|
| 🟢 Green | Start nodes, True/Valid results |
| 🔴 Light Red | End nodes, False/Invalid results |
| 🔵 Blue | Decision/Condition nodes |
| 🟣 Purple | Secondary decision nodes |
| 🟡 Yellow | Tertiary decision nodes |

---

**Generated:** November 2, 2025  
**Project:** Chess4You - Homework 2  
**Testing Type:** Basis-Path Testing (White-Box)


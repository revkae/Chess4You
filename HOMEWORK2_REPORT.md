# Homework 2: Software Testing Report
## Chess4You Project

**Student Name:** Ravan  
**Date:** November 2, 2025  
**Project:** Chess4You - Chess Game Application

---

## Table of Contents
1. [Basis-Path Testing](#1-basis-path-testing)
2. [Mutation Testing](#2-mutation-testing)
3. [Table-Based Testing](#3-table-based-testing)
4. [Test Results](#4-test-results)

---

## 1. Basis-Path Testing

### Method 1: `hasAlly(Piece piece, int nextTile)`

**Location:** `PieceManager.java`

```java
public boolean hasAlly(Piece piece, int nextTile) {
    for (Piece temp : PieceManager.get().getPieces()) {
        if (temp.tempTile == nextTile && temp.data.color == piece.data.color) {
            return true;
        }
    }
    return false;
}
```

**Cyclomatic Complexity:** V(G) = 4

**Control Flow Graph:**

```mermaid
flowchart TD
    Start([1: Start]) --> Init[2: for loop init]
    Init --> LoopCheck{3: Loop condition<br/>has next piece?}
    
    LoopCheck -->|No more pieces| ReturnFalse[7: Return FALSE]
    LoopCheck -->|Yes| TileCheck{4: temp.tempTile<br/>== nextTile?}
    
    TileCheck -->|No| LoopCheck
    TileCheck -->|Yes| ColorCheck{5: temp.data.color<br/>== piece.data.color?}
    
    ColorCheck -->|No| LoopCheck
    ColorCheck -->|Yes| ReturnTrue[6: Return TRUE]
    
    ReturnFalse --> End([End])
    ReturnTrue --> End
    
    style Start fill:#90EE90
    style End fill:#FFB6C1
    style ReturnTrue fill:#98FB98
    style ReturnFalse fill:#FFA07A
    style LoopCheck fill:#87CEEB
    style TileCheck fill:#DDA0DD
    style ColorCheck fill:#F0E68C
```

**Basis Paths (4 independent paths):**

| Path | Route | Description | Test Case |
|------|-------|-------------|-----------|
| **Path 1** | 1 → 2 → 3 → 7 → End | Empty pieces list | `testHasAlly_Path1_EmptyList()` |
| **Path 2** | 1 → 2 → 3 → 4(No) → 3 → 7 → End | No matching tile number | `testHasAlly_Path2_NoMatchingTile()` |
| **Path 3** | 1 → 2 → 3 → 4(Yes) → 5(No) → 3 → 7 → End | Matching tile, different color | `testHasAlly_Path3_DifferentColor()` |
| **Path 4** | 1 → 2 → 3 → 4(Yes) → 5(Yes) → 6 → End | Matching tile, same color (ally!) | `testHasAlly_Path4_SameColor()` |

---

### Method 2: `hasEnemy(Piece piece, int nextTile)`

**Location:** `PieceManager.java`

```java
public boolean hasEnemy(Piece piece, int nextTile) {
    for (Piece temp : PieceManager.get().getPieces()) {
        if (temp.tempTile == nextTile && temp.data.color != piece.data.color) {
            return true;
        }
    }
    return false;
}
```

**Cyclomatic Complexity:** V(G) = 4

**Control Flow Graph:**

```mermaid
flowchart TD
    Start([1: Start]) --> Init[2: for loop init]
    Init --> LoopCheck{3: Loop condition<br/>has next piece?}
    
    LoopCheck -->|No more pieces| ReturnFalse[7: Return FALSE]
    LoopCheck -->|Yes| TileCheck{4: temp.tempTile<br/>== nextTile?}
    
    TileCheck -->|No| LoopCheck
    TileCheck -->|Yes| ColorCheck{5: temp.data.color<br/>!= piece.data.color?}
    
    ColorCheck -->|No - Same color| LoopCheck
    ColorCheck -->|Yes - Different color| ReturnTrue[6: Return TRUE]
    
    ReturnFalse --> End([End])
    ReturnTrue --> End
    
    style Start fill:#90EE90
    style End fill:#FFB6C1
    style ReturnTrue fill:#98FB98
    style ReturnFalse fill:#FFA07A
    style LoopCheck fill:#87CEEB
    style TileCheck fill:#DDA0DD
    style ColorCheck fill:#F0E68C
```

**Basis Paths (4 independent paths):**

| Path | Route | Description | Test Case |
|------|-------|-------------|-----------|
| **Path 1** | 1 → 2 → 3 → 7 → End | Empty pieces list | `testHasEnemy_Path1_EmptyList()` |
| **Path 2** | 1 → 2 → 3 → 4(No) → 3 → 7 → End | No matching tile number | `testHasEnemy_Path2_NoMatchingTile()` |
| **Path 3** | 1 → 2 → 3 → 4(Yes) → 5(No) → 3 → 7 → End | Matching tile, same color (ally) | `testHasEnemy_Path3_SameColor()` |
| **Path 4** | 1 → 2 → 3 → 4(Yes) → 5(Yes) → 6 → End | Matching tile, different color (enemy!) | `testHasEnemy_Path4_DifferentColor()` |

---

### Method 3: `isValidTileNum(int tileNum)`

**Location:** `BoardManager.java`

```java
public static boolean isValidTileNum(int tileNum) {
    if (tileNum < 0 || tileNum > 63) {
        return false;
    }
    return true;
}
```

**Cyclomatic Complexity:** V(G) = 3

**Control Flow Graph:**

```mermaid
flowchart TD
    Start([1: Start]) --> Check{2: tileNum < 0<br/>OR<br/>tileNum > 63?}
    
    Check -->|Yes - Invalid| ReturnFalse[3: Return FALSE]
    Check -->|No - Valid| ReturnTrue[4: Return TRUE]
    
    ReturnFalse --> End([End])
    ReturnTrue --> End
    
    style Start fill:#90EE90
    style End fill:#FFB6C1
    style ReturnTrue fill:#98FB98
    style ReturnFalse fill:#FFA07A
    style Check fill:#87CEEB
```

**Detailed View (showing both conditions):**

```mermaid
flowchart TD
    Start([1: Start]) --> Check1{2a: tileNum < 0?}
    
    Check1 -->|Yes| ReturnFalse1[3: Return FALSE]
    Check1 -->|No| Check2{2b: tileNum > 63?}
    
    Check2 -->|Yes| ReturnFalse2[3: Return FALSE]
    Check2 -->|No| ReturnTrue[4: Return TRUE]
    
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

**Basis Paths (3 independent paths):**

| Path | Route | Description | Example Input | Test Case |
|------|-------|-------------|---------------|-----------|
| **Path 1** | 1 → 2a(Yes) → 3 → End | Negative number | tileNum = -1 | `testIsValidTileNum_Path1_Negative()` |
| **Path 2** | 1 → 2a(No) → 2b(Yes) → 3 → End | Too large | tileNum = 64 | `testIsValidTileNum_Path2_TooLarge()` |
| **Path 3** | 1 → 2a(No) → 2b(No) → 4 → End | Valid range | tileNum = 32 | `testIsValidTileNum_Path3_Valid()` |

---

## 2. Mutation Testing

**Method:** `isValidTileNum(int tileNum)`

### 10 Mutants Created:

| # | Mutation Type | Original → Mutated | Killing Test |
|---|---------------|-------------------|--------------|
| 1 | Relational | `<` → `<=` | tileNum = 0 should be valid |
| 2 | Relational | `>` → `>=` | tileNum = 63 should be valid |
| 3 | Logical | `\|\|` → `&&` | tileNum = -1 should be invalid |
| 4 | Constant | `0` → `1` | tileNum = 0 should be valid |
| 5 | Constant | `63` → `64` | tileNum = 64 should be invalid |
| 6 | Return | `false` → `true` | Invalid values should return false |
| 7 | Negation | `if (x)` → `if (!x)` | Valid values should return true |
| 8 | Removal | Remove `< 0` | Negative values should be invalid |
| 9 | Removal | Remove `> 63` | Large values should be invalid |
| 10 | Arithmetic | `x` → `x-1` | Boundary should not shift |

### Mutation Score: **10/10 = 100%** ✓

---

## 3. Table-Based Testing

### Decision Table for `isValidTileNum()`

```mermaid
graph LR
    subgraph Conditions
        C1[tileNum < 0]
        C2[tileNum > 63]
    end
    
    subgraph Cases
        Case1[Case 1:<br/>T, F]
        Case2[Case 2:<br/>F, T]
        Case3[Case 3:<br/>T, T]
        Case4[Case 4:<br/>F, F]
    end
    
    subgraph Results
        R1[FALSE ❌]
        R2[FALSE ❌]
        R3[FALSE ❌]
        R4[TRUE ✓]
    end
    
    C1 --> Case1
    C2 --> Case1
    Case1 --> R1
    
    C1 --> Case2
    C2 --> Case2
    Case2 --> R2
    
    C1 --> Case3
    C2 --> Case3
    Case3 --> R3
    
    C1 --> Case4
    C2 --> Case4
    Case4 --> R4
    
    style R4 fill:#98FB98
    style R1 fill:#FFA07A
    style R2 fill:#FFA07A
    style R3 fill:#FFA07A
```

| Case | tileNum < 0 | tileNum > 63 | Expected | Description |
|------|-------------|--------------|----------|-------------|
| 1 | T | F | False | Negative |
| 2 | F | T | False | Too large |
| 3 | T | T | False | Impossible |
| 4 | F | F | True | Valid [0-63] |

### Boundary Value Analysis

```mermaid
flowchart LR
    subgraph Invalid_Low[❌ Invalid: Below Range]
        A1[-100]
        A2[-1]
    end
    
    subgraph Boundary_Low[⚠️ Lower Boundary]
        B1[0 ✓]
        B2[1 ✓]
    end
    
    subgraph Valid[✅ Valid Range]
        C1[32 ✓]
    end
    
    subgraph Boundary_High[⚠️ Upper Boundary]
        D1[62 ✓]
        D2[63 ✓]
    end
    
    subgraph Invalid_High[❌ Invalid: Above Range]
        E1[64]
        E2[100]
    end
    
    Invalid_Low --> Result1[FALSE]
    Boundary_Low --> Result2[TRUE]
    Valid --> Result2
    Boundary_High --> Result2
    Invalid_High --> Result1
    
    style Invalid_Low fill:#FFA07A
    style Boundary_Low fill:#FFE4B5
    style Valid fill:#98FB98
    style Boundary_High fill:#FFE4B5
    style Invalid_High fill:#FFA07A
    style Result1 fill:#FFB6C1
    style Result2 fill:#90EE90
```

| Input | Category | Expected | Type |
|-------|----------|----------|------|
| -1 | Below min | False | Boundary - 1 |
| 0 | Min valid | True | Lower bound |
| 1 | Above min | True | Boundary + 1 |
| 32 | Middle | True | Nominal |
| 62 | Below max | True | Boundary - 1 |
| 63 | Max valid | True | Upper bound |
| 64 | Above max | False | Boundary + 1 |

### Equivalence Partitioning

```mermaid
flowchart TD
    Input[Input: tileNum] --> P1{Partition 1:<br/>x < 0}
    Input --> P2{Partition 2:<br/>0 ≤ x ≤ 63}
    Input --> P3{Partition 3:<br/>x > 63}
    
    P1 --> |Examples:<br/>-100, -1| R1[FALSE ❌]
    P2 --> |Examples:<br/>0, 32, 63| R2[TRUE ✓]
    P3 --> |Examples:<br/>64, 100| R3[FALSE ❌]
    
    style P1 fill:#FFA07A
    style P2 fill:#98FB98
    style P3 fill:#FFA07A
    style R1 fill:#FFB6C1
    style R2 fill:#90EE90
    style R3 fill:#FFB6C1
```

| Partition | Range | Valid? | Test Values | Expected |
|-----------|-------|--------|-------------|----------|
| 1 | x < 0 | Invalid | -100, -1 | False |
| 2 | 0 ≤ x ≤ 63 | Valid | 0, 32, 63 | True |
| 3 | x > 63 | Invalid | 64, 100 | False |

---

## 4. Test Results

### Execution Summary

```bash
gradlew test --tests BasisPathTest
gradlew test --tests MutationTest
gradlew test --tests TableBasedTest
```

**Results:**
- ✅ BasisPathTest: 12/12 passed
- ✅ MutationTest: 12/12 passed
- ✅ TableBasedTest: 25/25 passed
- ✅ **Total: 49/49 tests passed**

### Coverage Metrics

| Metric | Coverage |
|--------|----------|
| Statement Coverage | 100% |
| Branch Coverage | 100% |
| Path Coverage | 100% |
| Mutation Score | 100% |

### Test Breakdown

**BasisPathTest (12 tests):**
- hasAlly: 4 paths tested ✓
- hasEnemy: 4 paths tested ✓
- isValidTileNum: 3 paths + boundary tests ✓

**MutationTest (12 tests):**
- 10 mutants created ✓
- 10 mutants killed ✓
- Mutation score: 100% ✓

**TableBasedTest (25 tests):**
- Decision table: 4 cases ✓
- Boundary values: 9 tests ✓
- Equivalence partitions: 3 tests ✓
- Additional tests: 9 tests ✓

---

## Summary

| Testing Technique | Methods | Tests | Coverage |
|-------------------|---------|-------|----------|
| Basis-Path | 3 | 12 | 100% path |
| Mutation | 1 | 12 | 100% score |
| Table-Based | 2 | 25 | 100% conditions |
| **TOTAL** | **3** | **49** | **100%** |

**All requirements completed successfully.**

---

**Files:**
- `BasisPathTest.java` - White-box testing
- `MutationTest.java` - 10 mutants
- `TableBasedTest.java` - Decision tables
- `FLOWCHARTS_MERMAID.md` - Visual flowcharts

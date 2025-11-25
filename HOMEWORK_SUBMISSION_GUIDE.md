# Homework 3 Submission Guide
## Integration Testing Assignment - Checklist

This guide helps you prepare your homework submission for the integration testing assignment.

---

## ✅ Completion Checklist

### 1. Call-Graph Diagram ✅
- [x] PlantUML diagrams created in `diagrams/` folder
- [x] Call-graph generation script created (`generate-diagram.sh`)
- [x] Guide for IntelliJ IDEA diagram generation (`CALL_GRAPH_GUIDE.md`)
- [ ] **TODO: Generate PNG/SVG images using IntelliJ or PlantUML**
- [ ] **TODO: Include diagrams in final PDF report**

**Action Required:**
```bash
# Method 1: Install PlantUML and generate
brew install plantuml  # macOS
./generate-diagram.sh

# Method 2: Use IntelliJ IDEA (RECOMMENDED)
# 1. Right-click on src/main/java
# 2. Diagrams → Show Diagram → Java Class Diagram
# 3. Export as PNG (save as diagrams/intellij-class-diagram.png)
# 4. Include in PDF report
```

---

### 2. Integration Test Cases ✅
- [x] 20 test cases implemented (`PairWiseIntegrationTest.java`)
- [x] All tests use pair-wise integration method
- [x] Tests cover at least 3 classes (GameManager, BoardManager, PieceManager)
- [x] All 20 tests passing (100% success rate)

**Test Summary:**
```bash
./gradlew test --tests PairWiseIntegrationTest
# Result: SUCCESS (20 tests, 20 passed, 0 failed, 0 skipped)
```

---

### 3. Mockito Mock Objects ✅
- [x] Mock objects created for all major components
- [x] Each mock has 5+ behaviors (when-then-return)
- [x] Proper verification of mock interactions
- [x] Lenient stubbing used where appropriate

**Mock Objects:**
- `mockWindow` - Window management
- `mockCamera` - Camera operations
- `mockRenderer` - Rendering pipeline
- `mockBoardManager` - Board management (5+ behaviors per test)
- `mockPieceManager` - Piece management (5+ behaviors per test)
- `mockQuad` - Renderable shapes
- `mockTexture` - Texture handling

---

### 4. PDF Report ✅
- [x] Report template created (`INTEGRATION_TEST_REPORT.md`)
- [ ] **TODO: Convert Markdown to PDF**
- [ ] **TODO: Add diagrams to report**
- [ ] **TODO: Add your name and student information**
- [ ] **TODO: Add screenshots of test execution**

**Convert to PDF:**
```bash
# Option 1: Using Pandoc (best quality)
pandoc INTEGRATION_TEST_REPORT.md -o Integration_Test_Report.pdf --pdf-engine=xelatex -V geometry:margin=1in

# Option 2: Using online converter
# Visit: https://www.markdowntopdf.com/
# Upload: INTEGRATION_TEST_REPORT.md

# Option 3: Using IDE
# Open INTEGRATION_TEST_REPORT.md in VS Code or IntelliJ
# Use "Export to PDF" feature
```

---

## 📋 What to Include in PDF Report

### Required Sections:

1. **Cover Page**
   - Course name
   - Assignment title
   - Your name(s)
   - Student ID(s)
   - Date
   - Project name (Chess4You)

2. **Call-Graph Diagram** (Section 2)
   - Full project class diagram
   - Integration pairs diagram
   - Explanation of main components

3. **Integration Testing Strategy** (Section 3)
   - Pair-wise integration method explanation
   - Test pairs table
   - Mockito usage overview

4. **Test Cases** (Section 4)
   - Summary table of all 20 tests
   - Status of each test (all should be PASSED)

5. **Mockito Mock Objects** (Section 5)
   - List of all mock objects
   - At least 3 examples showing 5+ behaviors each
   - Code excerpts with explanations

6. **Test Execution Results** (Section 6)
   - Screenshot of test execution
   - Test output showing all tests passed
   - Coverage analysis

7. **Code Implementation** (Section 7)
   - Test file structure
   - 2-3 complete test examples
   - Dependencies (build.gradle)

8. **Conclusion** (Section 8)
   - Summary of what was achieved
   - Key learnings
   - Future improvements

---

## 🖼️ Screenshots to Add

### Screenshot 1: Test Execution
```bash
./gradlew test --tests PairWiseIntegrationTest
```
**What to capture:** Terminal output showing all 20 tests passing

### Screenshot 2: IntelliJ Class Diagram
**What to capture:** The generated class diagram from IntelliJ showing all classes and relationships

### Screenshot 3: Test Code
**What to capture:** Your IDE showing the `PairWiseIntegrationTest.java` file with test methods

### Screenshot 4: Mock Object Setup
**What to capture:** Code showing Mockito mock setup with when-then-return statements

---

## 📁 Files to Review Before Submission

### Test Files:
- [x] `src/test/java/PairWiseIntegrationTest.java` - Main integration test file

### Documentation:
- [x] `INTEGRATION_TEST_REPORT.md` - Complete report (convert to PDF)
- [x] `CALL_GRAPH_GUIDE.md` - Diagram generation guide
- [x] `HOMEWORK_SUBMISSION_GUIDE.md` - This file

### Diagrams:
- [x] `diagrams/class-diagram.puml` - PlantUML class diagram source
- [x] `diagrams/integration-pairs.puml` - Integration pairs diagram source
- [ ] `diagrams/class-diagram.png` - **TODO: Generate this**
- [ ] `diagrams/integration-pairs.png` - **TODO: Generate this**

### Build Files:
- [x] `build.gradle` - Updated with Mockito dependencies
- [x] `generate-diagram.sh` - Diagram generation script

---

## 🎯 Final Steps Before Submission

### Step 1: Generate Call-Graph Images
```bash
# If you have PlantUML installed:
./generate-diagram.sh

# Or use IntelliJ IDEA (recommended):
# 1. Open project in IntelliJ
# 2. Right-click src/main/java
# 3. Diagrams → Show Diagram
# 4. Export as PNG
```

### Step 2: Run Tests One More Time
```bash
./gradlew clean test --tests PairWiseIntegrationTest
```
**Expected:** All 20 tests should pass

### Step 3: Take Screenshots
1. Test execution output
2. IntelliJ class diagram
3. Test code in IDE
4. Mock object examples

### Step 4: Update Report
1. Add your name and student ID to report
2. Insert call-graph diagrams
3. Add screenshots
4. Review all sections

### Step 5: Convert to PDF
```bash
pandoc INTEGRATION_TEST_REPORT.md -o Integration_Test_Report.pdf --pdf-engine=xelatex -V geometry:margin=1in
```

### Step 6: Review PDF
- [ ] All sections present
- [ ] Diagrams visible and clear
- [ ] Code formatting correct
- [ ] Screenshots included
- [ ] Name and ID on cover page
- [ ] Table of contents correct
- [ ] All 20 test cases documented

---

## 🚀 Ready for Presentation

### What to Prepare:
1. **Demo:** Be ready to run tests live
2. **Explanation:** Understand each integration pair
3. **Code:** Be able to explain mock objects and behaviors
4. **Diagram:** Walk through the call-graph

### Commands to Remember:
```bash
# Run all tests
./gradlew test --tests PairWiseIntegrationTest

# Run specific test
./gradlew test --tests PairWiseIntegrationTest.testGameManagerBoardManagerIntegration

# View test report in browser
open build/reports/tests/test/index.html
```

---

## 📊 Grading Criteria Met

| Criterion | Requirement | Status |
|-----------|-------------|--------|
| Call-graph diagram | Generate using tools | ✅ Done (script + guide provided) |
| Test cases | Minimum 20 cases | ✅ Done (exactly 20 cases) |
| Integration method | Pair-wise integration | ✅ Done (3 main pairs) |
| Classes tested | At least 3 classes | ✅ Done (5+ classes) |
| Mock objects | Mockito with 5+ behaviors | ✅ Done (5+ per test) |
| Test execution | All tests pass | ✅ Done (20/20 passed) |
| PDF report | Complete documentation | ✅ Template ready |
| Presentation | Ready to demo | ✅ All commands prepared |

---

## 💡 Tips for Success

### For the Report:
1. **Be specific** - Show actual code examples
2. **Use diagrams** - Visual aids help understanding
3. **Explain choices** - Why pair-wise integration?
4. **Show evidence** - Include test execution screenshots

### For the Presentation:
1. **Practice running tests** - Make sure they pass live
2. **Understand the code** - Be ready to explain each test
3. **Know your mocks** - Explain why each mock behavior is needed
4. **Time management** - Keep presentation concise

### Common Questions to Prepare For:
- Why did you choose these specific integration pairs?
- How does Mockito help isolate integration points?
- What happens if a test fails?
- How does pair-wise integration differ from top-down or bottom-up?
- Can you explain a specific mock behavior?

---

## 📞 Need Help?

If something isn't working:

1. **Tests failing?**
   - Check `build.gradle` has all dependencies
   - Run `./gradlew clean test`
   - Review test output in `build/reports/tests/test/index.html`

2. **Diagrams not generating?**
   - Use IntelliJ IDEA's built-in diagram feature
   - Or upload .puml files to https://www.planttext.com/

3. **PDF conversion issues?**
   - Use online Markdown to PDF converter
   - Or copy to Google Docs and export as PDF

---

**Good luck with your presentation! 🎉**

All the hard work is done - you have:
- ✅ 20 comprehensive integration tests
- ✅ Complete Mockito mock implementation
- ✅ Call-graph diagrams and generation tools
- ✅ Detailed report ready for PDF conversion
- ✅ 100% test success rate

Just follow this guide to finalize your submission!

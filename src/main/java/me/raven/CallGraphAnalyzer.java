package me.raven;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Analyzes Java source code to generate a call graph showing method-to-method calls.
 * Outputs the graph in DOT format (GraphViz) for visualization.
 */
public class CallGraphAnalyzer {

    private Map<String, Set<String>> callGraph = new LinkedHashMap<>();
    private Map<String, String> methodToClass = new HashMap<>();
    private Set<String> processedFiles = new HashSet<>();

    public static void main(String[] args) {
        String sourceDir = args.length > 0 ? args[0] : "src/main/java";
        String outputDir = args.length > 1 ? args[1] : "diagrams";
        String entryPoint = args.length > 2 ? args[2] : "me.raven.Main.main";

        System.out.println("Chess4You Call Graph Analyzer");
        System.out.println("==============================");
        System.out.println("Source directory: " + sourceDir);
        System.out.println("Output directory: " + outputDir);
        System.out.println("Entry point: " + entryPoint);
        System.out.println();

        CallGraphAnalyzer analyzer = new CallGraphAnalyzer();

        try {
            // Analyze source code
            analyzer.analyzeDirectory(sourceDir);

            // Create output directory if it doesn't exist
            new File(outputDir).mkdirs();

            // Generate full call graph
            String dotFile = outputDir + "/call-graph-full.dot";
            analyzer.generateDotFile(dotFile, null);
            System.out.println("Generated full call graph: " + dotFile);

            // Generate call graph starting from entry point
            String dotFileFromMain = outputDir + "/call-graph-from-main.dot";
            analyzer.generateDotFile(dotFileFromMain, entryPoint);
            System.out.println("Generated call graph from " + entryPoint + ": " + dotFileFromMain);

            // Generate PlantUML
            String plantUMLFile = outputDir + "/call-graph.puml";
            analyzer.generatePlantUML(plantUMLFile, entryPoint);
            System.out.println("Generated PlantUML diagram: " + plantUMLFile);

            // Generate text representation
            String textFile = outputDir + "/call-graph.txt";
            analyzer.generateTextFile(textFile, entryPoint);
            System.out.println("Generated text call graph: " + textFile);

            System.out.println();
            System.out.println("To visualize the DOT files:");
            System.out.println("  1. Install GraphViz: brew install graphviz (macOS)");
            System.out.println("  2. Generate PNG: dot -Tpng " + dotFileFromMain + " -o " + outputDir + "/call-graph.png");
            System.out.println("  3. Generate SVG: dot -Tsvg " + dotFileFromMain + " -o " + outputDir + "/call-graph.svg");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Analyzes all Java files in the given directory
     */
    public void analyzeDirectory(String sourcePath) throws IOException {
        Path startPath = Paths.get(sourcePath);

        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.filter(Files::isRegularFile)
                 .filter(p -> p.toString().endsWith(".java"))
                 .forEach(this::analyzeFile);
        }

        System.out.println("Analyzed " + processedFiles.size() + " Java files");
        System.out.println("Found " + callGraph.size() + " methods with calls");
        System.out.println();
    }

    /**
     * Analyzes a single Java file
     */
    private void analyzeFile(Path filePath) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(filePath);
            processedFiles.add(filePath.toString());

            new MethodVisitor().visit(cu, null);

        } catch (IOException e) {
            System.err.println("Failed to parse: " + filePath + " - " + e.getMessage());
        }
    }

    /**
     * Visitor that extracts method calls from each method
     */
    private class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(ClassOrInterfaceDeclaration classDecl, Void arg) {
            super.visit(classDecl, arg);

            String className = classDecl.getFullyQualifiedName().orElse(classDecl.getNameAsString());

            classDecl.getMethods().forEach(method -> {
                String methodName = method.getNameAsString();
                String fullMethodName = className + "." + methodName;

                methodToClass.put(fullMethodName, className);
                callGraph.putIfAbsent(fullMethodName, new LinkedHashSet<>());

                // Track variable types within this method
                Map<String, String> variableTypes = new HashMap<>();
                method.findAll(com.github.javaparser.ast.body.VariableDeclarator.class).forEach(varDecl -> {
                    String varName = varDecl.getNameAsString();
                    String declaredType = varDecl.getType().asString();

                    // Check if it's initialized with 'new ClassName()'
                    if (varDecl.getInitializer().isPresent()) {
                        String init = varDecl.getInitializer().get().toString();
                        if (init.startsWith("new ") && init.contains("(")) {
                            int startIdx = 4;
                            int endIdx = init.indexOf('(');
                            if (endIdx > startIdx) {
                                String type = init.substring(startIdx, endIdx).trim();
                                variableTypes.put(varName, type);
                            }
                        }
                    }

                    // Use declared type as fallback
                    if (!variableTypes.containsKey(varName)) {
                        variableTypes.put(varName, declaredType);
                    }
                });

                // Find all method calls within this method
                method.findAll(MethodCallExpr.class).forEach(call -> {
                    // Try to resolve the full qualified name
                    String calledFullName = resolveMethodCall(call, className, variableTypes);

                    callGraph.get(fullMethodName).add(calledFullName);
                });
            });
        }

        private String resolveMethodCall(MethodCallExpr call, String currentClass, Map<String, String> variableTypes) {
            String methodName = call.getNameAsString();

            // Check if it has a scope (e.g., SomeClass.method() or object.method())
            if (call.getScope().isPresent()) {
                String scope = call.getScope().get().toString();

                // Handle singleton pattern: ClassName.get().method()
                if (scope.contains(".get()")) {
                    String singletonClass = scope.replace(".get()", "");
                    return findMethodInClass(singletonClass, methodName);
                }

                // Handle instance method calls: variable.method()
                if (variableTypes.containsKey(scope)) {
                    String className = variableTypes.get(scope);
                    return findMethodInClass(className, methodName);
                }

                // Try to resolve as a class name directly
                return findMethodInClass(scope, methodName);
            }

            // No scope - might be a call within the same class
            String sameClassCall = currentClass + "." + methodName;
            if (methodToClass.containsKey(sameClassCall)) {
                return sameClassCall;
            }

            // Unknown class - just use method name
            return methodName;
        }

        private String findMethodInClass(String className, String methodName) {
            // Try exact match first
            String exactMatch = className + "." + methodName;
            if (methodToClass.containsKey(exactMatch)) {
                return exactMatch;
            }

            // Try to find the class in our analyzed classes
            for (String fullMethodName : methodToClass.keySet()) {
                if (fullMethodName.endsWith("." + className + "." + methodName)) {
                    return fullMethodName;
                }
                if (fullMethodName.contains("." + className + ".") && fullMethodName.endsWith("." + methodName)) {
                    return fullMethodName;
                }
            }

            // Couldn't resolve - return className.methodName format
            return className + "." + methodName;
        }
    }

    /**
     * Generates a DOT file for GraphViz visualization
     */
    public void generateDotFile(String outputPath, String entryPoint) throws IOException {
        Set<String> visited = new HashSet<>();
        Set<String> methodsToInclude = new HashSet<>();

        if (entryPoint != null) {
            // Build subset starting from entry point
            buildReachableSet(entryPoint, methodsToInclude);
        } else {
            // Include all methods
            methodsToInclude.addAll(callGraph.keySet());
        }

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("digraph CallGraph {\n");
            writer.write("  rankdir=LR;\n");
            writer.write("  node [shape=box, style=rounded];\n");
            writer.write("  graph [fontname=\"Arial\", fontsize=10];\n");
            writer.write("  node [fontname=\"Arial\", fontsize=10];\n");
            writer.write("  edge [fontname=\"Arial\", fontsize=9];\n\n");

            // Color code by package
            Map<String, String> packageColors = new HashMap<>();
            packageColors.put("me.raven.Engine", "#E3F2FD");
            packageColors.put("me.raven.Sandbox", "#FFF3E0");
            packageColors.put("me.raven.Sandbox.Game", "#F3E5F5");
            packageColors.put("me.raven", "#E8F5E9");

            // Track all methods (including external ones) that should be included
            Set<String> allMethodsInGraph = new HashSet<>(methodsToInclude);
            if (entryPoint != null) {
                // Add external methods that are called by included methods
                for (String method : methodsToInclude) {
                    Set<String> calls = callGraph.getOrDefault(method, Collections.emptySet());
                    allMethodsInGraph.addAll(calls);
                }
            }

            for (String method : allMethodsInGraph) {
                Set<String> calls = callGraph.getOrDefault(method, Collections.emptySet());

                String color = getColorForMethod(method, packageColors);
                String label = formatMethodLabel(method);

                // Mark external methods with different style
                boolean isExternal = !methodToClass.containsKey(method);
                String style = isExternal ? "rounded,filled,dashed" : "rounded,filled";
                String fillColor = isExternal ? "#F5F5F5" : color;

                writer.write("  \"" + method + "\" [label=\"" + label + "\", fillcolor=\"" + fillColor + "\", style=\"" + style + "\"];\n");

                for (String calledMethod : calls) {
                    if (entryPoint == null || allMethodsInGraph.contains(calledMethod)) {
                        writer.write("  \"" + method + "\" -> \"" + calledMethod + "\";\n");
                    }
                }
            }

            writer.write("}\n");
        }
    }

    /**
     * Builds the set of reachable methods from a given entry point (BFS)
     */
    private void buildReachableSet(String entryPoint, Set<String> reachable) {
        Queue<String> queue = new LinkedList<>();
        queue.add(entryPoint);
        reachable.add(entryPoint);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Set<String> calls = callGraph.getOrDefault(current, Collections.emptySet());

            for (String called : calls) {
                if (!reachable.contains(called) && callGraph.containsKey(called)) {
                    reachable.add(called);
                    queue.add(called);
                }
            }
        }
    }

    /**
     * Gets a color for a method based on its package
     */
    private String getColorForMethod(String method, Map<String, String> packageColors) {
        for (Map.Entry<String, String> entry : packageColors.entrySet()) {
            if (method.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "#FFFFFF";
    }

    /**
     * Formats a method name for display
     */
    private String formatMethodLabel(String fullMethodName) {
        // Show only class and method name, not full package
        int lastDot = fullMethodName.lastIndexOf('.');
        if (lastDot > 0) {
            int secondLastDot = fullMethodName.lastIndexOf('.', lastDot - 1);
            if (secondLastDot > 0) {
                return fullMethodName.substring(secondLastDot + 1);
            }
        }
        return fullMethodName;
    }

    /**
     * Generates a PlantUML file
     */
    public void generatePlantUML(String outputPath, String entryPoint) throws IOException {
        Set<String> methodsToInclude = new HashSet<>();

        if (entryPoint != null) {
            buildReachableSet(entryPoint, methodsToInclude);
        } else {
            methodsToInclude.addAll(callGraph.keySet());
        }

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("@startuml\n");
            writer.write("!theme plain\n");
            writer.write("left to right direction\n\n");

            for (String method : methodsToInclude) {
                Set<String> calls = callGraph.getOrDefault(method, Collections.emptySet());

                for (String calledMethod : calls) {
                    if (methodsToInclude.contains(calledMethod)) {
                        String fromLabel = formatMethodLabel(method);
                        String toLabel = formatMethodLabel(calledMethod);
                        writer.write("[" + fromLabel + "] --> [" + toLabel + "]\n");
                    }
                }
            }

            writer.write("\n@enduml\n");
        }
    }

    /**
     * Generates a text-based tree representation
     */
    public void generateTextFile(String outputPath, String entryPoint) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("Call Graph Tree\n");
            writer.write("===============\n\n");

            if (entryPoint != null && callGraph.containsKey(entryPoint)) {
                Set<String> visited = new HashSet<>();
                writeMethodTree(writer, entryPoint, 0, visited);
            } else {
                writer.write("Entry point not found: " + entryPoint + "\n");
            }
        }
    }

    /**
     * Recursively writes method call tree
     */
    private void writeMethodTree(FileWriter writer, String method, int depth, Set<String> visited) throws IOException {
        String indent = "  ".repeat(depth);
        writer.write(indent + "- " + formatMethodLabel(method) + "\n");

        if (visited.contains(method)) {
            writer.write(indent + "  (already visited)\n");
            return;
        }

        visited.add(method);

        Set<String> calls = callGraph.getOrDefault(method, Collections.emptySet());
        for (String called : calls) {
            if (callGraph.containsKey(called)) {
                writeMethodTree(writer, called, depth + 1, visited);
            } else {
                writer.write(indent + "  - " + formatMethodLabel(called) + " (external)\n");
            }
        }
    }
}

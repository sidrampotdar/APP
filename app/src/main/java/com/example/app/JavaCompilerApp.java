package com.example.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.tools.*;
//
public class JavaCompilerApp {

    public static void compileAndRunJavaCode(String sourceCode) {
        try {
            // Prepare the source code file
            File sourceFile = new File("Main.java");
            FileWriter fileWriter = new FileWriter(sourceFile);
            fileWriter.write(sourceCode);
            fileWriter.close();

            // Get the Java compiler
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            // Get the file manager
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

            // Prepare the compilation options
            List<String> options = new ArrayList<>();
            options.add("-classpath");
            options.add(System.getProperty("java.class.path"));

            // Prepare the diagnostic collector
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

            // Get the compilation task
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(List.of(sourceFile));
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);

            // Perform the compilation
            boolean success = task.call();

            // Check for compilation errors
            if (success) {
                // Create a new class loader
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("").toURI().toURL()});

                // Load the compiled class
                Class<?> clazz = Class.forName("Main", true, classLoader);

                // Invoke the main method
                Method method = clazz.getDeclaredMethod("main", String[].class);
                method.invoke(null, new Object[]{null});
            } else {
                // Print compilation errors
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    System.out.println(diagnostic.getMessage(null));
                }
            }

            // Clean up - delete the source file
            sourceFile.delete();

        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

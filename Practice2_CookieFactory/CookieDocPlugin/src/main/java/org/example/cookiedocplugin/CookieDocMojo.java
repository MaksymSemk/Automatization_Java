package org.example.cookiedocplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "generate-docs")
public class CookieDocMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.basedir}", required = true)
    private File sourceDirectory;

    @Parameter(defaultValue = "cookie-docs.md", required = true)
    private File outputFile;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Generating documentation from Java source files4...");

        try {
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }

            List<File> javaFiles = new ArrayList<>();

            collectJavaFiles(sourceDirectory, javaFiles);
            PrintWriter writer = new PrintWriter(outputFile);
            for (File file : javaFiles) {
                if(file.getName().equals("CookieDocMojo.java")) continue;
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                writer.println("File: "+ file.getName()+"\n");
                while ((line = reader.readLine()) != null) {
                    if (line.contains("//Doc")) {
                        writer.println("@Doc " + line.trim().replace("//Doc", ""));
                        writer.println("Func signature: "+ reader.readLine().trim().replace("{", ""));
                        writer.println("\n");
                    }
                }
                writer.println("---------\n");
                reader.close();
            }
            writer.close();

            getLog().info("Documentation generated at: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new MojoExecutionException("Error generating documentation", e);
        }
    }

    private void collectJavaFiles(File dir, List<File> result) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                collectJavaFiles(file, result);
            } else if (file.isFile() && file.getName().endsWith(".java")) {
                result.add(file);
            }
        }
    }
}

package org.example;

import java.io.IOException;
import java.nio.file.*;

public class App {
  public static void main(String[] args) {
    if (args.length < 1 || args.length > 2) {
      System.err.println("Usage: java -jar app.jar <input.txt> [output.json]");
      System.exit(1);
    }

    Path input = Paths.get(args[0]);
    if (!Files.isRegularFile(input)) {
      System.err.println("Input file not found: " + input.toAbsolutePath());
      System.exit(2);
    }

    Path output = (args.length == 2) ? Paths.get(args[1]) : input.resolveSibling(input.getFileName().toString() + ".index.json");

    try {
      InvertedIndex index = InvertedIndex.fromFile(input, StopWords.basic());
      Files.writeString(output, index.toJson(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      System.out.println("Wrote inverted index -> " + output.toAbsolutePath());
    } catch (IOException e) {
      System.err.println("I/O error: " + e.getMessage());
      System.exit(3);
    }
  }
}

package org.example;

import java.util.*;

public final class StopWords {
  private StopWords() {}

  private static final Set<String> BASIC = Set.of(
      "a","an","and","are","as","at","be","by","for","from","has","he","in","is","it",
      "its","of","on","that","the","their","them","they","this","to","was","were","will",
      "with","you","your","i","we","our","or","but","so","if","then","than","there","here",
      "she","him","her","it’s","it's"
  );

  public static Set<String> basic() {
    return BASIC;
  }
}

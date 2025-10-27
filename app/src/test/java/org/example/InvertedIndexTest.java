package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InvertedIndexTest {

  @Test
  void buildsExampleIndex() {
    String text = "The brown cat and the blue rat live in the brown house. " + "They enjoy live music concerts.";
    InvertedIndex idx = InvertedIndex.fromString(text, StopWords.basic());

    Map<String, List<Integer>> map = idx.asMapSorted();

    assertEquals(List.of(5), map.get("blue"));
    assertEquals(List.of(1, 10), map.get("brown"));
    assertEquals(List.of(2), map.get("cat"));
    assertEquals(List.of(16), map.get("concerts"));
    assertEquals(List.of(13), map.get("enjoy"));
    assertEquals(List.of(11), map.get("house"));
    assertEquals(List.of(7, 14), map.get("live"));
    assertEquals(List.of(15), map.get("music"));
    assertEquals(List.of(6), map.get("rat"));
    assertFalse(map.containsKey("the")); // stopword
    assertFalse(map.containsKey("and")); // stopword
  }

  @Test
  void treatsHyphenatedAsSingleToken() {
    String text = "A well-known high-quality cat.";
    InvertedIndex idx = InvertedIndex.fromString(text, StopWords.basic());
    Map<String, List<Integer>> map = idx.asMapSorted();

    assertTrue(map.containsKey("well-known"));
    assertTrue(map.containsKey("high-quality"));
  }
}

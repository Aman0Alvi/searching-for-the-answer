package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InvertedIndex {
  private static final Pattern TOKEN = Pattern.compile("\\b[0-9a-zA-Z]+(?:-[0-9a-zA-Z]+)*\\b");
  private final Map<String, List<Integer>> postings = new HashMap<>();

  public static InvertedIndex fromFile(Path path, Set<String> stopwords) throws IOException {
    String text = Files.readString(path, StandardCharsets.UTF_8);
    return fromString(text, stopwords);
  }

  public static InvertedIndex fromString(String text, Set<String> stopwords) {
    InvertedIndex idx = new InvertedIndex();

    Matcher m = TOKEN.matcher(text.toLowerCase(Locale.ROOT));
    int position = 0;
    while (m.find()) {
      String token = m.group();
      if (!stopwords.contains(token)) {
        idx.postings.computeIfAbsent(token, k -> new ArrayList<>()).add(position);
      }
      position++;
    }
    return idx;
  }

  public Map<String, List<Integer>> asMapSorted() {
    return postings.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (a, b) -> a,
        LinkedHashMap::new
     ));
  }

  public String toJson() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    Iterator<Map.Entry<String, List<Integer>>> it = asMapSorted().entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, List<Integer>> e = it.next();
      sb.append("  \"").append(e.getKey()).append("\": [");
      for (int i = 0; i < e.getValue().size(); i++) {
        if (i > 0) sb.append(", ");
        sb.append(e.getValue().get(i));
      }
      sb.append("]");
      if (it.hasNext()) sb.append(",");
      sb.append("\n");
    }
    sb.append("}\n");
    return sb.toString();
  }
}

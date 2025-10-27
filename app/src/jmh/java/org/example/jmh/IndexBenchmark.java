package org.example.jmh;

import org.example.InvertedIndex;
import org.example.StopWords;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Benchmark)
public class IndexBenchmark {
  @Param({"1000","10000","100000"})
  int tokens;

  String corpus;

  @Setup
  public void setup() {
    String base = "the brown-cat and the blue rat live in the brown house they enjoy live music concerts high-quality well-known ";
    StringBuilder sb = new StringBuilder(tokens * 10);
    int count = 0;
    while (count < tokens) {
      sb.append(base);
      count += base.split("\\s+").length;
    }
    corpus = sb.toString();
    new String(corpus.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
  }

  @Benchmark
  public void buildIndex(Blackhole bh) {
    InvertedIndex idx = InvertedIndex.fromString(corpus, StopWords.basic());
    bh.consume(idx);
  }
}

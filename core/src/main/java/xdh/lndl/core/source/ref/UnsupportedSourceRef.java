package xdh.lndl.core.source.ref;

public class UnsupportedSourceRef extends RuntimeException {
  public UnsupportedSourceRef(SourceRef<?> actual, Class<?> expected) {
    super("Expected " + expected.getName() + ", received " + actual.getClass().getName());
  }
}

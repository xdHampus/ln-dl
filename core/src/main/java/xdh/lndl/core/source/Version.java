package xdh.lndl.core.source;

/**
 * Semantic versioning.
 *
 * @param major major version
 * @param minor minor version
 * @param patch patch version
 */
public record Version(

      int major,
      int minor,
      int patch
)  implements Comparable<Version> {

  /**
   * Semantic versioning.
   *
   * @param major major version
   * @param minor minor version
   * @param patch patch version
   * @exception IllegalArgumentException version number must be non-negative
   */
  public Version {
    if (major < 0 || minor < 0 || patch < 0) {
      throw new IllegalArgumentException("Version number must be non-negative");
    }

  }

  @Override
  public int compareTo(Version o) {
    int comparison = Integer.compare(major, o.major);
    if (comparison != 0) {
      return comparison;
    }
    comparison = Integer.compare(minor, o.minor);
    if (comparison != 0) {
      return comparison;
    }
    return Integer.compare(patch, o.patch);
  }

  @Override
  public String toString() {
    return major + "." + minor + "." + patch;
  }
}

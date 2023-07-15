package xdh.lndl.core.source.ref;

import xdh.lndl.core.persistence.Entity;

/**
 * A source reference for a html source.
 */

public interface HttpSourceRef<T extends Entity> extends SourceRef<T> {
  String getPath();
}

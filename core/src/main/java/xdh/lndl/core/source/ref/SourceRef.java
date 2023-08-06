package xdh.lndl.core.source.ref;

import xdh.lndl.core.fetching.RetrievalStatus;
import xdh.lndl.core.persistence.Entity;

/**
 * A reference to where something was sourced by a specific source.
 */
public interface SourceRef<T extends Entity> extends Entity {
  String getSourceId();

  RetrievalStatus getRetrievalStatus();
}

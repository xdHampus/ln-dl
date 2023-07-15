package xdh.lndl.core.source.ref;

import lombok.AllArgsConstructor;
import lombok.Builder;
import xdh.lndl.core.data.Chapter;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.fetching.RetrievalStatus;
import xdh.lndl.core.persistence.Entity;
import xdh.lndl.core.source.Source;

import java.util.UUID;

/**
 * A reference to where something was sourced by a specific source.
 */
public interface SourceRef<T extends Entity> extends Entity {
  String getSourceId();

  RetrievalStatus getRetrievalStatus();
}

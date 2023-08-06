package xdh.lndl.core.persistence;

import java.io.Serializable;
import java.util.UUID;

/**
 * An entity that may be persisted.
 */
public interface Entity extends Serializable {
  UUID getId();

  void setId(UUID id);
}

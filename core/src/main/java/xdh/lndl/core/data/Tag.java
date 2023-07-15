package xdh.lndl.core.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

import java.util.UUID;

/**
 * Any tag that can be related to a novel.
 */
@Data
@NoArgsConstructor
public final class Tag implements Entity {
  private UUID id;
  private String category;
  private String tag;
}

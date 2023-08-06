package xdh.lndl.core.data;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

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

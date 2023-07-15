package xdh.lndl.core.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

import java.util.UUID;

/**
 * A novel's ranking.
 */
@Data
@NoArgsConstructor
public final class Ranking implements Entity {
  private UUID id;
  private int allTime = -1;
  private int weekly = -1;
  private int monthly = -1;
  private int yearly = -1;

}

package xdh.lndl.core.data;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

/**
 * A volume of chapters.
 */
@Data
@NoArgsConstructor
public final class Volume implements Entity {
  private UUID id;
  private int index = -1;
  private String title;

  public void setIndex(int index) {
    this.index = index;
  }
}

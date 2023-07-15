package xdh.lndl.core.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

import java.util.UUID;

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

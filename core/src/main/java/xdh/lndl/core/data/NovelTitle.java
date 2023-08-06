package xdh.lndl.core.data;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

/**
 * Novel title.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class NovelTitle implements Entity {
  private UUID id;
  private String title;
  private String abbreviation;
  private List<String> alternativeTitles;

}

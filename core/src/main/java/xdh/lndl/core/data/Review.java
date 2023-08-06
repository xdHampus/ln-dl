package xdh.lndl.core.data;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

/**
 * A review of a novel.
 */
@Data
@NoArgsConstructor
public final class Review implements Entity {
  private UUID id;
  private String author;
  private String message;
  private Rating rating;
  private List<Comment> replies;
}

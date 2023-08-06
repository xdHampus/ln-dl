package xdh.lndl.core.data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

/**
 * Comment attached to novel or chapter.
 */
@Data
@NoArgsConstructor
public final class Comment implements Entity {
  private UUID id;
  private String author;
  private String message;
  private Comment responseTo;
  private List<Comment> respondents;
  private Instant releaseDate;
}

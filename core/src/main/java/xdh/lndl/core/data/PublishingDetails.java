package xdh.lndl.core.data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

/**
 * Information related to the publishing of a novel.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class PublishingDetails implements Entity {
  private UUID id;
  private List<String> publishers;
  private Instant publishingDate;

}

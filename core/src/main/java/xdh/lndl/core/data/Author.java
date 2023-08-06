package xdh.lndl.core.data;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;
import xdh.lndl.core.source.ref.SourceRef;

/**
 * Novel author.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Author implements Entity {
  private UUID id;
  private String name;
  private SourceRef<Author> sourceRef;

}

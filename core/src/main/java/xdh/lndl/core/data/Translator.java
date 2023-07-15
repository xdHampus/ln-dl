package xdh.lndl.core.data;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;
import xdh.lndl.core.source.ref.SourceRef;

/**
 * Novel translator.
 */
@Data
@NoArgsConstructor
public final class Translator implements Entity {
  private UUID id;
  private String name;
  private SourceRef<Translator> sourceRef;

}

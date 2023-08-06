package xdh.lndl.core.source.ref;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.fetching.RetrievalStatus;
import xdh.lndl.core.persistence.Entity;

/**
 * A basic implementation of a html source ref.
 *
 * @param <T> referenced type
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class BasicHttpSourceRef<T extends Entity> implements HttpSourceRef<T> {
  private UUID id;
  private String sourceId;
  private String path;
  private RetrievalStatus retrievalStatus = RetrievalStatus.NOT_RETRIEVED;

}

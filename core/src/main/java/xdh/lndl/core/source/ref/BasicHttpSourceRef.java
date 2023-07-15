package xdh.lndl.core.source.ref;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xdh.lndl.core.data.Chapter;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.fetching.RetrievalStatus;
import xdh.lndl.core.persistence.Entity;
import xdh.lndl.core.source.Source;

import java.util.UUID;

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

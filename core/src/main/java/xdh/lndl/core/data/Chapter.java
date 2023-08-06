package xdh.lndl.core.data;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.content.ChapterContent;
import xdh.lndl.core.persistence.Entity;
import xdh.lndl.core.source.ref.SourceRef;

/**
 * Novel chapter.
 */
@Data
@NoArgsConstructor
public final class Chapter implements Entity {
  private UUID id;
  private String title;
  private ChapterContent chapterContent;
  private SourceRef<Chapter> sourceRef;
  private Volume volume;
}

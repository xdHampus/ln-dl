package xdh.lndl.core.source;

import java.io.IOException;
import xdh.lndl.core.content.Image;
import xdh.lndl.core.data.Chapter;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.persistence.Entity;
import xdh.lndl.core.source.ref.SourceRef;
import xdh.lndl.core.source.ref.UnsupportedSourceRef;

/**
 * A source for novels.
 */
public interface Source extends Entity {

  default String getSourceId() {
    return this.getClass().getName();
  }

  String getTitle();

  Image getIcon();

  Version getVersion();

  Novel getNovelBySourceRef(SourceRef<Novel> novelRef) throws IOException, UnsupportedSourceRef;

  Chapter getChapterBySourceRef(SourceRef<Chapter> chapterRef) throws IOException, UnsupportedSourceRef;

}

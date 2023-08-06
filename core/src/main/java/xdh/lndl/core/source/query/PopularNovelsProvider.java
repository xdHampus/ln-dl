package xdh.lndl.core.source.query;

import java.util.List;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.util.Pageable;

/**
 * Provider of novels by popularity.
 */
public interface PopularNovelsProvider {
  List<Novel> getPopularNovels(Pageable pageable);
}

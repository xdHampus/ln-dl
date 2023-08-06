package xdh.lndl.core.source.query;

import xdh.lndl.core.data.Novel;

import xdh.lndl.core.util.Pageable;
import java.util.List;

/**
 * Provider of novels by popularity.
 */
public interface PopularNovelsProvider {
  List<Novel> getPopularNovels(Pageable pageable);
}

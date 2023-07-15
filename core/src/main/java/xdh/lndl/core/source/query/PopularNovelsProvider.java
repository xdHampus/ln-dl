package xdh.lndl.core.source.query;

import xdh.lndl.core.data.Novel;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Provider of novels by popularity.
 */
public interface PopularNovelsProvider {
  List<Novel> getPopularNovels(Pageable pageable);
}

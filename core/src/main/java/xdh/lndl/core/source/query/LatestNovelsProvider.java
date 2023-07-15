package xdh.lndl.core.source.query;


import java.awt.print.Pageable;
import java.util.List;
import xdh.lndl.core.data.Novel;

/**
 * Provider of novels sorted by latest.
 */
public interface LatestNovelsProvider {
  List<Novel> getLatestNovels(Pageable pageable);
}

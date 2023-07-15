package xdh.lndl.core.source.query;

import java.awt.print.Pageable;
import java.util.List;
import xdh.lndl.core.data.Novel;

/**
 * Provider of novels by genre.
 */
public interface GenreNovelsProvider {
  /**
   * Get novels by genre.
   *
   * @param genre genre
   * @param pageable pageable
   * @return novels
   */
  List<Novel> getNovelsByGenre(String genre, Pageable pageable);

}

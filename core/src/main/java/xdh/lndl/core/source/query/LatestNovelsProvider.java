package xdh.lndl.core.source.query;


import java.io.IOException;
import java.util.List;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.util.Page;
import xdh.lndl.core.util.Pageable;
/**
 * Provider of novels sorted by latest.
 */
public interface LatestNovelsProvider {
  Page<Novel> getLatestNovels(Pageable pageable) throws IOException;
}

package xdh.lndl.core.source.query;

import java.util.List;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.util.Pageable;

public interface Searchable {
  List<Novel> searchForNovels(SearchQuery searchQuery, Pageable pageable);

}

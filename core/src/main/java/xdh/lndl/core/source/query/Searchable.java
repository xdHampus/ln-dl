package xdh.lndl.core.source.query;

import xdh.lndl.core.data.Novel;

import java.awt.print.Pageable;
import java.util.List;

public interface Searchable {
  List<Novel> searchForNovels(SearchQuery searchQuery, Pageable pageable);

}

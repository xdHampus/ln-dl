package xdh.lndl.core.util;

import java.util.List;
import lombok.Data;

@Data
public class Page<T> {
  private int currentPage;
  private int lastPage;
  private List<T> elements;
}

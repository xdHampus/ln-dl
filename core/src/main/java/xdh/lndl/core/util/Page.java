package xdh.lndl.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
  private int currentPage;
  private int lastPage;
  private List<T> elements;
}

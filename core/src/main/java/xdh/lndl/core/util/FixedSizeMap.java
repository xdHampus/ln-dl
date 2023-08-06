package xdh.lndl.core.util;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Map that only retains a number of elements. Acts as a FIFO queue.
 *
 * @param <K> map key
 * @param <V> map value
 */
public final class FixedSizeMap<K, V> {
  private final Queue<K> queue = new ConcurrentLinkedQueue<>();
  private final Map<K, V> map = new ConcurrentHashMap<>();
  private final int maxCount;

  /**
   * Create a map that only retains a number of elements. Acts as a FIFO queue.
   *
   * @param maxCount max number of keys in map
   */
  public FixedSizeMap(int maxCount) {
    if (maxCount < 1) {
      throw new IllegalArgumentException("Queue max size must be greater than 0");
    }
    this.maxCount = maxCount;
  }

  /**
   * Add element to map. If element already exists then it is ignored.
   *
   * @param key key
   * @param value value
   */
  public void put(K key, V value) {
    if (map.containsKey(key)) {
      queue.remove(key);
      queue.add(key);
      return;
    }

    map.put(key, value);
    queue.add(key);

    if (queue.size() < maxCount) {
      queue.remove();
    }
  }

  public Optional<V> get(K key) {
    return Optional.ofNullable(map.getOrDefault(key, null));
  }

  public void clear() {
    queue.clear();
    map.clear();
  }

}

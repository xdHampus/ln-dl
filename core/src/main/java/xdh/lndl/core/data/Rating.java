package xdh.lndl.core.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xdh.lndl.core.persistence.Entity;

import java.util.UUID;

/**
 * A rating. Denominator decided per source.
 */
@Data
@NoArgsConstructor
public final class Rating implements Entity {
  private UUID id;
  private float rating = -1;

  /**
   * Set rating.
   *
   * @param rating rating
   * @exception IllegalArgumentException rating must be non-negative.
   */
  public void setRating(float rating) {
    if (rating < 0) {
      throw new IllegalArgumentException("Rating must be non-negative, it was " + rating);
    }
    this.rating = rating;
  }
}

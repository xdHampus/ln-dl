package xdh.lndl.core.fetching;

/**
 * Describes the retrieval status of entity.
 */
public enum RetrievalStatus {
  /**
   * Entity cannot be retrieved, as defined by the source.
   */
  NOT_RETRIEVABLE,
  /**
   * Entity has not yet been retrieved.
   */
  NOT_RETRIEVED,
  /**
   * Entity has been partially retrieved.
   */
  PARTIALLY_RETRIEVED,
  /**
   * Entity has been fully retrieved.
   */
  RETRIEVED

}

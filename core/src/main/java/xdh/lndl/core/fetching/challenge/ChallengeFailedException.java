package xdh.lndl.core.fetching.challenge;

public class ChallengeFailedException extends RuntimeException {
  public ChallengeFailedException(String message) {
    super(message);
  }
}

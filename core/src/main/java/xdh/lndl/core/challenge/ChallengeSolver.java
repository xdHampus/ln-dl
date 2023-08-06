package xdh.lndl.core.challenge;

import lombok.Getter;
import org.htmlunit.WebClient;
import org.htmlunit.WebResponse;
import org.htmlunit.html.HtmlPage;
import xdh.lndl.core.fetching.WebDriver;

import java.io.IOException;
import java.net.URL;

public abstract class ChallengeSolver {
  @Getter
  protected final int maxRetries;

  protected ChallengeSolver(int maxRetries) {
    this.maxRetries = maxRetries;
  }

  public abstract boolean challengePresent(HtmlPage page, WebResponse response);

  public abstract HtmlPage solveChallenge(WebDriver driver, WebClient client, URL path)
          throws ChallengeFailedException, IOException;
}
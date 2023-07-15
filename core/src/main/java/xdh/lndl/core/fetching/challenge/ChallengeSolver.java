package xdh.lndl.core.fetching.challenge;

import lombok.Getter;
import org.htmlunit.Page;
import org.htmlunit.WebClient;
import org.htmlunit.WebResponse;
import org.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import xdh.lndl.core.fetching.WebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

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
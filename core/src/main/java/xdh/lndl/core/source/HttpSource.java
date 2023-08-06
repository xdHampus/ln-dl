package xdh.lndl.core.source;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import org.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import xdh.lndl.core.challenge.ChallengeSolver;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.fetching.WebDriver;
import xdh.lndl.core.source.ref.HttpSourceRef;

/**
 * A source for novels based on html.
 */
public abstract class HttpSource implements Source {

  protected static WebDriver webDriver = new WebDriver();

  protected abstract String getBaseUrl();

  protected abstract ChallengeSolver getChallengeSolver();

  public abstract String getNovelUrl(HttpSourceRef<Novel> novelRef);

  protected Optional<Document> fetchPage(String path) throws IOException {
    final URL url = new URL(getBaseUrl() + path);
    final var request = new WebRequest(url);

    try (WebClient client = webDriver.createWebClient()) {
      //Fetch
      HtmlPage page = client.getPage(request);
      WebResponse response = webDriver.getSavedResponse(request)
              .orElseThrow(() -> new RuntimeException("WebResponse could not be found."));

      if (getChallengeSolver().challengePresent(page, response)) {
        HtmlPage solvedPage = getChallengeSolver().solveChallenge(webDriver, client, url);
        return parsePage(solvedPage);
      }

      return parsePage(page);
    }
  }

  private Optional<Document> parsePage(HtmlPage page) {
    return Optional.of(Jsoup.parse(page.asXml()));
  }

}

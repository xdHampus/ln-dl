package xdh.lndl.core.challenge.antibotcloud;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import org.htmlunit.WebClient;
import org.htmlunit.WebResponse;
import org.htmlunit.html.HtmlImage;
import org.htmlunit.html.HtmlPage;
import xdh.lndl.core.challenge.ChallengeSolver;
import xdh.lndl.core.fetching.WebDriver;
import xdh.lndl.core.challenge.ChallengeFailedException;

/**
 * Naive AntiBotCloud solver for large screens.
 * Attempts to solve an image captcha by repeatedly selecting the first option.
 */
public class AntiBotCloudNaiveSolver extends ChallengeSolver {
  private static final String firstImageOptionSelector =
      "#content > p:nth-child(3) > img:nth-child(1)";
  private static final int javascriptWaitTime = 5_000;

  public AntiBotCloudNaiveSolver() {
    super(45);
  }

  @Override
  public boolean challengePresent(HtmlPage page, @NotNull WebResponse response) {
    String header = response.getResponseHeaderValue("link");
    return header == null || header.contains("antibotcloud.com");
  }

  @Override
  public HtmlPage solveChallenge(WebDriver driver, WebClient client, URL path)
      throws ChallengeFailedException, IOException {
    boolean javaScriptEnabled = client.getOptions().isJavaScriptEnabled();
    client.getOptions().setJavaScriptEnabled(true);
    client.getOptions().setDownloadImages(true);
    client.getOptions().setCssEnabled(true);
    client.getOptions().setWebSocketEnabled(true);
    client.getOptions().setActiveXNative(true);

    HtmlPage page = client.getPage(path);
    client.waitForBackgroundJavaScript(javascriptWaitTime * 10);

    try {
      page = trySolve(client, page, 0);
    } finally {
      client.getOptions().setJavaScriptEnabled(javaScriptEnabled);
    }

    return page;
  }

  /**
   * Selects first image, out of 6 others, then clicks it in hope of hitting the correct one. Runs
   * until retryCount exceeds maxRetries or challenge is solved.
   *
   * @param wc web client
   * @param page html page
   * @param retryCount current retry
   * @return successful page
   * @throws ChallengeFailedException max retries exceeded
   * @throws IOException failed to click captcha image.
   */
  private HtmlPage trySolve(WebClient wc, HtmlPage page, int retryCount)
      throws IOException, ChallengeFailedException {

    var content = page.getPage().asXml();
    HtmlImage image = page.querySelector(firstImageOptionSelector);
    if (image == null) {
      return page;
    }

    try {
      //Override image display: none; attribute to allow for clicking.
      image.setAttribute("style", "cursor: pointer;");
      var pageAfterClick = image.click();
      if (pageAfterClick.isHtmlPage()) {
        page = (HtmlPage) pageAfterClick;
      } // TODO: 7/6/23 Handle else-case
    } catch (IOException e) {
      throw new IOException(e);
    }
    wc.waitForBackgroundJavaScript(javascriptWaitTime);

    image = page.querySelector(firstImageOptionSelector);
    if (image == null) {
      return page;
    } else if (retryCount < maxRetries) {
      wc.waitForBackgroundJavaScript(javascriptWaitTime);
      return trySolve(wc, page, ++retryCount);
    } else {
      throw new ChallengeFailedException("Max tries exceeded");
    }
  }

}

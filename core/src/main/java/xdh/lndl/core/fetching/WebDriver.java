package xdh.lndl.core.fetching;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Optional;

import lombok.Getter;
import org.htmlunit.BrowserVersion;
import org.htmlunit.NicelyResynchronizingAjaxController;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import org.htmlunit.util.WebConnectionWrapper;
import xdh.lndl.core.util.FixedSizeMap;

public class WebDriver {
  @Getter protected static final CookieManager cookieManager = createCookieManager();
  private final FixedSizeMap<WebRequest, WebResponse> responses = new FixedSizeMap<>(50);

  protected static CookieManager createCookieManager() {
    var cm = new CookieManager();
    cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    return cm;
  }

  private void handleWebResponses(WebClient client) {
    new WebConnectionWrapper(client) {
      @Override
      public WebResponse getResponse(final WebRequest request) throws IOException {
        final WebResponse response = super.getResponse(request);
        responses.put(request, response);
        return response;
      }
    };
  }

  public Optional<WebResponse> getSavedResponse(WebRequest request) {
    return responses.get(request);
  }

  public WebClient createWebClient() {
    var wc = new WebClient(BrowserVersion.FIREFOX);
    wc.setAjaxController(new NicelyResynchronizingAjaxController());
    wc.setJavaScriptTimeout(15000);
    wc.getOptions().setGeolocationEnabled(true);
    wc.getOptions().setDoNotTrackEnabled(true);
    wc.getOptions().setProxyPolyfillEnabled(true);
    wc.getOptions().setWebSocketEnabled(true);
    wc.getOptions().setDownloadImages(true);
    wc.getOptions().setJavaScriptEnabled(false);
    wc.getOptions().setRedirectEnabled(true);
    wc.getOptions().setCssEnabled(true);
    wc.getOptions().setThrowExceptionOnScriptError(false);
    wc.getOptions().setFetchPolyfillEnabled(true);
    handleWebResponses(wc);
    return wc;
  }
}

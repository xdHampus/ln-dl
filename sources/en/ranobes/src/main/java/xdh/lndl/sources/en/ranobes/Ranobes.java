package xdh.lndl.sources.en.ranobes;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xdh.lndl.core.challenge.ChallengeSolver;
import xdh.lndl.core.challenge.antibotcloud.AntiBotCloudNaiveSolver;
import xdh.lndl.core.content.Image;
import xdh.lndl.core.data.Author;
import xdh.lndl.core.data.Chapter;
import xdh.lndl.core.data.Language;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.data.NovelTitle;
import xdh.lndl.core.data.PublishingDetails;
import xdh.lndl.core.data.ReleaseStatus;
import xdh.lndl.core.source.HttpSource;
import xdh.lndl.core.source.Version;
import xdh.lndl.core.source.query.LatestNovelsProvider;
import xdh.lndl.core.source.query.PopularNovelsProvider;
import xdh.lndl.core.source.query.SearchQuery;
import xdh.lndl.core.source.query.Searchable;
import xdh.lndl.core.source.ref.BasicHttpSourceRef;
import xdh.lndl.core.source.ref.HttpSourceRef;
import xdh.lndl.core.source.ref.SourceRef;
import xdh.lndl.core.source.ref.UnsupportedSourceRef;
import xdh.lndl.core.util.Page;
import xdh.lndl.core.util.Pageable;

/** The ranobes light novel site. */
@NoArgsConstructor
@AllArgsConstructor
public class Ranobes extends HttpSource
    implements PopularNovelsProvider, LatestNovelsProvider, Searchable {

  private final String title = "Ranobes";
  private final String baseUrl = "https://ranobes.top";
  private final Version version = new Version(1, 0, 0);
  private final ChallengeSolver challengeSolver = new AntiBotCloudNaiveSolver();
  private final String novelTitleSelector = "h1.title";
  private final String metaContainer = ".r-fullstory-spec";
  @Getter @Setter private UUID id;

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public Version getVersion() {
    return version;
  }

  @Override
  public Image getIcon() {
    return null;
  }

  @Override
  public Novel getNovelBySourceRef(SourceRef<Novel> sourceRef) throws IOException {
    if (!(sourceRef instanceof final HttpSourceRef<Novel> novelRef)) {
      throw new UnsupportedSourceRef(sourceRef, HttpSourceRef.class);
    }
    Optional<Document> fetched = fetchPage("/novels/" + novelRef.getPath());
    if (fetched.isEmpty()) {
      return null;
    }
    final Document doc = fetched.get();
    final var builder = Novel.builder();

    parseNovelTitle(builder, doc);
    // Consider throwing
    getMetaElements(doc).ifPresent(e -> parseMetaElements(builder, e));
    parseChapterOverview(builder, doc);
    parseTags(builder, doc);

    return builder.build();
  }

  private void parseMetaElements(Novel.NovelBuilder builder, Elements metaElements) {
    parseLanguage(builder, metaElements);
    parseAuthors(builder, metaElements);
    parseNovelStatus(builder, metaElements);
    parsePublishers(builder, metaElements);
  }

  private Novel.NovelBuilder parseTags(Novel.NovelBuilder builder, Document doc) {
    return builder;
  }

  private Novel.NovelBuilder parseChapterOverview(Novel.NovelBuilder builder, Document doc) {
    return builder;
  }

  Novel.NovelBuilder parsePublishers(Novel.NovelBuilder builder, Elements metaElements) {
    String publishDate = getTextAfterContainer(metaElements, "Year of publishing:");
    List<String> publishers =
        Arrays.stream(getTextAfterContainer(metaElements, "Publishers:").trim().split(" "))
            .map(String::trim)
            .collect(Collectors.toList());

    Instant year =
        LocalDate.ofYearDay(Integer.parseInt(publishDate.trim()), 1)
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);

    return builder.publishingDetails(
        PublishingDetails.builder().publishingDate(year).publishers(publishers).build());
  }

  Novel.NovelBuilder parseAuthors(Novel.NovelBuilder builder, Elements metaElements) {
    String authorElementId = "Authors:";
    return builder.authorList(
        getElementsAfterContainer(metaElements, authorElementId).stream()
            .map(
                e ->
                    Author.builder()
                        .name(e.text().replace(authorElementId, "").trim())
                        .sourceRef(
                            BasicHttpSourceRef.<Author>builder()
                                .sourceId(this.getSourceId())
                                .path(e.select("[href]").attr("href"))
                                .build())
                        .build())
            .toList());
  }

  @Override
  public Chapter getChapterBySourceRef(SourceRef<Chapter> sourceRef) {
    if (!(sourceRef instanceof final HttpSourceRef<Chapter> chapterRef)) {
      throw new UnsupportedSourceRef(sourceRef, HttpSourceRef.class);
    }

    return null;
  }

  Novel.NovelBuilder parseNovelTitle(Novel.NovelBuilder builder, Document doc) {
    List<String> titles =
        Arrays.stream(doc.select(novelTitleSelector).text().split("•")).map(String::trim).toList();

    return builder.title(
        NovelTitle.builder()
            .title(titles.stream().findFirst().orElse(null))
            .alternativeTitles(titles.stream().skip(1).collect(Collectors.toList()))
            .build());
  }

  Novel.NovelBuilder parseLanguage(Novel.NovelBuilder builder, Elements metaElements) {
    return builder
        .actualLanguage(Language.ENGLISH)
        .originalLanguage(Language.parseFull(getTextAfterContainer(metaElements, "Language:")));
  }

  Novel.NovelBuilder parseNovelStatus(Novel.NovelBuilder builder, Elements metaElements) {
    String statusInCooString = getTextAfterContainer(metaElements, "Status in COO:");
    String statusTranslationString = getTextAfterContainer(metaElements, "Translation:");

    return builder
        .releaseStatusInCoo(stringToReleaseStatus(statusInCooString))
        .releaseStatusTranslation(stringToReleaseStatus(statusTranslationString));
  }

  Optional<Elements> getMetaElements(Document doc) {
    Element container = doc.select(metaContainer).first();
    return container != null ? Optional.of(container.getElementsByTag("li")) : Optional.empty();
  }

  ReleaseStatus stringToReleaseStatus(String string) {
    if (string == null) {
      return ReleaseStatus.UNKNOWN;
    }
    return switch (string.trim().toLowerCase()) {
      case "ongoing" -> ReleaseStatus.ONGOING;
      case "on hold" -> ReleaseStatus.ON_HOLD;
      case "cancelled" -> ReleaseStatus.CANCELLED;
      case "completed" -> ReleaseStatus.FINISHED;
      default -> ReleaseStatus.UNKNOWN;
    };
  }

  String getTextAfterContainer(Elements elements, String containerText) {
    return elements.eachText().stream()
        .filter(s -> s.startsWith(containerText))
        .limit(1)
        .map(s -> s.replace(containerText, ""))
        .findFirst()
        .orElse("");
  }

  List<Element> getElementsAfterContainer(Elements elements, String containerText) {
    return elements.stream()
        .filter(s -> s.text().startsWith(containerText))
        .collect(Collectors.toList());
  }

  @Override
  public List<Novel> getPopularNovels(Pageable pageable) {
    return null;
  }

  @Override
  public Page<Novel> getLatestNovels(Pageable pageable) throws IOException {
    var pagePath =
        "/updates/" + (pageable.getPage() <= 1 ? "" : ("page/" + pageable.getPage() + "/"));
    var page = fetchPage(pagePath).map(this::parseLatestNovels).orElse(new Page<>());
    page.setCurrentPage(pageable.getPage());

    return page;
  }

  Page<Novel> parseLatestNovels(Document document) {
    final var mainContent = document.getElementById("mainside");
    final var page = new Page<Novel>();
    if (mainContent == null) {
      return page;
    }

    final var leftSide = mainContent.getElementsByClass("str_left").stream().findFirst();
    if (leftSide.isEmpty()) {
      return page;
    }

    final var novelElements = leftSide.get().children();
    final var navigationElement = novelElements.last();
    if (navigationElement == null) {
      return page;
    }
    var navigationPages = navigationElement.getElementsByClass("pages").first();
    if (navigationPages == null) {
      return page;
    }
    var lastNavigationPage = navigationPages.children().last();
    if (lastNavigationPage == null) {
      return page;
    }
    try {
      int lastPage = Integer.parseInt(lastNavigationPage.text());
      page.setLastPage(lastPage);
    } catch (Exception ignored) {
      // TODO: 8/6/23 Log error
    }

    novelElements.remove(navigationElement);
    List<Novel> novelEntries =
        novelElements.stream()
            .map(this::parseLatestNovelEntry)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    page.setElements(novelEntries);

    return page;
  }

  Optional<Novel> parseLatestNovelEntry(Element element) {
    final var builder = Novel.builder();
    final var linkElement = element.getElementsByTag("a").stream().findFirst();
    if (linkElement.isEmpty()) {
      return Optional.empty();
    }

    // Get ref
    String novelRef = linkElement.get().attr("href").replace("https://ranobes.top/", "");
    novelRef = novelRef.replace(novelRef.substring(novelRef.indexOf("/")), "");
    int novelRefSeparator = novelRef.lastIndexOf("-");
    String part1 = novelRef.substring(0, novelRefSeparator);
    String part2 = novelRef.substring(novelRefSeparator + 1);
    novelRef = part2 + "-" + part1 + ".html";
    builder.sourceRef(BasicHttpSourceRef.<Novel>builder().path(novelRef).build());

    // Title
    final var titleElement = element.getElementsByTag("h3").stream().findFirst();
    titleElement.ifPresent(
        value -> builder.title(NovelTitle.builder().title(value.ownText()).build()));

    return Optional.of(builder.build());
  }

  protected String getBaseUrl() {
    return baseUrl;
  }

  @Override
  protected ChallengeSolver getChallengeSolver() {
    return challengeSolver;
  }

  @Override
  public String getNovelUrl(HttpSourceRef<Novel> novelRef) {
    return getBaseUrl() + "/novel/" + novelRef.getPath();
  }

  @Override
  public List<Novel> searchForNovels(SearchQuery searchQuery, Pageable pageable) {
    return null;
  }
}

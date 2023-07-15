package xdh.lndl.sources.en.ranobes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xdh.lndl.core.data.Author;
import xdh.lndl.core.data.Chapter;
import xdh.lndl.core.data.Language;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.data.NovelTitle;
import xdh.lndl.core.data.PublishingDetails;
import xdh.lndl.core.data.ReleaseStatus;
import xdh.lndl.core.fetching.challenge.antibotcloud.AntiBotCloudNaiveSolver;
import xdh.lndl.core.fetching.challenge.ChallengeSolver;
import xdh.lndl.core.source.HttpSource;
import xdh.lndl.core.content.Image;
import xdh.lndl.core.source.Version;
import xdh.lndl.core.source.query.LatestNovelsProvider;
import xdh.lndl.core.source.query.PopularNovelsProvider;
import xdh.lndl.core.source.ref.BasicHttpSourceRef;
import xdh.lndl.core.source.ref.HttpSourceRef;
import xdh.lndl.core.source.ref.SourceRef;
import xdh.lndl.core.source.ref.UnsupportedSourceRef;

import java.awt.print.Pageable;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The ranobes light novel site.
 */
@NoArgsConstructor
@AllArgsConstructor
public class Ranobes extends HttpSource
        implements PopularNovelsProvider, LatestNovelsProvider {

  @Getter @Setter
  private UUID id;
  private final String title = "Ranobes";
  private final String baseUrl = "https://ranobes.top";
  private final Version version = new Version(1, 0, 0);
  private final ChallengeSolver challengeSolver = new AntiBotCloudNaiveSolver();
  private final String novelTitleSelector = "h1.title";
  private final String metaContainer = ".r-fullstory-spec";


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
    //Consider throwing
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
    return null;
  }

  private Novel.NovelBuilder parseChapterOverview(Novel.NovelBuilder builder, Document doc) {
    return null;
  }

  public Novel.NovelBuilder parsePublishers(Novel.NovelBuilder builder, Elements metaElements) {
    String publishDate = getTextAfterContainer(metaElements, "Year of publishing:");
    List<String> publishers = Arrays.stream(
            getTextAfterContainer(metaElements, "Publishers:")
            .trim()
            .split(" "))
            .map(String::trim)
            .collect(Collectors.toList());;

    Instant year = LocalDate.ofYearDay(Integer.parseInt(publishDate.trim()), 1)
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);

    return builder
            .publishingDetails(
                    PublishingDetails
                            .builder()
                            .publishingDate(year)
                            .publishers(publishers)
                            .build()

            );
  }

  public Novel.NovelBuilder parseAuthors(Novel.NovelBuilder builder, Elements metaElements) {
    String authorElementId = "Authors:";
    return builder.authorList(getElementsAfterContainer(metaElements, authorElementId).stream()
            .map(
                    e ->
                            Author.builder()
                                    .name(e.text().replace(authorElementId, "").trim())
                                    .sourceRef(BasicHttpSourceRef.<Author>builder()
                                            .sourceId(this.getSourceId())
                                            .path(e.select("[href]").attr("href"))
                                            .build()
                                    ).build())
            .toList());
  }

  @Override
  public Chapter getChapterBySourceRef(SourceRef<Chapter> sourceRef) {
    if (!(sourceRef instanceof final HttpSourceRef<Chapter> chapterRef)) {
      throw new UnsupportedSourceRef(sourceRef, HttpSourceRef.class);
    }

    return null;
  }


  public Novel.NovelBuilder parseNovelTitle(Novel.NovelBuilder builder, Document doc) {
    List<String> titles = Arrays.stream(
            doc.select(novelTitleSelector)
                    .text()
                    .split("•"))
            .map(String::trim).toList();

    return builder.title(NovelTitle.builder()
              .title(titles.stream().findFirst().orElse(null))
              .alternativeTitles(titles.stream().skip(1).collect(Collectors.toList())
            ).build()
    );
  }

  public Novel.NovelBuilder parseLanguage(Novel.NovelBuilder builder, Elements metaElements) {
    return builder
            .actualLanguage(Language.ENGLISH)
            .originalLanguage(Language.parseFull(getTextAfterContainer(metaElements, "Language:")));
  }

  public Novel.NovelBuilder parseNovelStatus(Novel.NovelBuilder builder, Elements metaElements) {
    String statusInCooString = getTextAfterContainer(metaElements, "Status in COO:");
    String statusTranslationString = getTextAfterContainer(metaElements, "Translation:");

    return builder
            .releaseStatusInCoo(stringToReleaseStatus(statusInCooString))
            .releaseStatusTranslation(stringToReleaseStatus(statusTranslationString));
  }


  public Optional<Elements> getMetaElements(Document doc) {
    Element container = doc.select(metaContainer).first();
    return container != null ? Optional.of(container.getElementsByTag("li")) : Optional.empty();
  }

  public ReleaseStatus stringToReleaseStatus(String string) {
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

  public String getTextAfterContainer(Elements elements, String containerText) {
    return elements
            .eachText()
            .stream()
            .filter(s -> s.startsWith(containerText))
            .limit(1)
            .map(s -> s.replace(containerText, ""))
            .findFirst()
            .orElse("");
  }

  public List<Element> getElementsAfterContainer(Elements elements, String containerText) {


    return elements
            .stream()
            .filter(s ->
                    s.text().startsWith(containerText)
            ).collect(Collectors.toList());
  }

  @Override
  public List<Novel> getPopularNovels(Pageable pageable) {
    return null;
  }

  @Override
  public List<Novel> getLatestNovels(Pageable pageable) {
    return null;
  }

  protected String getBaseUrl() {
    return baseUrl;
  }

  @Override
  protected ChallengeSolver getChallengeSolver() {
    return challengeSolver;
  }
}

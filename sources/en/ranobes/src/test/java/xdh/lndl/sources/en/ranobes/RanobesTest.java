package xdh.lndl.sources.en.ranobes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xdh.lndl.core.data.Author;
import xdh.lndl.core.data.Language;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.data.NovelTitle;
import xdh.lndl.core.data.ReleaseStatus;
import xdh.lndl.core.source.ref.BasicHttpSourceRef;
import xdh.lndl.core.util.Pageable;

class RanobesTest {

  private static final Document novelPageFile;
  private static final Document latestNovelsFile;

  static {
    novelPageFile =
        Jsoup.parse(
            StaticHelper.readFileAsString(
                    RanobesTest.class.getClassLoader(),
                    "ranobes-page/novel-page.html"));
    latestNovelsFile =
            Jsoup.parse(
                    StaticHelper.readFileAsString(
                            RanobesTest.class.getClassLoader(),
                            "ranobes-page/latest-updates.html"));
  }

  private Ranobes ranobes;

  @BeforeEach
  void setUp() {
    ranobes = new Ranobes();
  }

  @Test
  @Disabled
  void getNovelBySourceRef() throws IOException {
    final var novelRef =
        BasicHttpSourceRef.<Novel>builder().path("39616-swallowed-star-v812312.html").build();

    final var novel = ranobes.getNovelBySourceRef(novelRef);

    assert novel != null;
  }

  @Nested
  class ParsingTests {
    private Novel.NovelBuilder builder;

    @BeforeEach
    void setUp() {
      builder = Novel.builder();
    }

    @Test
    void parseNovelTitle() {
      NovelTitle expectedTitle =
          NovelTitle.builder()
              .title("Swallowed Star")
              .alternativeTitles(List.of("吞噬星空", "Tun Shi Xing Kong"))
              .build();

      Novel novel = ranobes.parseNovelTitle(builder, novelPageFile).build();

      assertEquals(expectedTitle, novel.getTitle());
    }

    @Nested
    class MetaElementsTests {

      private Elements metaElements;

      @BeforeEach
      void setUp() {
        Optional<Elements> metaElementsOpt = ranobes.getMetaElements(novelPageFile);
        assertTrue(metaElementsOpt.isPresent());
        metaElements = metaElementsOpt.get();
      }

      @Test
      void parseNovelStatus() {
        Novel novel = ranobes.parseNovelStatus(builder, metaElements).build();

        assertEquals(ReleaseStatus.FINISHED, novel.getReleaseStatusInCoo());
        assertEquals(ReleaseStatus.FINISHED, novel.getReleaseStatusTranslation());
      }

      @Test
      void parseLanguage() {
        Novel novel = ranobes.parseLanguage(builder, metaElements).build();

        assertEquals(Language.CHINESE, novel.getOriginalLanguage());
        assertEquals(Language.ENGLISH, novel.getActualLanguage());
      }

      @Test
      void parseAuthors() {
        List<Author> expectedAuthors =
            List.of(
                Author.builder()
                    .name("I Eat Tomatoes")
                    .sourceRef(
                        BasicHttpSourceRef.<Author>builder()
                            .sourceId(ranobes.getSourceId())
                            .path("https://ranobes.top/tags/authors/I%20Eat%20Tomatoes/")
                            .build())
                    .build());

        Novel novel = ranobes.parseAuthors(builder, metaElements).build();

        assertEquals(expectedAuthors, novel.getAuthorList());
      }

      @Test
      void parsePublishers() {
        Novel novel = ranobes.parsePublishers(builder, metaElements).build();

        assertNotNull(novel.getPublishingDetails());
        assertEquals(List.of("Qidian", "Webnovel"), novel.getPublishingDetails().getPublishers());
        assertEquals(
            LocalDate.ofYearDay(Integer.parseInt("2010"), 1)
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC),
            novel.getPublishingDetails().getPublishingDate());
      }
    }
  }

  @Nested
  class FindLatest {

    @Test
    void parseLatestNovelsReal() throws IOException {
      var page = new Pageable();
      page.setPage(2);
      var wtf = ranobes.getLatestNovels(page);
      var dam = wtf.getElements().size();
    }
    @Test
    void parseLatestNovels() {
      var wtf = ranobes.parseLatestNovels(latestNovelsFile);
    }

  }

}

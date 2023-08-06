package xdh.lndl.core.data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xdh.lndl.core.content.Image;
import xdh.lndl.core.persistence.Entity;
import xdh.lndl.core.source.ref.SourceRef;

/**
 * A light novel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Novel implements Entity {
  private UUID id;
  private boolean isTranslation;
  private SourceRef<Novel> sourceRef;
  private Image poster;
  private NovelTitle title;
  private String synopsis;
  private ReleaseStatus releaseStatusInCoo;
  private ReleaseStatus releaseStatusTranslation;
  private PublishingDetails publishingDetails;
  private Rating userRating;
  private ContentRating contentRating;
  private Language originalLanguage;
  private Language actualLanguage;
  private Ranking ranking;
  private Translator translator;
  private List<Author> authorList;
  private List<Tag> tags;
  private List<Review> reviews;
  private List<Comment> comments;
  private List<Chapter> chapters;
  private int chapterCountReportedInOriginal;
  private int chapterCountReportedInTranslation;
  private List<Volume> volumes;
  private List<Novel> recommendations;
  private List<Novel> related;
  private Instant updatedDate;
}

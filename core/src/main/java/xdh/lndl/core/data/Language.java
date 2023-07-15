package xdh.lndl.core.data;

/**
 * Language.
 */
public enum Language {
  UNKNOWN("", ""),
  ENGLISH("english", "en"),
  JAPANESE("japanese", "jp"),
  KOREAN("korean", "kr"),
  CHINESE("chinese", "cn"),
  SPANISH("spanish", "sp"),
  RUSSIAN("russian", "ru");

  private final String fullString;
  private final String abbreviation;

  Language(String fullString, String abbreviation) {
    this.fullString = fullString;
    this.abbreviation = abbreviation;
  }

  public static Language parseFull(String fullString) {
    if (fullString == null) {
      return Language.UNKNOWN;
    }

    return switch (fullString.trim().toLowerCase()) {
      case "english" -> Language.ENGLISH;
      case "japanese" -> Language.JAPANESE;
      case "korean" -> Language.KOREAN;
      case "chinese" -> Language.CHINESE;
      case "spanish" -> Language.SPANISH;
      case "russian" -> Language.RUSSIAN;
      default -> Language.UNKNOWN;
    };

  }



}

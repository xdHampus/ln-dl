package xdh.lndl.sources.en.ranobes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Static helper functions for tests.
 */
public class StaticHelper {
  /**
   * Read file from resources as string.
   *
   * @param loader class loader
   * @param filename file name
   * @return file contents
   */
  public static String readFileAsString(ClassLoader loader, String filename) {
    StringBuilder stringBuilder = new StringBuilder();
    try (InputStream inputStream = loader.getResourceAsStream(filename)) {
      assert inputStream != null;
      try (InputStreamReader streamReader =
              new InputStreamReader(inputStream, StandardCharsets.UTF_8);
          BufferedReader reader = new BufferedReader(streamReader)) {

        String line;
        while ((line = reader.readLine()) != null) {
          stringBuilder.append(line);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuilder.toString();
  }
}

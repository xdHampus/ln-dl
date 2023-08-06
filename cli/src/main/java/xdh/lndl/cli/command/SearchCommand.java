package xdh.lndl.cli.command;

import java.io.IOException;
import java.util.List;
import java.util.ServiceLoader;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;
import xdh.lndl.core.data.Novel;
import xdh.lndl.core.source.HttpSource;
import xdh.lndl.core.source.Source;
import xdh.lndl.core.source.query.LatestNovelsProvider;
import xdh.lndl.core.source.ref.HttpSourceRef;
import xdh.lndl.core.util.Page;
import xdh.lndl.core.util.Pageable;

/** Search command. */
@Command(name = "search", description = "Search for thing")
public class SearchCommand implements Runnable {
  @Spec CommandSpec spec;

  @Option(names = {"-l", "--latest"})
  private boolean findLatest;

  @Option(names = {"-p", "--page"})
  private int searchPage = 1;

  @Option(names = {"-S", "-src", "--source"})
  private String source;

  @Override
  public void run() {
    var loader = ServiceLoader.load(Source.class);
    List<Source> sources = loader.stream().map(ServiceLoader.Provider::get).toList();

    Source source =
        sources.stream()
            .filter(s -> s.getTitle().toLowerCase().trim().equals(this.source.toLowerCase().trim()))
            .findFirst()
            .orElseThrow(
                () -> new CommandLine.ParameterException(spec.commandLine(), "Source not found."));

    if (findLatest) {
      runFindLatest(source);
      return;
    }

    throw new CommandLine.ParameterException(spec.commandLine(), "Missing action");
  }

  private void runFindLatest(Source source) {
    if (!(source instanceof LatestNovelsProvider)) {
      throw new CommandLine.ParameterException(
          spec.commandLine(), "Source doesn't support finding latest novels.");
    }

    var pageable = new Pageable();
    pageable.setPage(this.searchPage);
    System.out.println("Finding latest novels on " + source.getTitle() + "v" + source.getVersion());
    try {
      Page<Novel> novelPage = ((LatestNovelsProvider) source).getLatestNovels(pageable);
      System.out.println(
          "Page  " + novelPage.getCurrentPage() + " out of " + novelPage.getLastPage());

      novelPage
          .getElements()
          .forEach(
              novel -> {
                System.out.println(
                    "Novel: "
                        + (novel.getTitle() != null
                            ? novel.getTitle().getTitle()
                            : "Could not be parsed"));

                if (source instanceof HttpSource) {
                  System.out.println(
                      "Link: "
                          + (novel.getSourceRef() != null
                              ? (((HttpSource) source)
                                  .getNovelUrl((HttpSourceRef<Novel>) novel.getSourceRef()))
                              : "Could not be parsed"));
                }
              });

    } catch (IOException e) {
      // TODO: 8/6/23 Log and only print stacktrace on verbose setting
      throw new RuntimeException(e);
    }
  }
}

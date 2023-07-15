package xdh.lndl.cli.command;

import java.util.ServiceLoader;
import java.util.List;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import xdh.lndl.core.source.Source;

/** Source command. */
@Command(name = "source", description = "Deals with available source")
public class SourceCommand implements Runnable {

  @Option(names = {"-a", "--all"})
  private boolean allSources;

  @Override
  public void run() {
    var loader = ServiceLoader.load(Source.class);
    List<Source> services = loader.stream().map(ServiceLoader.Provider::get).toList();

    if (allSources) {
      System.out.println("Sources:");
      services.forEach(source -> printSource(source, 1));
      return;
    }


  }

  private void printSource(Source source, int depth) {
    var builder = new StringBuilder(source.getTitle() + " v" + source.getVersion());
    for (int i = 0; i < depth; i++) {
      builder.insert(0, "\t");
    }

    System.out.println(builder);
  }
}

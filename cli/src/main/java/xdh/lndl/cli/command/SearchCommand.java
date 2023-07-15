package xdh.lndl.cli.command;

import picocli.CommandLine.Command;

/**
 * Search command.
 */
@Command(
        name = "search",
        description = "Search for thing"
)
public class SearchCommand implements Runnable {
  @Override
  public void run() {
    System.out.println("Search!");
  }
}

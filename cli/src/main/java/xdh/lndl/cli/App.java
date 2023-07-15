package xdh.lndl.cli;

import picocli.CommandLine;
import xdh.lndl.cli.command.BaseCommand;

/**
 * Application entrypoint.
 */
public class App {
  public static final String version = "1.0.0";

  /**
   * Application entrypoint.
   *
   * @param args args.
   */
  public static void main(String[] args) {
    var cmd = new CommandLine(new BaseCommand());
    modifyCommandLine(cmd);

    int exitCode = cmd.execute(args);
    System.exit(exitCode);
  }

  private static void modifyCommandLine(CommandLine cmd) {
    cmd
        .getSubcommands()
        .get("generate-completion")
        .getCommandSpec()
        .usageMessage()
        .hidden(true);
  }
}

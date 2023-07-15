package xdh.lndl.cli.command;

import picocli.AutoComplete;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import xdh.lndl.cli.App;
import static picocli.AutoComplete.GenerateCompletion;

/**
 * ln-dl cli.
 */
@Command(
        name = "ln-dl",
        version =  {
            "ln-dl v" + App.version
        },
        description = "light novel download cli",
        subcommands = {
          SearchCommand.class,
          SourceCommand.class,
          GenerateCompletion.class
        }
)
public class BaseCommand implements Runnable {

  @Spec CommandSpec spec;
  @Option(names = { "-h", "--help" }, usageHelp = true, description = "display help message")
  private boolean helpRequested = false;
  @Option(names = {"-v", "--version"}, versionHelp = true, description = "display version")
  private boolean version;

  @Override
  public void run() {

    throw new ParameterException(spec.commandLine(), "Missing required subcommand");
  }
}

package xdh.lndl.cli.command;

import static picocli.AutoComplete.GenerateCompletion;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;
import xdh.lndl.cli.App;

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

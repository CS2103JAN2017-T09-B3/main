package seedu.address.logic.parser;

import java.io.File;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.OpenCommand;

/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class OpenCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the OpenCommand
     * and returns an OpenCommand object for execution.
     */
    public Command parse(String args) {
        File file = new File(args.trim());
        return new OpenCommand(file);
    }

}

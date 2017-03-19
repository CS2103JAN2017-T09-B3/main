package seedu.address.logic.parser;

import java.io.File;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SaveCommand;

/**
 * Parses input arguments and creates a new SaveCommand object
 */
public class SaveCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     */
    public Command parse(String args) {
        File file = new File(args.trim());
        return new SaveCommand(file);
    }

}
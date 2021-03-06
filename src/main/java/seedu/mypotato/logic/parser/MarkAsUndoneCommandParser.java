//@@author A0135753A
package seedu.mypotato.logic.parser;

import static seedu.mypotato.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.mypotato.logic.commands.Command;
import seedu.mypotato.logic.commands.IncorrectCommand;
import seedu.mypotato.logic.commands.MarkAsUndoneCommand;

/**
 * Parses input arguments and creates a new MarkAsUndoneCommand object
 */
public class MarkAsUndoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAsUndoneCommand.MESSAGE_USAGE));
        }

        return new MarkAsUndoneCommand(index.get());
    }

}

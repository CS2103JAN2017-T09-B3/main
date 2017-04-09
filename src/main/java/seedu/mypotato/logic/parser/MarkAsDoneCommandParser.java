//@@author A0135753A
package seedu.mypotato.logic.parser;

import static seedu.mypotato.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.mypotato.logic.commands.Command;
import seedu.mypotato.logic.commands.IncorrectCommand;
import seedu.mypotato.logic.commands.MarkAsDoneCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class MarkAsDoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAsDoneCommand
     * and returns an MarkAsDoneCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAsDoneCommand.MESSAGE_USAGE));
        }

        return new MarkAsDoneCommand(index.get());
    }

}

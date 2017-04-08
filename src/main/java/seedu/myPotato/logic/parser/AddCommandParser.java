package seedu.myPotato.logic.parser;

import static seedu.myPotato.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_DATE_TIME_END;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.myPotato.commons.exceptions.IllegalValueException;
import seedu.myPotato.logic.commands.AddCommand;
import seedu.myPotato.logic.commands.Command;
import seedu.myPotato.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    public static final String DEFAULT_VALUE = "";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_CONTENT, PREFIX_DATE_TIME_START, PREFIX_DATE_TIME_END, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            Optional<String> title = argsTokenizer.getPreamble();
            Optional<String> content = argsTokenizer.getValue(PREFIX_CONTENT);
            Optional<String> startDateTime = argsTokenizer.getValue(PREFIX_DATE_TIME_START);
            Optional<String> endDateTime = argsTokenizer.getValue(PREFIX_DATE_TIME_END);
            Optional<List<String>> tags = argsTokenizer.getAllValues(PREFIX_TAG);

            return new AddCommand(
                    title.get(),
                    content.orElse(DEFAULT_VALUE),
                    startDateTime.orElse(DEFAULT_VALUE),
                    endDateTime.orElse(DEFAULT_VALUE),
                    ParserUtil.toSet(tags)
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_POINT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

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
                new ArgumentTokenizer(PREFIX_CONTENT, PREFIX_DATE_TIME_POINT, PREFIX_DATE_TIME_START, PREFIX_DATE_TIME_END, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            Optional<String> title = argsTokenizer.getPreamble();
            Optional<String> content = argsTokenizer.getValue(PREFIX_CONTENT);
            Optional<String> startDateTime = argsTokenizer.getValue(PREFIX_DATE_TIME_START);
            
            //end date time can come from two prefixes
            Optional<String> endDateTime = argsTokenizer.getValue(PREFIX_DATE_TIME_END);
            if (!endDateTime.isPresent()) {
                endDateTime = argsTokenizer.getValue(PREFIX_DATE_TIME_POINT);
            }
            
            Optional<List<String>> tags = argsTokenizer.getAllValues(PREFIX_TAG);
            return new AddCommand(
                    title.get(),
                    content.orElse(DEFAULT_VALUE),
                    startDateTime.orElse(DEFAULT_VALUE),
                    endDateTime.orElse(DEFAULT_VALUE),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}

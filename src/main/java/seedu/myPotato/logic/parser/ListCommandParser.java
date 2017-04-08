package seedu.myPotato.logic.parser;

import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_LIST_ALL;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_LIST_COMPLETED;
import static seedu.myPotato.logic.parser.CliSyntax.PREFIX_LIST_TODAY;

import java.util.Optional;

import seedu.myPotato.commons.exceptions.IllegalValueException;
import seedu.myPotato.logic.commands.Command;
import seedu.myPotato.logic.commands.IncorrectCommand;
import seedu.myPotato.logic.commands.ListCommand;

//@@author A0144895N
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_LIST_ALL, PREFIX_LIST_TODAY, PREFIX_LIST_COMPLETED);
        argsTokenizer.tokenize(args);
        try {
            Optional<String> all = argsTokenizer.getValue(PREFIX_LIST_ALL);
            Optional<String> today = argsTokenizer.getValue(PREFIX_LIST_TODAY);
            Optional<String> completed = argsTokenizer.getValue(PREFIX_LIST_COMPLETED);
            boolean isAll = all.isPresent();
            boolean isToday = today.isPresent();
            boolean isCompleted = completed.isPresent();
            return new ListCommand(isAll, isToday, isCompleted);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}

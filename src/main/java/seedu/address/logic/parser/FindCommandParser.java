package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_CONTENT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    //@@author A0144895N
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        boolean isInContent = false;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_FIND_CONTENT);
        argsTokenizer.tokenize(args);

        Optional<String> preamble = argsTokenizer.getPreamble();
        Optional<String> content = argsTokenizer.getValue(PREFIX_FIND_CONTENT);

        if (content.isPresent()) {
            isInContent = true;
        }

        String keywordsString = isInContent ? content.get() : preamble.get();
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(keywordsString.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));

        return new FindCommand(isInContent, keywordSet);
    }

}

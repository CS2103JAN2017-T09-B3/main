package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DATE_TIME_START = new Prefix("start/");
    public static final Prefix PREFIX_DATE_TIME_END = new Prefix("end/");
    public static final Prefix PREFIX_CONTENT = new Prefix("c/");
    public static final Prefix PREFIX_TAG = new Prefix("#");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}

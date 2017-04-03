package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    /* Empty String */
    public static final String EMPTY_STRING = "";

    /* Prefix definitions */
    public static final Prefix PREFIX_EMPTY = new Prefix("");
    public static final Prefix PREFIX_TITLE = new Prefix("");
    public static final Prefix PREFIX_DATE_TIME_START = new Prefix("start/");
    public static final Prefix PREFIX_DATE_TIME_END = new Prefix("end/");
    public static final Prefix PREFIX_CONTENT = new Prefix("c/");
    public static final Prefix PREFIX_FIND_CONTENT = new Prefix("content/");
    public static final Prefix PREFIX_LIST_ALL = new Prefix("all");
    public static final Prefix PREFIX_LIST_TODAY = new Prefix("today");
    public static final Prefix PREFIX_LIST_COMPLETED = new Prefix("completed");
    public static final Prefix PREFIX_TAG = new Prefix("#");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}

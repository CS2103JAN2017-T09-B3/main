package seedu.myPotato.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in myPotato whose content contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose contents contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meeting";

    private final Set<String> keywords;
    private final boolean isInContent;

    public FindCommand(boolean isInContent, Set<String> keywords) {
        this.keywords = keywords;
        this.isInContent = isInContent;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(isInContent, keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}

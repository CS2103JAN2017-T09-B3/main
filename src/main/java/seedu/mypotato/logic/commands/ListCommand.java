package seedu.mypotato.logic.commands;

import seedu.mypotato.commons.core.EventsCenter;
import seedu.mypotato.commons.events.ui.SwitchToTabRequestEvent;
import seedu.mypotato.commons.exceptions.IllegalValueException;

//@@author A0144895N
/**
 * Lists filtered tasks in myPotato to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String KEYWORD_TODAY = "today";

    public static final String KEYWORD_ALL = "all";

    public static final String KEYWORD_COMPLETED = "completed";

    public static final String MESSAGE_ALL_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_TODAY_SUCCESS = "Listed all today tasks";

    public static final String MESSAGE_COMPLETED_SUCCESS = "Listed all completed tasks";

    public static final String MESSAGE_NO_LIST = "One list should be requested";

    public static final String MESSAGE_MULTIPLE_LIST = "Only one list should be requested";

    private boolean isAll;
    private boolean isToday;
    private boolean isCompleted;

    /**
     * Validates the given requested list
     * @throws IllegalValueException if none or more than 1 boolean parameters are set to be true
     */
    public ListCommand(boolean isAll, boolean isToday, boolean isCompleted) throws IllegalValueException {

        checkValidList(isAll, isToday, isCompleted);

        this.isAll = isAll;
        this.isToday = isToday;
        this.isCompleted = isCompleted;
    }

    public void checkValidList(boolean isAll, boolean isToday, boolean isCompleted) throws IllegalValueException {
        if ((!isAll && !isToday && !isCompleted)) {
            throw new IllegalValueException(MESSAGE_NO_LIST);
        }

        if ((isAll && isToday) || (isAll && isCompleted) || (isToday && isCompleted)) {
            throw new IllegalValueException(MESSAGE_MULTIPLE_LIST);
        }
    }

    @Override
    public CommandResult execute() {
        if (isToday) {
            EventsCenter.getInstance().post(new SwitchToTabRequestEvent(KEYWORD_TODAY));
            model.setCurrentList(KEYWORD_TODAY);
            model.updateFilteredListToShowToday();
            return new CommandResult(MESSAGE_TODAY_SUCCESS);
        } else if (isCompleted) {
            EventsCenter.getInstance().post(new SwitchToTabRequestEvent(KEYWORD_COMPLETED));
            model.setCurrentList(KEYWORD_COMPLETED);
            model.updateFilteredListToShowCompleted();
            return new CommandResult(MESSAGE_COMPLETED_SUCCESS);
        } else if (isAll) {
            EventsCenter.getInstance().post(new SwitchToTabRequestEvent(KEYWORD_ALL));
            model.setCurrentList(KEYWORD_ALL);
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_ALL_SUCCESS);
        } else {
            EventsCenter.getInstance().post(new SwitchToTabRequestEvent(KEYWORD_ALL));
            model.setCurrentList(KEYWORD_ALL);
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_ALL_SUCCESS);
        }
    }
}

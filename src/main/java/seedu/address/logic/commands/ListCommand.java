package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchToTabRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0144895N
/**
 * Lists filtered tasks in myPotato to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_ALL_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_TODAY_SUCCESS = "Listed all today tasks";

    public static final String MESSAGE_COMPLETED_SUCCESS = "Listed all completed tasks";

    private static final String MESSAGE_NO_LIST = "One list should be requested";

    private static final String MESSAGE_MULTIPLE_LIST = "Only one list should be requested";

    private boolean isAll;
    private boolean isToday;
    private boolean isCompleted;

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
            EventsCenter.getInstance().post(new SwitchToTabRequestEvent("today"));
            model.setCurrentList("today");
            model.updateFilteredListToShowToday();
            return new CommandResult(MESSAGE_TODAY_SUCCESS);
        } else if (isCompleted) {
            EventsCenter.getInstance().post(new SwitchToTabRequestEvent("completed"));
            model.setCurrentList("completed");
            model.updateFilteredListToShowCompleted();
            return new CommandResult(MESSAGE_COMPLETED_SUCCESS);
        } else if (isAll) {
            EventsCenter.getInstance().post(new SwitchToTabRequestEvent("all"));
            model.setCurrentList("all");
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_ALL_SUCCESS);
        } else {
            EventsCenter.getInstance().post(new SwitchToTabRequestEvent("all"));
            model.setCurrentList("all");
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_ALL_SUCCESS);
        }
    }
}

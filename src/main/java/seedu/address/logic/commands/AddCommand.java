package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.SwitchToTabRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Content;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDateTime;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;

/**
 * Adds a task to the list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TITLE c/[CONTENT] start/[DATE] [TIME] end/[DATE] [TIME] #[TAG]...\n" + "Example: "
            + COMMAND_WORD + " Awesome title d/badass content start/2pm end/16:00 23 Mar #project #meeting";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String title, String content, String startDateTime, String endDateTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Title(title), new Content(content), new TaskDateTime(startDateTime, endDateTime),
                new UniqueTagList(tagSet), new Status(false));
    }

    // @@author A0125221Y
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);

            //jump to added task
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            int indexToAdd = lastShownList.indexOf(toAdd);
            if (indexToAdd == -1) {
                EventsCenter.getInstance().post(new SwitchToTabRequestEvent("all"));
                model.setCurrentList("all");
                model.updateFilteredListToShowAll();
                indexToAdd = model.getFilteredTaskList().indexOf(toAdd);
                EventsCenter.getInstance().post(new JumpToListRequestEvent(indexToAdd));
            } else {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(indexToAdd));
            }

            model.getUndoStack().push(COMMAND_WORD);
            model.getAddedStackOfTasks().push(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}

package seedu.mypotato.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.mypotato.commons.core.EventsCenter;
import seedu.mypotato.commons.core.Messages;
import seedu.mypotato.commons.events.ui.JumpToListRequestEvent;
import seedu.mypotato.commons.events.ui.SwitchToTabRequestEvent;
import seedu.mypotato.commons.exceptions.IllegalValueException;
import seedu.mypotato.commons.util.CollectionUtil;
import seedu.mypotato.logic.commands.exceptions.CommandException;
import seedu.mypotato.model.tag.UniqueTagList;
import seedu.mypotato.model.task.Content;
import seedu.mypotato.model.task.ReadOnlyTask;
import seedu.mypotato.model.task.Status;
import seedu.mypotato.model.task.Task;
import seedu.mypotato.model.task.TaskDateTime;
import seedu.mypotato.model.task.Title;
import seedu.mypotato.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [TITLE] c/[CONTENT] "
            + "start/[DATE][TIME] end/[DATE][END] #[TAG]...\n" + "Example: "
            + COMMAND_WORD + " 1 Pay c/bill end/10am 15 Jul #overspeed";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided "
                                                        + "& Task Title cannot be empty.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in myPotato.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     * @param editTaskDescriptor
     *            details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = null;
        try {
            editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        try {
            model.getUndoStack().push(COMMAND_WORD);
            model.getOldTask().push(new Task(taskToEdit));
            model.getCurrentTask().push(new Task(editedTask));

            model.updateTask(filteredTaskListIndex, editedTask);

            //jump to edited task
            lastShownList = model.getFilteredTaskList();
            int indexToEdit = lastShownList.indexOf(editedTask);
            if (indexToEdit == -1) {
                EventsCenter.getInstance().post(new SwitchToTabRequestEvent("all"));
                model.setCurrentList("all");
                model.updateFilteredListToShowAll();
                indexToEdit = model.getFilteredTaskList().indexOf(editedTask);
                EventsCenter.getInstance().post(new JumpToListRequestEvent(indexToEdit));
            } else {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(indexToEdit));
            }
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    //@@author A0144895N
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor)
            throws IllegalValueException {
        assert taskToEdit != null;
        Title updatedTitle = editTaskDescriptor.getTitle().orElseGet(taskToEdit::getTitle);
        Content updatedContent = editTaskDescriptor.getContent().orElseGet(taskToEdit::getContent);

        String updatedStartDateTime = editTaskDescriptor.getStartDateTime()
                .orElse(taskToEdit.getDateTime().getStartDateTimeString());
        String updatedEndDateTime = editTaskDescriptor.getEndDateTime()
                .orElse(taskToEdit.getDateTime().getEndDateTimeString());
        TaskDateTime updatedDateTime = null;
        if (editTaskDescriptor.getDateTime().isPresent()) {
            updatedDateTime = editTaskDescriptor.getDateTime().get();
        } else {
            updatedDateTime = new TaskDateTime(updatedStartDateTime, updatedEndDateTime);
        }

        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        Status status = editTaskDescriptor.getStatus().orElseGet(taskToEdit::getStatus);

        return new Task(updatedTitle, updatedContent, updatedDateTime, updatedTags, status);
    }
    //@@author

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<Content> content = Optional.empty();
        private Optional<String> startDateTime = Optional.empty();
        private Optional<String> endDateTime = Optional.empty();
        private Optional<TaskDateTime> dateTime = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<Status> status = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.content = toCopy.getContent();
            this.startDateTime = toCopy.getStartDateTime();
            this.endDateTime = toCopy.getEndDateTime();
            this.tags = toCopy.getTags();
            this.status = toCopy.getStatus();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.title, this.content,
                    this.startDateTime, this.endDateTime, this.tags);
        }

        public void setTitle(Optional<Title> title) {
            assert title != null;
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return title;
        }

        public void setContent(Optional<Content> content) {
            assert content != null;
            this.content = content;
        }

        public Optional<Content> getContent() {
            return content;
        }

        public void setDateTime(Optional<TaskDateTime> dateTime) {
            assert dateTime != null;
            this.dateTime = dateTime;
        }

        public Optional<TaskDateTime> getDateTime() {
            return dateTime;
        }

        public void setStartDateTime(Optional<String> startDateTime) {
            this.startDateTime = startDateTime;
        }

        public void setEndDateTime(Optional<String> endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<String> getStartDateTime() {
            return startDateTime;
        }

        public Optional<String> getEndDateTime() {
            return endDateTime;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        // @@author A0135753A
        public void setStatus(Optional<Status> status) {
            assert status != null;
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return status;
        }
    }
}

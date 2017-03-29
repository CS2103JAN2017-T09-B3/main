package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Content;
import seedu.address.model.task.DateValue;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDateTime;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;

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
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
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
        // model.getUndoStack().push(COMMAND_WORD);
        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        // ReadOnlyTask testing = lastShownList.get(filteredTaskListIndex - 1);

        try {
            model.getUndoStack().push(COMMAND_WORD);
            model.getOldTask().push(new Task(taskToEdit));
            model.getCurrentTask().push(new Task(editedTask));
            model.updateTask(filteredTaskListIndex, editedTask);
            // model.getOldTask().push(taskToEdit);
            // model.getCurrentTask().push(editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;
        Title updatedTitle = editTaskDescriptor.getTitle().orElseGet(taskToEdit::getTitle);
        Content updatedContent = editTaskDescriptor.getContent().orElseGet(taskToEdit::getContent);

        DateValue updatedStartDateTime = editTaskDescriptor.getDateTime().isPresent()
                ? editTaskDescriptor.getDateTime().get().getStartDateTime()
                        .orElse(taskToEdit.getDateTime().getStartDateTime().orElse(null))
                : taskToEdit.getDateTime().getStartDateTime().orElse(null);
        DateValue updatedEndDateTime = editTaskDescriptor.getDateTime().isPresent()
                ? editTaskDescriptor.getDateTime().get().getEndDateTime()
                        .orElse(taskToEdit.getDateTime().getEndDateTime().orElse(null))
                : taskToEdit.getDateTime().getEndDateTime().orElse(null);
        TaskDateTime updatedDateTime = new TaskDateTime(updatedStartDateTime, updatedEndDateTime);

        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        Status status = editTaskDescriptor.getStatus().orElseGet(taskToEdit::getStatus);

        return new Task(updatedTitle, updatedContent, updatedDateTime, updatedTags, status);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<Content> content = Optional.empty();
        private Optional<TaskDateTime> dateTime = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<Status> status = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.content = toCopy.getContent();
            this.dateTime = toCopy.getDateTime();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.title, this.content, this.dateTime, this.tags);
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
            this.dateTime = dateTime;
        }

        public Optional<TaskDateTime> getDateTime() {
            return dateTime;
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

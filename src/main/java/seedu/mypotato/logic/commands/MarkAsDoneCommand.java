//@@author A0135753A
package seedu.mypotato.logic.commands;

import java.util.Optional;

import seedu.mypotato.commons.core.Messages;
import seedu.mypotato.commons.core.UnmodifiableObservableList;
import seedu.mypotato.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.mypotato.logic.commands.exceptions.CommandException;
import seedu.mypotato.model.task.ReadOnlyTask;
import seedu.mypotato.model.task.Status;
import seedu.mypotato.model.task.UniqueTaskList.DuplicateTaskException;

public class MarkAsDoneCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task as done: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    public final int targetIndex;

    public MarkAsDoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
        Optional<Status> status = Optional.of(new Status(true));
        EditTaskDescriptor task = new EditTaskDescriptor();
        task.setStatus(status);
        EditCommand taskToEdit = new EditCommand(targetIndex, task);
        taskToEdit.setData(model);
        taskToEdit.execute();

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }
    public void markTask(ReadOnlyTask taskToMark) throws DuplicateTaskException {
        taskToMark.getStatus().setStatus(true);
    }

}

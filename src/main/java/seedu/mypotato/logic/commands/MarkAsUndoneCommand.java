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

public class MarkAsUndoneCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Marked Task as undone: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    public final int targetIndex;

    public MarkAsUndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUnmark = lastShownList.get(targetIndex - 1);

        Optional<Status> status = Optional.of(new Status(false));
        EditTaskDescriptor task = new EditTaskDescriptor();
        task.setStatus(status);
        EditCommand taskToEdit = new EditCommand(targetIndex, task);
        taskToEdit.setData(model);
        taskToEdit.execute();

        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }
    public void unmarkTask(ReadOnlyTask taskToMark) throws DuplicateTaskException {
        taskToMark.getStatus().setStatus(false);
    }

}

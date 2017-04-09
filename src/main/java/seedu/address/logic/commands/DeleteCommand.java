package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the list.
 * Delete a task deadline.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Deadline deleted for %1$s!";
    public static final String EMPTY = "";

    public final int targetIndex;
    public String deadline;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.deadline = EMPTY;
    }

    public DeleteCommand(int targetIndex, String deadline) {
        this.targetIndex = targetIndex;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {

            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        if (deadline.equals(EMPTY)) {
            try {
                int indexRemoved = model.deleteTask(taskToDelete);
                model.getUndoStack().push(COMMAND_WORD);
                model.getDeletedStackOfTasks().push(taskToDelete);
                model.getDeletedStackOfTasksIndex().push(indexRemoved);
            } catch (TaskNotFoundException tnfe) {
                System.out.println("Task not found");
            }
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        } else {
            EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
            editTaskDescriptor.setStartDateTime(Optional.of(new String(CliSyntax.EMPTY_STRING)));
            editTaskDescriptor.setEndDateTime(Optional.of(new String(CliSyntax.EMPTY_STRING)));
            EditCommand taskToEdit = new EditCommand(targetIndex, editTaskDescriptor);
            taskToEdit.setData(model);
            taskToEdit.execute();
            return new CommandResult(String.format(MESSAGE_DELETE_DEADLINE_SUCCESS,
                                                    taskToDelete.getTitle().fullTitle));
        }
    }

}

//@@ Zhang Yan Hao A0135753A
package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
	
public class MarkAsUndoneCommand extends Command {

    public static final String COMMAND_WORD = "markasundone";

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

            System.out.println("invalid index");
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUnmark = lastShownList.get(targetIndex - 1);

        try{
        	this.unmarkTask(taskToUnmark);
        }catch (UniqueTaskList.DuplicateTaskException e) {
        	throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }
    public void unmarkTask(ReadOnlyTask taskToUnmark) throws DuplicateTaskException{
    	Task task = (Task) taskToUnmark;
    	task.getStatus().setStatus(false);
    }
}

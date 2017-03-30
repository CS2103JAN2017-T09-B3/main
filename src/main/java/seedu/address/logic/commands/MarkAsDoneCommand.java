///@@author A0135753A
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.EMPTY_STRING;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Content;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDateTime;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class MarkAsDoneCommand extends Command {

    public static final String COMMAND_WORD = "markasdone";

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

        try {
            Task task = new Task(new Title(taskToMark.getTitle().toString()),
                    new Content(
                            taskToMark.getContent().isThereContent() ? taskToMark.getContent().toString(): EMPTY_STRING), 
                    new TaskDateTime(taskToMark.getDateTime().getStartDateTime().isPresent()
                            ? taskToMark.getDateTime().getStartDateTime().get().toString() : EMPTY_STRING,
                                    taskToMark.getDateTime().getEndDateTime().isPresent()
                                    ? taskToMark.getDateTime().getEndDateTime().get().toString() : EMPTY_STRING),
                    new UniqueTagList(),
            	    new Status(true));
            model.updateTask(taskToMark, task);
            return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException e) {
            System.out.println("Exception");
        } catch (TaskNotFoundException e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));

    }

}

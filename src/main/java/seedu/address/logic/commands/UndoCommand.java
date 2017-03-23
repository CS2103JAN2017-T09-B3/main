package seedu.address.logic.commands;

import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0125221Y
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undo a previous add/ delete command"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Previous command has been undo";
    public static final String MESSAGE_FAIL = "No previous command found";

    private String prevCommand;

    @Override
    public CommandResult execute() {

        if (!model.getUndoStack().isEmpty()) {
            prevCommand = model.getUndoStack().pop();

            switch (prevCommand) {

            case AddCommand.COMMAND_WORD:
                return undoAdd();

            case DeleteCommand.COMMAND_WORD:
                return undoDelete();

            case ClearCommand.COMMAND_WORD:
                return undoClear();

            }
        } else {
            prevCommand = "";
        }

        return new CommandResult(MESSAGE_FAIL);

    }

    private CommandResult undoAdd() {
        assert model != null;
        if (model.getDeletedStackOfTasksAdd().isEmpty()) {
            return new CommandResult(String.format("Unable to undo"));
        } else {
            try {
                ReadOnlyTask reqTask = model.getDeletedStackOfTasksAdd().pop();
                model.deleteTask(reqTask);
            } catch (TaskNotFoundException tnfe) {
                return new CommandResult(String.format("Unable to undo"));
            }
            return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS));
        }
    }

    private CommandResult undoDelete() {
        if (model.getDeletedStackOfTasks().isEmpty() || model.getDeletedStackOfTasksIndex().isEmpty()) {
            return new CommandResult("Unable to undo");
        }

        ReadOnlyTask taskToReAdd = model.getDeletedStackOfTasks()
                .pop(); /** Gets the required task to reAdd */


        try {
            model.addTask((Task) taskToReAdd);
        } catch (DuplicateTaskException e) {
            return new CommandResult("Unable to undo");
        }
        return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS));
    }

    private CommandResult undoClear() {
        assert model != null;
        model.revertData();
        return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
    }

}


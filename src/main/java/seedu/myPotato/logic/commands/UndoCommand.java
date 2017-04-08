package seedu.myPotato.logic.commands;

import seedu.myPotato.model.task.ReadOnlyTask;
import seedu.myPotato.model.task.Task;
import seedu.myPotato.model.task.UniqueTaskList;
import seedu.myPotato.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.myPotato.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undo a previous add/ delete command"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Previous command has been undo";
    public static final String MESSAGE_FAIL = "No previous command found";

    private static final String MESSAGE_DUPLICATE_TASK = "Task is already existed";

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

            case EditCommand.COMMAND_WORD:
                try {
                    return undoEdit();
                } catch (TaskNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            prevCommand = "";
        }

        return new CommandResult(MESSAGE_FAIL);

    }

    private CommandResult undoAdd() {
        assert model != null;
        if (model.getAddedStackOfTasks().isEmpty()) {
            return new CommandResult(String.format("Unable to undo"));
        } else {
            try {
                ReadOnlyTask reqTask = model.getAddedStackOfTasks().pop();
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

        int targetIndex = model.getDeletedStackOfTasksIndex().pop();

        try {
            model.addTaskIdx((Task) taskToReAdd, targetIndex);
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

    private CommandResult undoEdit() throws TaskNotFoundException {
        assert model != null;
        if (model.getOldTask().isEmpty() && model.getCurrentTask().isEmpty()) {
            return new CommandResult(String.format(UndoCommand.MESSAGE_FAIL));
        } else {
            try {
                Task updated = (Task) model.getOldTask().pop();
                Task original = (Task) model.getCurrentTask().pop();
                model.updateTask(original, updated);
                model.getOldNextTask().push(original);
                model.getNewNextTask().push(updated);
            } catch (UniqueTaskList.DuplicateTaskException utle) {
                return new CommandResult(UndoCommand.MESSAGE_DUPLICATE_TASK);
            }
            return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
        }
    }

}

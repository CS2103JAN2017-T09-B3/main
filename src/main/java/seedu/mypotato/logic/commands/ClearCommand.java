package seedu.mypotato.logic.commands;

import seedu.mypotato.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "myPotato has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        model.getUndoStack().push(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

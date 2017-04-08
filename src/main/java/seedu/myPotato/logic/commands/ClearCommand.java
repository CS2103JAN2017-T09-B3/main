package seedu.myPotato.logic.commands;

import seedu.myPotato.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "myPotato has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new AddressBook());
        model.getUndoStack().push(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

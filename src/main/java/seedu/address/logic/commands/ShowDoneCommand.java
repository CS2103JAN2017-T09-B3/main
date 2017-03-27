//@@author A0135753A
package seedu.address.logic.commands;

public class ShowDoneCommand extends Command {
    public static final String COMMAND_WORD = "showdone";
    public static final String MESSAGE_SHOWN_ACKNOWLEDGEMENT = "Done List Shown";

    @Override
    public CommandResult execute() {
        model.getDoneTaskList();
        return new CommandResult(MESSAGE_SHOWN_ACKNOWLEDGEMENT);
    }
}

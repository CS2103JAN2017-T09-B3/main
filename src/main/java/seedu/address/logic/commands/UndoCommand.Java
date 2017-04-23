package seedu.address.logic.commands;

import java.util.Stack;

public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undo a previous add/ delete command"
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Previous command has been undo";

	public CommandResult execute() {

		if (!addOrDeleteStacks.empty()) {
			PrevCommand = addOrDeleteStacks.pop();

			switch (PrevCommand.getCommand()) {

			case AddCommand.COMMAND_WORD:
				return DeleteCommand(PrevCommand.getIndex());

			case DeleteCommand.COMMAND_WORD:
				return AddCommand(PrevCommand.getContent(), PrevCommand.getDateTime, PreviousCommand.getTags);

			}

			System.out.println(MESSAGE_SUCCESS);

		}

	}
}

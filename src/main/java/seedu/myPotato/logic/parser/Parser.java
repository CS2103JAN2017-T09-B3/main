package seedu.myPotato.logic.parser;

import static seedu.myPotato.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.myPotato.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.myPotato.logic.commands.AddCommand;
import seedu.myPotato.logic.commands.ClearCommand;
import seedu.myPotato.logic.commands.Command;
import seedu.myPotato.logic.commands.DeleteCommand;
import seedu.myPotato.logic.commands.EditCommand;
import seedu.myPotato.logic.commands.ExitCommand;
import seedu.myPotato.logic.commands.FindCommand;
import seedu.myPotato.logic.commands.HelpCommand;
import seedu.myPotato.logic.commands.IncorrectCommand;
import seedu.myPotato.logic.commands.ListCommand;
import seedu.myPotato.logic.commands.MarkAsDoneCommand;
import seedu.myPotato.logic.commands.MarkAsUndoneCommand;
import seedu.myPotato.logic.commands.OpenCommand;
import seedu.myPotato.logic.commands.SaveCommand;
import seedu.myPotato.logic.commands.SelectCommand;
import seedu.myPotato.logic.commands.UndoCommand;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);
        //@@author A0135753A
        case MarkAsDoneCommand.COMMAND_WORD:
            return new MarkAsDoneCommandParser().parse(arguments);
        case MarkAsUndoneCommand.COMMAND_WORD:
            return new MarkAsUndoneCommandParser().parse(arguments);
        //@@author

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

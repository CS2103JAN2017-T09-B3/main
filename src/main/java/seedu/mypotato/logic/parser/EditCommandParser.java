package seedu.mypotato.logic.parser;

import static seedu.mypotato.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mypotato.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.mypotato.logic.parser.CliSyntax.PREFIX_DATE_TIME_END;
import static seedu.mypotato.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.mypotato.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.mypotato.commons.exceptions.IllegalValueException;
import seedu.mypotato.logic.commands.Command;
import seedu.mypotato.logic.commands.EditCommand;
import seedu.mypotato.logic.commands.IncorrectCommand;
import seedu.mypotato.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.mypotato.model.tag.UniqueTagList;
import seedu.mypotato.model.task.TaskDateTime;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_CONTENT, PREFIX_DATE_TIME_START, PREFIX_DATE_TIME_END, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setTitle(ParserUtil.parseTitle(preambleFields.get(1)));
            editTaskDescriptor.setContent(ParserUtil.parseContent(argsTokenizer.getValue(PREFIX_CONTENT)));

            Optional<String> parsedStartDateTime = ParserUtil.parseStartDateTime(
                    argsTokenizer.getValue(PREFIX_DATE_TIME_START));
            Optional<String> parsedEndDateTime = ParserUtil.parseEndDateTime(
                    argsTokenizer.getValue(PREFIX_DATE_TIME_END));
            editTaskDescriptor.setStartDateTime(parsedStartDateTime);
            editTaskDescriptor.setEndDateTime(parsedEndDateTime);
            if (parsedStartDateTime.isPresent() && !parsedStartDateTime.get().equals("")
                    && parsedEndDateTime.isPresent() && !parsedEndDateTime.get().equals("")) {
                editTaskDescriptor.setDateTime(Optional.of(
                        new TaskDateTime(parsedStartDateTime.get(), parsedEndDateTime.get())));
            }

            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

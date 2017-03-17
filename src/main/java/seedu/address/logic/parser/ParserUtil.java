package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Content;
import seedu.address.model.task.TaskDateTime;
import seedu.address.model.task.Title;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>\\d+).*");
    private static final Pattern DEADLINE_ARGS_FORMAT = Pattern.compile("(?<keyword>deadline)", Pattern.CASE_INSENSITIVE);

    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty,
     * or if the list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new HashSet<>(elements);
    }

    /**
    * Splits a preamble string into ordered fields.
    * @return A list of size {@code numFields} where the ith element is the ith field value if specified in
    *         the input, {@code Optional.empty()} otherwise.
    */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        assert title != null;
        return title.isPresent() ? Optional.of(new Title(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> content} into an {@code Optional<Content>} if {@code content} is present.
     */
    public static Optional<Content> parseContent(Optional<String> content) throws IllegalValueException {
        assert content != null;
        return content.isPresent() ? Optional.of(new Content(content.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> dateTime} into an {@code Optional<TaskDateTime>} if {@code dateTime} is present.
     */
    public static Optional<TaskDateTime> parseDateTime(Optional<String> dateTime) throws IllegalValueException {
        assert dateTime != null;
        return dateTime.isPresent() ? Optional.of(new TaskDateTime(dateTime.get())) : Optional.empty();
    }

    public static Optional<String> parseDeadline(String command) {
        final Matcher matcher = DEADLINE_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.find()) {
            return Optional.empty();
        }
        String keyword = matcher.group("keyword");
        return Optional.of(keyword);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }
}

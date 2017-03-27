package seedu.address.model;

import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.DateValue;
import seedu.address.model.task.DateWithTime;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0144895N
public class ListFilter {

    public ListFilter() {}

    public static void filterAll(FilteredList<ReadOnlyTask> filteredList) {
        filteredList.setPredicate(null);
    }

    public static void filterKeywords(FilteredList<ReadOnlyTask> filteredList,
            boolean isInContent, Set<String> keywords) {
        ListFilter.filterExpression(filteredList,
                new ListFilter().new PredicateExpression(new ListFilter().new NameQualifier(isInContent, keywords)));
    }

    public static void filterExpression(FilteredList<ReadOnlyTask> filteredList, Expression expression) {
        filteredList.setPredicate(expression::satisfies);
    }

    public static void filterEndDate(FilteredList<ReadOnlyTask> filteredList, DateValue dateValue) {
        assert dateValue != null;
        if (dateValue.isWithTime()) {
            filteredList.setPredicate(
                t -> t.getDateTime().getEndDateTime().orElse(new DateWithTime())
                    .getStringValue().equals(dateValue.getStringValue()));
        } else {
            filteredList.setPredicate(
                t -> t.getDateTime().getEndDateTime().orElse(new DateWithTime())
                    .getDateValue().equals(dateValue.getDateValue()));
        }
    }

 // ========== Inner classes/interfaces used for filtering
    // =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private boolean isInContent;

        NameQualifier(boolean isInContent, Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
            this.isInContent = isInContent;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(isInContent
                            ? keyword -> (StringUtil.containsWordIgnoreCase(task.getTitle().fullTitle, keyword)
                                    || StringUtil.containsWordIgnoreCase(task.getContent().fullContent, keyword))
                            : keyword -> StringUtil.containsWordIgnoreCase(task.getTitle().fullTitle, keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
}

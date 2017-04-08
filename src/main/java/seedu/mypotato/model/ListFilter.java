package seedu.mypotato.model;

import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.mypotato.commons.util.StringUtil;
import seedu.mypotato.model.task.DateMaker;
import seedu.mypotato.model.task.DateValue;
import seedu.mypotato.model.task.DateWithTime;
import seedu.mypotato.model.task.ReadOnlyTask;

//@@author A0144895N
public class ListFilter {

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

    public static void filterStartDate(FilteredList<ReadOnlyTask> filteredList, DateValue dateValue) {
        assert dateValue != null;
        if (dateValue.isWithTime()) {
            filteredList.setPredicate(
                t -> t.getDateTime().getStartDateTime().orElse(new DateWithTime())
                    .getStringValue().equals(dateValue.getStringValue()));
        } else {
            filteredList.setPredicate(
                t -> t.getDateTime().getStartDateTime().orElse(new DateWithTime())
                    .getDateValue().equals(dateValue.getDateValue()));
        }
    }

    public static void filterStartAndEndDate(FilteredList<ReadOnlyTask> filteredList, DateValue dateValue) {
        assert dateValue != null;
        if (dateValue.isWithTime()) {
            filteredList.setPredicate(
                t -> (t.getDateTime().getStartDateTime().orElse(new DateWithTime())
                    .getStringValue().equals(dateValue.getStringValue())
                    || t.getDateTime().getEndDateTime().orElse(new DateWithTime())
                    .getStringValue().equals(dateValue.getStringValue())));
        } else {
            filteredList.setPredicate(
                t -> (t.getDateTime().getStartDateTime().orElse(new DateWithTime())
                    .getDateValue().equals(dateValue.getDateValue())
                    || t.getDateTime().getEndDateTime().orElse(new DateWithTime())
                    .getDateValue().equals(dateValue.getDateValue())));
        }
    }

    public static void filterToday(FilteredList<ReadOnlyTask> filteredList) {
        DateValue todayDate = DateMaker.getCurrentDate();
        ListFilter.filterStartAndEndDate(filteredList, todayDate);
    }

    public static void filterCompleted (FilteredList<ReadOnlyTask> filteredList) {
        filteredList.setPredicate(t -> t.getStatus().getStatus());
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

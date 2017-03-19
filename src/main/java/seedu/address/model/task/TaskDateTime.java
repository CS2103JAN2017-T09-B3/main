package seedu.address.model.task;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidDate(String)}
 */
public class TaskDateTime {

    public static final String MESSAGE_START_END_INVALID = "Start time should be before end time";
    public static final String DEFAULT_VALUE = "";

    private final DateValue startDateTime;
    private final DateValue endDateTime;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public TaskDateTime(String startDateTime, String endDateTime) throws IllegalValueException {

        String trimmedStartDateTime = startDateTime.trim();
        String trimmedEndDateTime = endDateTime.trim();

        DateMaker maker = new DateMaker();

        maker.makeDate(trimmedStartDateTime);
        this.startDateTime = maker.getDateValue();

        maker.makeDate(trimmedEndDateTime);
        this.endDateTime = maker.getDateValue();

        alignStartWithEndDateTime();

        if (!isValidStartAndEndDateTime()) {
            throw new IllegalValueException(MESSAGE_START_END_INVALID);
        }
    }

    private boolean isValidStartAndEndDateTime() {
        return ((!isThereStartDateTime() || (!isThereEndDateTime())
                || isThereStartDateTime() && isThereEndDateTime()
                && this.startDateTime.getFullDate().before(this.endDateTime.getFullDate())));
    }

    private void alignStartWithEndDateTime() {
        if (isThereStartDateTime() && isThereEndDateTime() && this.startDateTime.getYear() < DateMaker.OLDEST_YEAR) {
            this.startDateTime.setDate(this.endDateTime.getDate());
            this.startDateTime.setMonth(this.endDateTime.getMonth());
            this.startDateTime.setYear(this.endDateTime.getYear());
        }
    }

    public boolean isThereStartDateTime() {
        return this.startDateTime != null;
    }

    public boolean isThereEndDateTime() {
        return this.endDateTime != null;
    }

    public Optional<DateValue> getStartDateTime() {
        return isThereStartDateTime() ? Optional.of(this.startDateTime) : Optional.empty();
    }

    public Optional<DateValue> getEndDateTime() {
        return isThereEndDateTime() ? Optional.of(this.endDateTime) : Optional.empty();
    }

    @Override
    public String toString() {
        if (!isThereStartDateTime() && !isThereEndDateTime()) {
            return "";
        } else if (!isThereStartDateTime()) {
            return this.endDateTime.getStringValue();
        } else if (!isThereEndDateTime()) {
            return this.startDateTime.getStringValue();
        } else {
            if (this.startDateTime.getDateValue().equals(this.endDateTime.getDateValue())) {
                return this.startDateTime.getDateValue() + " "
                        + this.startDateTime.getTimeValue() + " - " + this.endDateTime.getTimeValue();
            } else {
                return this.startDateTime.getStringValue() + " - " + this.endDateTime.getStringValue();
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDateTime // instanceof handles nulls
                        && this.toString().equals(((TaskDateTime) other).toString())); // state
        // check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}

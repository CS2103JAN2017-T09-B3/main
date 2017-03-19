package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidDate(String)}
 */
public class TaskDateTime {

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS = "Due date should contain valid date in format "
            + "day/month/year hour:minute";
    public static final String MESSAGE_START_END_INVALID = "Start time should be before end time";
    public static final String DEFAULT_VALUE = "";

    public final String startValue;
    public final String endValue;
    public final Date startDateTime;
    public final Date endDateTime;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public TaskDateTime(String startDateTime, String endDateTime) throws IllegalValueException {

        String trimmedStartDateTime = startDateTime.trim();
        String trimmedEndDateTime = endDateTime.trim();
        if (startDateTime.equals(DEFAULT_VALUE) && endDateTime.equals(DEFAULT_VALUE)) {
            this.startDateTime = null;
            this.endDateTime = null;
            this.startValue = DEFAULT_VALUE;
            this.endValue = DEFAULT_VALUE;
        } else if (startDateTime.equals(DEFAULT_VALUE)) {
            this.startDateTime = null;
            this.startValue = DEFAULT_VALUE;
            this.endDateTime = makeDateTime(trimmedEndDateTime);
            this.endValue = trimmedEndDateTime;
        } else if (endDateTime.equals(DEFAULT_VALUE)) {
            this.endDateTime = null;
            this.endValue = DEFAULT_VALUE;
            this.startDateTime = makeDateTime(trimmedStartDateTime);
            this.startValue = trimmedStartDateTime;
        } else {
            Date start = makeDateTime(trimmedStartDateTime);
            Date end = makeDateTime(trimmedEndDateTime);
            start = alignStartWithEnd(start, end);
            if (!isValidStartAndEndDateTime(start, end)) {
                System.out.println(start.toString());
                System.out.println(end.toString());
                throw new IllegalValueException(MESSAGE_START_END_INVALID);
            }
            this.startDateTime = start;
            this.endDateTime = end;
            this.startValue = trimmedStartDateTime;
            this.endValue = trimmedEndDateTime;
        }
    }

    /**
     * Returns true if a given string is valid date
     */
    public static boolean isValidDateTime(String value) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setLenient(false);
        try {
            format.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if start date is before end date
     */
    public boolean isValidStartAndEndDateTime(Date start, Date end) {
        return start.before(end);
    }

    /**
     * Returns true if there is a start time
     */
    public boolean isThereStartDateTime() {
        return !(this.startDateTime == null);
    }

    /**
     * Returns true if there is an end time
     */
    public boolean isThereEndDateTime() {
        return !(this.endDateTime == null);
    }

    /**
     * Returns object containing date and time given by string value
     * @param dateTime
     * @return
     */
    public Date makeDateTime(String dateTime) throws IllegalValueException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setLenient(false);
        Date result;
        try {
            result = format.parse(dateTime);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_CONSTRAINTS);
        }
        return result;
    }

    /**
     * Sets date of start time is date of end time if date is not specified in start time
     */
    Date alignStartWithEnd(Date start, Date end) {
        if (start.getYear() + 1900 < 2000) {
            System.out.println(start.getYear());
            start.setDate(end.getDate());
            start.setMonth(end.getMonth());
            start.setYear(end.getYear());
        }
        return start;
    }

    public Date getStartDateTime() {
        return this.startDateTime;
    }

    public Date getEndDateTime() {
        return this.endDateTime;
    }

    @Override
    public String toString() {
        if (this.startDateTime == null && this.endDateTime == null) {
            return DEFAULT_VALUE;
        } else if (this.startDateTime == null) {
            return this.endDateTime.toString();
        } else if (this.endDateTime == null) {
            return "from" + this.startDateTime.toString();
        } else {
            return this.startDateTime.toString() + " - " + this.endDateTime.toString();
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

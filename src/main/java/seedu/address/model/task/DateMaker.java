package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0144895N
/**
 * Makes a date object of type {@code DateValue} from given input string of all forms specified in
 * {@code DATE_FORMATS} and {@code TIME_FORMATS}
 * Date and time can be in any order
 */
public class DateMaker {

    private static final String[] DATE_FORMATS = { "dd/MM/yyyy", "dd/MM/yy", "dd/MM", "dd-MM-yyyy", "dd-MM-yy", "dd-MM",
        "dd MMM", "dd MMM yyyy", "dd MMM yy", "EEE, dd MMM yyyy", "dd.MM.yy" };

    private static final String[] TIME_FORMATS = { "hh:mmaa", "HH:mm", "hhaa" };

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS = "Invalid date and time format";

    public static final int OLDEST_YEAR = 2000;

    private DateValue date;

    public DateMaker() {
        resetMaker();
    }

    /**
     * Input string can be empty. In this case, the date object is null
     * @throws IllegalValueException: given input string is not in supported format
     */
    public void makeDate(String dateString) throws IllegalValueException {
        assert dateString != null;
        resetMaker();
        dateString = dateString.trim();
        if (dateString.equals("")) {
            date = null;
            return;
        }

        Optional<DateWithTime> dateWithTime;
        dateWithTime = makeAsDateWithTime(dateString);
        if (dateWithTime.isPresent()) {
            date = dateWithTime.get();
            refineYear();
            return;
        }

        Optional<DateWithoutTime> dateWithoutTime;
        dateWithoutTime = makeAsDateWithoutTime(dateString);
        if (dateWithoutTime.isPresent()) {
            date = dateWithoutTime.get();
            refineYear();
            return;
        }

        Optional<DateWithTime> timeOnly;
        timeOnly = makeAsTimeOnly(dateString);
        if (timeOnly.isPresent()) {
            date = timeOnly.get();
            return;
        } else {
            throw new IllegalValueException(MESSAGE_DATE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Makes date object containing both date and time information
     */
    private Optional<DateWithTime> makeAsDateWithTime(String dateString) {
        for (String dateFormat : DATE_FORMATS) {
            for (String timeFormat : TIME_FORMATS) {
                String format = dateFormat + " " + timeFormat;
                SimpleDateFormat df = new SimpleDateFormat(format);
                df.setLenient(false);
                try {
                    Date parsedDate = df.parse(dateString);
                    return Optional.of(new DateWithTime(parsedDate));
                } catch (ParseException pe1) {
                    format = timeFormat + " " + dateFormat;
                    df = new SimpleDateFormat(format);
                    df.setLenient(false);
                    try {
                        Date parsedDate = df.parse(dateString);
                        return Optional.of(new DateWithTime(parsedDate));
                    } catch (ParseException pe2) {
                        continue;
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Makes date object containing only date information
     */
    private Optional<DateWithoutTime> makeAsDateWithoutTime(String dateString) {
        for (String dateFormat : DATE_FORMATS) {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            try {
                Date parsedDate = df.parse(dateString);
                return Optional.of(new DateWithoutTime(parsedDate));
            } catch (ParseException pe) {
                continue;
            }
        }
        return Optional.empty();
    }

    /**
     * Makes date object containing only time information
     */
    private Optional<DateWithTime> makeAsTimeOnly(String dateString) {
        for (String timeFormat : TIME_FORMATS) {
            SimpleDateFormat df = new SimpleDateFormat(timeFormat);
            df.setLenient(false);
            try {
                Date parsedDate = df.parse(dateString);
                return Optional.of(new DateWithTime(parsedDate));
            } catch (ParseException pe) {
                continue;
            }
        }
        return Optional.empty();
    }

    /**
     * Sets year of this date object to be the current year if the date object containing only time information
     */
    private void refineYear() {
        if (this.date.getYear() < OLDEST_YEAR) {
            this.date.setYear(getCurrentTime().getYear());
        }
    }

    /**
     * Returns a date object representing current date time
     */
    public static DateValue getCurrentTime() {
        Date current = new Date();
        return new DateWithTime(current);
    }

    /**
     * Returns a date object representing current date only
     */
    public static DateValue getCurrentDate() {
        Date current = new Date();
        return new DateWithoutTime(current);
    }

    private void resetMaker() {
        date = null;
    }

    public DateValue getDateValue() {
        return this.date;
    }
}

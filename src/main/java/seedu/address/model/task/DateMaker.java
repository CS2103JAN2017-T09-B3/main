package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateMaker {

    private static final String[] DATE_FORMATS = { "dd/MM/yyyy", "dd/MM/yy", "dd/MM", "dd-MM-yyyy", "dd-MM-yy", "dd-MM",
            "dd MMM", "EEE, dd MMM yyyy" };

    private static final String[] TIME_FORMATS = { "HH:mm", "hhaa", "hh:mmaa" };

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS = "Invalid date and time format";
    
    public static final int OLDEST_YEAR = 2000;

    private DateValue date;

    public DateMaker() {
        resetMaker();
    }

    public void makeDate(String dateString) throws IllegalValueException {
        resetMaker();
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

    private void refineYear() {
        if (this.date.getYear() < OLDEST_YEAR) {
            this.date.setYear(getCurrentTime().getYear());
        }
    }

    public DateValue getCurrentTime() {
        Date current = new Date();
        return new DateWithTime(current);
    }

    private void resetMaker() {
        date = null;
    }

    public DateValue getDateValue() {
        return this.date;
    }
}

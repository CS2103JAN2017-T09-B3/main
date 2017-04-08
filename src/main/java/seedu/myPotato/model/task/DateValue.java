package seedu.myPotato.model.task;

import java.util.Date;

//@@author A0144895N
/**
 * An abstract class represents date (day, month, year) and time (hour, minute)
 */
public abstract class DateValue {

    public abstract Date getFullDate();

    //returns a string of both date and time information
    public abstract String getStringValue();

    //returns a string of date only
    public abstract String getDateValue();

    //returns a string of time only
    public abstract String getTimeValue();

    public abstract boolean isWithTime();

    public abstract int getDate();
    public abstract int getMonth();
    public abstract int getYear();
    public abstract int getHour();
    public abstract int getMinute();

    public abstract void setDate(int date);
    public abstract void setMonth(int month);
    public abstract void setYear(int year);
    public abstract void setHour(int hour);
    public abstract void setMinute(int minute);
}

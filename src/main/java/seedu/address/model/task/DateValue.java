package seedu.address.model.task;

import java.util.Date;

public abstract class DateValue {

    public abstract String getStringValue();
    public abstract Date getFullDate();

    public abstract int getDate();
    public abstract int getMonth();
    public abstract int getYear();
    public abstract int getHour();
    public abstract int getMinute();

    public abstract String getDateValue();
    public abstract String getTimeValue();

    public abstract void setDate(int date);
    public abstract void setMonth(int month);
    public abstract void setYear(int year);
    public abstract void setHour(int hour);
    public abstract void setMinute(int minute);

    public abstract String toString();
}

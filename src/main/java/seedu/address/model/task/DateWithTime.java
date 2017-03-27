package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;

//@@author A0144895N
/**
 * Represents a deadline with both date and time
 */
public class DateWithTime extends DateValue {

    private String value;
    private final Date date;

    public DateWithTime() {
        this.value = "";
        this.date = new Date(1970, 1, 1);
    }
    public DateWithTime(Date date) {
        this.date = date;
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
        this.value = df.format(date);
    }

    @Override
    public String getStringValue() {
        return value;
    }

    @Override
    public Date getFullDate() {
        return this.date;
    }

    @Override
    public boolean isWithTime() {
        return true;
    }

    @Override
    public int getDate() {
        return this.date.getDate();
    }

    @Override
    public int getMonth() {
        return this.date.getMonth() + 1;
    }

    @Override
    public int getYear() {
        return this.date.getYear() + 1900;
    }

    @Override
    public int getHour() {
        return this.date.getHours();
    }

    @Override
    public int getMinute() {
        return this.date.getMinutes();
    }

    @Override
    public String getDateValue() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy");
        return df.format(this.date);
    }

    @Override
    public String getTimeValue() {
        int colonIndex = this.value.indexOf(":");
        return this.value.substring(colonIndex - 2).trim();
    }

    @Override
    public void setDate(int date) {
        this.date.setDate(date);
        updateStringValue();
    }

    @Override
    public void setMonth(int month) {
        this.date.setMonth(month - 1);
        updateStringValue();
    }

    @Override
    public void setYear(int year) {
        this.date.setYear(year - 1900);
        updateStringValue();
    }

    @Override
    public void setHour(int hour) {
        this.date.setHours(hour);
        updateStringValue();
    }

    @Override
    public void setMinute(int minute) {
        this.date.setMinutes(minute);
        updateStringValue();
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        return o == this
                || (o instanceof DateWithTime
                && this.getDateValue().equals(((DateWithTime) o).getDateValue()));
    }

    /**
     * updates string representing date when changing date and time value
     */
    private void updateStringValue() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
        this.value = df.format(date);
    }
}

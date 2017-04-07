package seedu.address.model.task.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DateWithTime;
import seedu.address.model.task.DateWithoutTime;
import seedu.address.model.task.TaskDateTime;

//@@author A0144895N
public class TaskDateTimeTest {

    @Test
    public void isValidStartAndEndDate() {

        DateWithTime startDateWithTime;
        DateWithTime endDateWithTime;
        DateWithoutTime startDateWithoutTime;
        DateWithoutTime endDateWithoutTime;

        // --Invalid start and end date time--

        // date is invalid
        startDateWithTime = new DateWithTime(new Date(117, 3, 10, 5, 10));
        endDateWithTime = new DateWithTime(new Date(117, 2, 10, 6, 15));
        assertFalse(TaskDateTime.isValidStartAndEndDateTime(startDateWithTime, endDateWithTime));

        // time is invalid
        startDateWithTime = new DateWithTime(new Date(117, 3, 10, 5, 10));
        endDateWithTime = new DateWithTime(new Date(117, 3, 10, 4, 15));
        assertFalse(TaskDateTime.isValidStartAndEndDateTime(startDateWithTime, endDateWithTime));

        // date is invalid
        startDateWithoutTime = new DateWithoutTime(new Date(117, 7, 11));
        endDateWithoutTime = new DateWithoutTime(new Date(117, 4, 15));
        assertFalse(TaskDateTime.isValidStartAndEndDateTime(startDateWithoutTime, endDateWithoutTime));

        // --Valid start and end date time--

        startDateWithoutTime = new DateWithoutTime(new Date(117, 1, 10));
        endDateWithoutTime = new DateWithoutTime(new Date(117, 2, 10));
        assertTrue(TaskDateTime.isValidStartAndEndDateTime(startDateWithoutTime, endDateWithoutTime));

        startDateWithTime = new DateWithTime(new Date(117, 3, 6, 5, 10));
        endDateWithTime = new DateWithTime(new Date(117, 3, 6, 6, 15));
        assertTrue(TaskDateTime.isValidStartAndEndDateTime(startDateWithTime, endDateWithTime));

        // the same start and end date time
        startDateWithTime = new DateWithTime(new Date(117, 3, 10, 5, 10));
        endDateWithTime = new DateWithTime(new Date(117, 3, 10, 5, 10));
        assertTrue(TaskDateTime.isValidStartAndEndDateTime(startDateWithTime, endDateWithTime));
    }

    @Test
    public void testAlignDateTime() {
        try {
            // align with today with end time
            String inputStart = "";
            String inputEnd = "3pm";
            TaskDateTime outputTaskDateTime = new TaskDateTime(inputStart, inputEnd);
            Date date = new Date();
            date.setHours(15);
            date.setMinutes(0);
            String expectedOutput = new SimpleDateFormat(DateWithTime.DATE_WITH_TIME_FORMAT).format(date);
            assertEquals(expectedOutput, outputTaskDateTime.toString());

            // align with today without end time
            inputStart = "2pm";
            inputEnd = "";
            outputTaskDateTime = new TaskDateTime(inputStart, inputEnd);
            date = new Date();
            date.setHours(14);
            date.setMinutes(0);
            expectedOutput = new SimpleDateFormat(DateWithTime.DATE_WITH_TIME_FORMAT).format(date);
            assertEquals(expectedOutput, outputTaskDateTime.toString());

            // align with end time
            inputStart = "3pm";
            inputEnd = "4pm 25 Mar";
            outputTaskDateTime = new TaskDateTime(inputStart, inputEnd);
            date = new Date();
            date.setDate(25);
            date.setMonth(2);
            expectedOutput = new SimpleDateFormat(DateWithoutTime.DATE_WITHOUT_TIME_FORMAT).format(date)
                    + " 15:00 - 16:00";
            assertEquals(expectedOutput, outputTaskDateTime.toString());
        } catch (IllegalValueException e) {

        }
    }

    @Test
    public void testOutputFormat() {

        DateWithTime startDateWithTime;
        DateWithTime endDateWithTime;

        try {
            startDateWithTime = new DateWithTime(new Date(117, 3, 10, 5, 10));
            endDateWithTime = new DateWithTime(new Date(117, 2, 10, 6, 15));
            TaskDateTime taskDateTime = new TaskDateTime(startDateWithTime, endDateWithTime);
            String expectedOutput = new SimpleDateFormat(DateWithoutTime.DATE_WITHOUT_TIME_FORMAT)
                    .format(startDateWithTime) + " - "
                    + new SimpleDateFormat(DateWithoutTime.DATE_WITHOUT_TIME_FORMAT).format(endDateWithTime);
            assertEquals(expectedOutput, taskDateTime.toString());
        } catch (IllegalValueException ive) {

        }
    }
}

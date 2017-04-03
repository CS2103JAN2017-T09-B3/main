package seedu.address.model.task.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DateMaker;
import seedu.address.model.task.DateValue;
import seedu.address.model.task.DateWithTime;
import seedu.address.model.task.DateWithoutTime;

public class DateMakerTest {

    private final DateMaker dateMaker = new DateMaker();

    @Test
    public void testDateWithTime() {
        try {
            String inputDate = "23 Mar 4:15pm";
            dateMaker.makeDate(inputDate);
            DateValue outputDate = dateMaker.getDateValue();

            Date expectedDate = new Date();
            expectedDate.setDate(23);
            expectedDate.setMonth(2);
            expectedDate.setHours(16);
            expectedDate.setMinutes(15);
            String expectedOutput = new SimpleDateFormat(DateWithTime.DATE_WITH_TIME_FORMAT).format(expectedDate);

            assertEquals(expectedOutput, outputDate.getStringValue());
        } catch (IllegalValueException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testDateWithoutTime() {
        try {
            String inputDate = "7 Dec";
            dateMaker.makeDate(inputDate);
            DateValue outputDate = dateMaker.getDateValue();

            Date expectedDate = new Date();
            expectedDate.setDate(7);
            expectedDate.setMonth(11);
            String expectedOutput = new SimpleDateFormat(DateWithoutTime.DATE_WITHOUT_TIME_FORMAT).format(expectedDate);

            assertEquals(expectedOutput, outputDate.getStringValue());
        } catch (IllegalValueException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testTimeOnly() {
        try {
            String inputDate = "2:30pm";
            dateMaker.makeDate(inputDate);
            DateValue outputDate = dateMaker.getDateValue();

            Date expectedDate = new Date();
            expectedDate.setHours(14);
            expectedDate.setMinutes(30);
            String expectedOutput = new SimpleDateFormat("HH:mm").format(expectedDate);

            assertEquals(expectedOutput, outputDate.getTimeValue());
        } catch (IllegalValueException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testNullInput() {
        try {
            String inputDate = "  "; //only spaces
            dateMaker.makeDate(inputDate);
            DateValue outputDate = dateMaker.getDateValue();

            assertNull(outputDate);
        } catch (IllegalValueException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testInvalidInputValue() {
        final String[] invalidInputList = {"29.2.2017", "17:15pm", "31/6"};
        try {
            for (String inputDate : invalidInputList) {
                dateMaker.makeDate(inputDate);
            }
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), DateMaker.MESSAGE_DATE_TIME_CONSTRAINTS);
        }
    }
}

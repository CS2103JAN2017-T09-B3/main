


package seedu.address.testutil;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.DateWithTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withTitle("Alice Pauline")
                    .withContent("Alice Pauline content")
                    .withTaskDateTime("", "1/2/2013 9:00")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier")
                    .withContent("Benson Meier content")
                    .withTaskDateTime("2-3-2014 3am", "15/3/2014 10:00")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz")
                    .withTaskDateTime("3pm 1-2-2015", "")
                    .withStatus(true).build();
            daniel = new TaskBuilder().withTitle("Daniel Meier")
                    .withStatus(true).build();
            elle = new TaskBuilder().withTitle("Elle Meyer")
                    .withTaskDateTime("", getTodayTime()).build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz")
                    .withContent("Fiona Kunz content").build();
            //george = new TaskBuilder().withTitle("George Best").build();

            // Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier")
                    .withTaskDateTime("3pm", "5pm 27.12.17").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona};
    }

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }

    private String getTodayTime() {
        Date date = new Date();
        date.setHours(15);
        date.setMinutes(15);
        return new SimpleDateFormat(DateWithTime.DATE_WITH_TIME_FORMAT)
                .format(date);
    }
}

package seedu.myPotato.model.util;

import seedu.myPotato.commons.exceptions.IllegalValueException;
import seedu.myPotato.model.AddressBook;
import seedu.myPotato.model.ReadOnlyAddressBook;
import seedu.myPotato.model.tag.UniqueTagList;
import seedu.myPotato.model.task.Content;
import seedu.myPotato.model.task.Status;
import seedu.myPotato.model.task.Task;
import seedu.myPotato.model.task.TaskDateTime;
import seedu.myPotato.model.task.Title;
import seedu.myPotato.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("CS2101"),
                        new Content("Communication"),
                        new TaskDateTime("22/12/2017 13:20", "22/12/2017 14:20"),
                        new UniqueTagList("12MCs"),
                        new Status(false)),
                new Task(new Title("CS2103"),
                        new Content("Software Engineering"),
                        new TaskDateTime("", "12/12/2017 13:20"),
                        new UniqueTagList("12MCs"),
                        new Status(false)),
                new Task(new Title("CS2105"),
                        new Content("Networking"),
                        new TaskDateTime("22/07/2017 13:20", ""),
                        new UniqueTagList("4MCs"),
                        new Status(false)),
                new Task(new Title("CS2106"),
                        new Content("Operating System"),
                        new TaskDateTime("03/05/2017 13:20", "04/07/2017 15:00"),
                        new UniqueTagList("4MCs"),
                        new Status(false)),
                new Task(new Title("CS2107"),
                        new Content("Security"),
                        new TaskDateTime("2/2/2017 13:20", ""),
                        new UniqueTagList("4MCs"),
                        new Status(false)),
                new Task(new Title("CS2100"),
                        new Content("Computer Organization"),
                        new TaskDateTime("", "5/5/2017 13:20"),
                        new UniqueTagList("4MCs"),
                        new Status(false))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAB = new AddressBook();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}

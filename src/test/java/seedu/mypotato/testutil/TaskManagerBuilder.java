package seedu.mypotato.testutil;

import seedu.mypotato.commons.exceptions.IllegalValueException;
import seedu.mypotato.model.TaskManager;
import seedu.mypotato.model.tag.Tag;
import seedu.mypotato.model.task.Task;
import seedu.mypotato.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskManager addressBook;

    public TaskManagerBuilder(TaskManager addressBook) {
        this.addressBook = addressBook;
    }

    public TaskManagerBuilder withPerson(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        return this;
    }

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build() {
        return addressBook;
    }
}

package seedu.mypotato.testutil;

import seedu.mypotato.commons.exceptions.IllegalValueException;
import seedu.mypotato.model.tag.Tag;
import seedu.mypotato.model.tag.UniqueTagList;
import seedu.mypotato.model.task.Content;
import seedu.mypotato.model.task.Status;
import seedu.mypotato.model.task.TaskDateTime;
import seedu.mypotato.model.task.Title;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() throws IllegalValueException {
        this.task = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withTitle(String title) throws IllegalValueException {
        this.task.setTitle(new Title(title));
        return this;
    }

    public TaskBuilder withContent(String content) throws IllegalValueException {
        this.task.setContent(new Content(content));
        return this;
    }

    public TaskBuilder withTaskDateTime(String startDateTime, String endDateTime) throws IllegalValueException {
        this.task.setTaskDateTime(new TaskDateTime(startDateTime, endDateTime));
        return this;
    }

    public TaskBuilder withStartDateTime(String startDateTime) throws IllegalValueException {
        String endDateTime = this.task.getDateTime().getEndDateTimeString();
        this.task.setTaskDateTime(new TaskDateTime(startDateTime, endDateTime));
        return this;
    }

    public TaskBuilder withEndDateTime(String endDateTime) throws IllegalValueException {
        String startDateTime = this.task.getDateTime().getStartDateTimeString();
        this.task.setTaskDateTime(new TaskDateTime(startDateTime, endDateTime));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withStatus(boolean status) {
        this.task.setStatus(new Status(status));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}

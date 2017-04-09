package seedu.mypotato.testutil;

import static seedu.mypotato.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.mypotato.logic.parser.CliSyntax.PREFIX_DATE_TIME_END;
import static seedu.mypotato.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.mypotato.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.mypotato.commons.exceptions.IllegalValueException;
import seedu.mypotato.model.tag.UniqueTagList;
import seedu.mypotato.model.task.Content;
import seedu.mypotato.model.task.ReadOnlyTask;
import seedu.mypotato.model.task.Status;
import seedu.mypotato.model.task.TaskDateTime;
import seedu.mypotato.model.task.Title;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Content content;
    private TaskDateTime dateTime;
    private UniqueTagList tags;
    private Status status;

    public TestTask() throws IllegalValueException {
        content = new Content("");
        dateTime = new TaskDateTime("", "");
        tags = new UniqueTagList();
        status = new Status(false);
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.content = taskToCopy.getContent();
        this.dateTime = taskToCopy.getDateTime();
        this.tags = taskToCopy.getTags();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setTaskDateTime(TaskDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Content getContent() {
        return content;
    }

    @Override
    public TaskDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().fullTitle + " ");
        if (this.getContent().isThereContent()) {
            sb.append(PREFIX_CONTENT.toString() + this.getContent().fullContent + " ");
        }
        if (this.getDateTime().isThereStartDateTime()) {
            sb.append(PREFIX_DATE_TIME_START.toString()
                    + this.getDateTime().getStartDateTime().get().getStringValue() + " ");
        }
        if (this.getDateTime().isThereEndDateTime()) {
            sb.append(PREFIX_DATE_TIME_END.toString()
                    + this.getDateTime().getEndDateTime().get().getStringValue() + " ");
        }
        this.getTags().asObservableList().stream().forEach(s -> sb.append(PREFIX_TAG.toString() + s.tagName + " "));
        return sb.toString();
    }
    //@@Zhang Yan Hao A0135753A
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}


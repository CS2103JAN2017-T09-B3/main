package seedu.mypotato.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.mypotato.commons.exceptions.IllegalValueException;
import seedu.mypotato.model.tag.Tag;
import seedu.mypotato.model.tag.UniqueTagList;
import seedu.mypotato.model.task.Content;
import seedu.mypotato.model.task.ReadOnlyTask;
import seedu.mypotato.model.task.Status;
import seedu.mypotato.model.task.Task;
import seedu.mypotato.model.task.TaskDateTime;
import seedu.mypotato.model.task.Title;


/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement
    private String content;
    @XmlElement
    private String startDateTime;
    @XmlElement
    private String endDateTime;
    @XmlElement
    private boolean status;


    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        title = source.getTitle().fullTitle;
        content = source.getContent().fullContent;
        TaskDateTime dateTime = source.getDateTime();
        startDateTime = dateTime.getStartDateTime().isPresent()
                ? dateTime.getStartDateTime().get().getStringValue() : "";
        endDateTime = dateTime.getEndDateTime().isPresent()
                ? dateTime.getEndDateTime().get().getStringValue() : "";
        status = source.getStatus().getStatus();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Title title = new Title(this.title);
        final Content content = new Content(this.content);
        final TaskDateTime dateTime = new TaskDateTime(this.startDateTime, this.endDateTime);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final Status status = new Status(this.status);
        return new Task(title, content, dateTime, tags, status);
    }
}

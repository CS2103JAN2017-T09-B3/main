package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135807A
/**
 * Indicates a request to update the UI TaskDescription
 */
public class UpdateUiTaskDescriptionEvent extends BaseEvent {

    private final ReadOnlyTask task;

    public UpdateUiTaskDescriptionEvent(ReadOnlyTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getTask() {
        return task;
    }
}

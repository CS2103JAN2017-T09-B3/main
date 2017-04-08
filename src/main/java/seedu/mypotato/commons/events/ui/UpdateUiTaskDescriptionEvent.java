package seedu.mypotato.commons.events.ui;

import seedu.mypotato.commons.events.BaseEvent;
import seedu.mypotato.model.task.ReadOnlyTask;

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

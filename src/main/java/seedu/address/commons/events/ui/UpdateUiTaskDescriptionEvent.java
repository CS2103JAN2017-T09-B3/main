package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135807A
/**
 * Indicates a request to update the UI TaskDescription
 */
public class UpdateUiTaskDescriptionEvent extends BaseEvent {

    private final ReadOnlyTask editedTask;

    public UpdateUiTaskDescriptionEvent(ReadOnlyTask editedTask) {
        this.editedTask = editedTask;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getEditedTask() {
        return editedTask;
    }
}

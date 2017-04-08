package seedu.mypotato.commons.events.model;

import seedu.mypotato.commons.events.BaseEvent;
import seedu.mypotato.model.task.ReadOnlyTask;

//@@author A0135807A
/**
 * Send the first task information from the in-memory storage.
 */
public class ReadFirstTaskEvent extends BaseEvent {
    private ReadOnlyTask task;

    public ReadFirstTaskEvent(ReadOnlyTask task) {
        this.task = task;
    }

    public ReadOnlyTask getTask() {
        return this.task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

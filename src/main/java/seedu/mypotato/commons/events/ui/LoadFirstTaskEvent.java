package seedu.mypotato.commons.events.ui;

import seedu.mypotato.commons.events.BaseEvent;

//@@author A0135807A
/**
 * Indicates a request to read first task from in-memory storage
 */
public class LoadFirstTaskEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

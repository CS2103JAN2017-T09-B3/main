package seedu.mypotato.commons.events.storage;

import seedu.mypotato.commons.core.Config;
import seedu.mypotato.commons.events.BaseEvent;

//@@author A0135807A
/**
 * Indicate a request for change in storage location
 */
public class ChangedFileLocationRequestEvent extends BaseEvent {
    public final Config config;

    public ChangedFileLocationRequestEvent(Config config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

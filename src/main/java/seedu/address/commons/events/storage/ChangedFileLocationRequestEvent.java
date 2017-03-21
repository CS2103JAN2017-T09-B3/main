package seedu.address.commons.events.storage;

import seedu.address.commons.core.Config;
import seedu.address.commons.events.BaseEvent;

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

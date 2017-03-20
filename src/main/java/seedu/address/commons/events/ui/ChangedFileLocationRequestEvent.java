package seedu.address.commons.events.ui;

import seedu.address.commons.core.Config;
import seedu.address.commons.events.BaseEvent;

public class ChangedFileLocationRequestEvent extends BaseEvent{
    public final Config config;

    public ChangedFileLocationRequestEvent(Config config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
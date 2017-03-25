package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@author A0144895N
/**
 * Indicates a request to switch to another tab
 */
public class SwitchToTabRequestEvent extends BaseEvent {

    public final String targetTab;

    public SwitchToTabRequestEvent(String targetTab) {
        this.targetTab = targetTab;
    }

    public String getTargetTab() {
        return targetTab;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

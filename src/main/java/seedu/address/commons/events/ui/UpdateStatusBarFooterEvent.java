package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author A0135807A
/**
 * Indicates a request to update the StatusBarFooter
 */
public class UpdateStatusBarFooterEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

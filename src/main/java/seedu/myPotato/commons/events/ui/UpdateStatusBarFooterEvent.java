package seedu.myPotato.commons.events.ui;

import seedu.myPotato.commons.events.BaseEvent;

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

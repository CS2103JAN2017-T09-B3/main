//@@author A0135753A
package seedu.myPotato.model.task;

public class Status {
    public static final boolean MARKDONE = true;
    public static final boolean MARKUNDONE = false;
    public boolean status;
    public Status(boolean status) {
        this.status = status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return status;
    }
    public String toString() {
        return String.valueOf(status);
    }
}

//@@Zhang Yan Hao A0135753A
package seedu.address.model.task;

public class Status {
    public static final boolean MARK_DONE = true;
    public static final boolean MARK_UNDONE = false;
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

package tasks;

public class Deadline extends Task {
    private final String deadlineDateTime;

    /**
     * The constructor.
     *
     * @param description The description of this task.
     * @param deadlineDateTime The deadline date/time of this task.
     */
    public Deadline(String description, String deadlineDateTime) {
        super(description);
        this.deadlineDateTime = deadlineDateTime;
    }

    public String getTaskDataString() {
        return toRunTimeClassString() + "|" + getTaskStatusString() + "|" + super.description + "|" + deadlineDateTime;
    }

    public String toString() {
        return "[D]" + super.toString() + "(by:" + deadlineDateTime + ")";
    }

}

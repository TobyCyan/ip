package tasks;

import java.time.LocalDateTime;


public class Deadline extends TimedTask {
    // private final String deadlineDateTime;
    private final LocalDateTime deadlineDateTime;

    /**
     * The constructor.
     *
     * @param description The description of this task.
     * @param deadlineDateTime The deadline date/time of this task.
     */
    public Deadline(String description, String deadlineDateTime) {
        super(description);
        this.deadlineDateTime = convertDateTimeFormat(deadlineDateTime);
    }

    public String getTaskDataString() {
        return toRunTimeClassString() + "|" + getTaskStatusString() + "|" + super.description + "|" + toFormattedDateTimeInputString(deadlineDateTime);
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + toFormattedDateTimeOutputString(deadlineDateTime) + ")";
    }

}

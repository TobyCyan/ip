package tasks;

import java.time.LocalDateTime;

public class Event extends TimedTask {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    /**
     * The constructor.
     *
     * @param description The description of this task.
     * @param startDateTime The starting date/time of this task.
     * @param endDateTime The ending date/time of this task.
     */
    public Event(String description, String startDateTime, String endDateTime) {
        super(description);
        this.startDateTime = convertDateTimeFormat(startDateTime);
        this.endDateTime = convertDateTimeFormat(endDateTime);
    }

    public String getTaskDataString() {
        return toRunTimeClassString() + "|" + getTaskStatusString() + "|" + super.description + "|" + toFormattedDateTimeInputString(startDateTime) + "|" + toFormattedDateTimeInputString(endDateTime);
    }

    public String toString() {
        return "[E]" + super.toString() + " (from: " + toFormattedDateTimeOutputString(startDateTime) + " to: " + toFormattedDateTimeOutputString(endDateTime) + ")";
    }

}

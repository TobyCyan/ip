package tasks;

public class Event extends Task {
    private final String startDateTime;
    private final String endDateTime;

    /**
     * The constructor.
     *
     * @param description The description of this task.
     * @param startDateTime The starting date/time of this task.
     * @param endDateTime The ending date/time of this task.
     */
    public Event(String description, String startDateTime, String endDateTime) {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getTaskDataString() {
        return getClass() + "|" + getTaskStatusString() + "|" + super.description + "|" + startDateTime + "|" + endDateTime;
    }

    public String toString() {
        return "[E]" + super.toString() + "(from:" + startDateTime + "to:" + endDateTime + ")";
    }

}

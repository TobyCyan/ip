package tasks;

import java.time.LocalDateTime;

/**
 * Represents the deadline task.
 * Consists of a description and a deadline date/time for the task.
 * This class contains methods to easily represent this task as a string
 * for both displaying to the user and writing to the task data save file.
 */
public class Deadline extends TimedTask {
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

    /**
     * Represents the current task in a format to be written to the task data save file.
     * This representation helps the file reader to interpret this task when loading from the save file.
     *
     * @return The string representation for writing to the save file.
     */
    public String getTaskDataString() {
        return toRunTimeClassString() + "|" + getTaskStatusString() + "|" + super.description + "|" + toFormattedDateTimeInputString(deadlineDateTime);
    }

    /**
     * Represents the current task in a format to be displayed to the user.
     *
     * @return The string representation for displaying to the user.
     */
    public String toString() {
        return "[D]" + super.toString() + " (by: " + toFormattedDateTimeOutputString(deadlineDateTime) + ")";
    }

}

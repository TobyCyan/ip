package mei.task;

import java.time.LocalDateTime;

import mei.exception.DateTimeConversionException;
import mei.exception.DatesNotInOrderException;

/**
 * Represents the event task.
 * Consists of the description, the start and end date/time for this task.
 * This class contains methods to easily represent this task as a string
 * for both displaying to the user and writing to the task data save file.
 */
public class Event extends TimedTask {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    /**
     * Initializes the task description
     * and start and end date/time by converting them into their LocalDateTime objects.
     *
     * @param description The description of this task.
     * @param startDateTime The starting date/time of this task.
     * @param endDateTime The ending date/time of this task.
     * @param addTaskCommand The command used to add this task.
     * @throws DateTimeConversionException if the input deadlineDateTime doesn't match any of the valid formats.
     */
    public Event(String description, String startDateTime, String endDateTime, String addTaskCommand)
            throws DateTimeConversionException, DatesNotInOrderException {
        super(description, addTaskCommand);
        this.startDateTime = convertDateTimeFormat(startDateTime);
        this.endDateTime = convertDateTimeFormat(endDateTime);

        if (!isInOrder(this.startDateTime, this.endDateTime)) {
            throw new DatesNotInOrderException();
        }
    }

    /**
     * Represents the current task in a format to be written to the task data save file.
     * This representation helps the file reader to interpret this task when loading from the save file.
     *
     * @return The string representation for writing to the save file.
     */
    public String getTaskDataString() {
        return toRunTimeClassString()
                + "|" + getTaskStatusString()
                + "|" + super.description
                + "|" + toFormattedDateTimeInputString(startDateTime)
                + "|" + toFormattedDateTimeInputString(endDateTime)
                + "|" + super.addTaskCommand;
    }

    /**
     * Represents the current task in a format to be displayed to the user.
     *
     * @return The string representation for displaying to the user.
     */
    public String toString() {
        return "[E]" + super.toString() + " (from: " + toFormattedDateTimeOutputString(startDateTime)
                + " to: " + toFormattedDateTimeOutputString(endDateTime) + ")";
    }

}

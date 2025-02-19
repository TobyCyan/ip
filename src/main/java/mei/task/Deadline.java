package mei.task;

import java.time.LocalDateTime;

import mei.exception.DateTimeConversionException;


/**
 * Represents the deadline task.
 * Consists of a description and a deadline date/time for the task.
 * This class contains methods to easily represent this task as a string
 * for both displaying to the user and writing to the task data save file.
 */
public class Deadline extends TimedTask {
    private final LocalDateTime deadlineDateTime;

    /**
     * Initializes the date/time field by converting the given date/time string into a LocalDateTime object.
     *
     * @param description The description of this task.
     * @param deadlineDateTime The deadline date/time of this task.
     * @param addTaskCommand The command used to add this task.
     * @throws DateTimeConversionException if the input deadlineDateTime doesn't match any of the valid formats.
     */
    public Deadline(String description, String deadlineDateTime, String addTaskCommand)
            throws DateTimeConversionException {
        super(description, addTaskCommand);
        this.deadlineDateTime = convertDateTimeFormat(deadlineDateTime);
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
                + "|" + toFormattedDateTimeInputString(deadlineDateTime)
                + "|" + super.addTaskCommand;
    }

    /**
     * Represents the current task as the command used when it was first added.
     *
     * @return The add task command string.
     */
    public String toAddTaskCommandString() {
        return addTaskCommand;
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

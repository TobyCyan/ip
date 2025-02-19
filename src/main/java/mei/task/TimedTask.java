package mei.task;

import mei.exception.DateTimeConversionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the base class for timed tasks.
 * Consists of the formatters for input and output date/time objects.
 * This class contains methods to convert a date/time string into LocalDateTime objects
 * or LocalDateTime objects of different formats into another format.
 */
public class TimedTask extends Task {
    /** The possible formats that the user could input their datetime. **/
    private static final String[] INPUT_FORMATS = new String[] {
        "d/MM/yyyy HHmm",
        "d-MM-yyyy HHmm",
        "yyyy/MM/d HHmm",
        "yyyy-MM-d HHmm"
    };

    /** The formatters for parsing any local date time data in a timed task. **/
    private static final List<DateTimeFormatter> INPUT_FORMATTERS = new ArrayList<>();

    /** The formatter for formatting any local date time data into the desired format. **/
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMMM d yyyy 'at' h:mm a");

    /**
     * Initializes the task description and the list of formatters to convert the input date/time string
     * into its LocalDateTime object.
     *
     * @param description The description of this task.
     * @param addTaskCommand The command used to add this task.
     */
    public TimedTask(String description, String addTaskCommand) {
        super(description, addTaskCommand);
        for (String format : INPUT_FORMATS) {
            INPUT_FORMATTERS.add(DateTimeFormatter.ofPattern(format));
        }
    }

    /**
     * Converts the given datetime string into a LocalDateTime object.
     * The string should be given in the format specified to the formatters in INPUT_FORMATTERS.
     * The string is trimmed to remove leading and trailing spaces, then parsed into LocalDateTime object.
     * <p>
     * Tries to loop through all the available formatters
     * and return when found one that matches the format of the input.
     * If none of the formats match, returns null for error handling.
     *
     * @param dateTime The datetime string to be parsed into a LocalDateTime object.
     * @return The LocalDateTime object of the given datetime string.
     * @throws DateTimeConversionException If the input dateTime doesn't match any of the formats.
     */
    public static LocalDateTime convertDateTimeFormat(String dateTime) throws DateTimeConversionException {
        for (DateTimeFormatter formatter : INPUT_FORMATTERS) {
            try {
                return LocalDateTime.parse(dateTime.trim(), formatter);
            } catch (DateTimeParseException ignore) {
                // This can be safely ignored since we want to check whether the date/time input matches
                // any of the formatters and the parse method could throw an exception.
            }
        }

        // Input is not in a valid format.
        throw new DateTimeConversionException();
    }

    /**
     * Checks whether the start and end dates are in order,
     * which means the start date cannot be after the end date.
     *
     * @param startDate The start date.
     * @param endDate The end date.
     * @return true or false depending on whether start and end dates are in order.
     */
    public static boolean isInOrder(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isBefore(endDate);
    }

    /**
     * Formats the datetime object into a string that can be read/ write in the output format.
     * Mainly used for displaying the task's datetime in a more readable format.
     *
     * @param dateTime The datetime object to format.
     * @return A string of the datetime in the output format.
     */
    public String toFormattedDateTimeOutputString(LocalDateTime dateTime) {
        assert dateTime != null : "date/time object cannot be null";

        return dateTime.getDayOfWeek() + " " + dateTime.format(OUTPUT_FORMATTER);
    }

    /**
     * Formats the datetime object into a string that can be read/ write in the input format.
     * Mainly used for writing the task's datetime to the .txt file where task data are stored
     * so that the datetime can be retrieved and interpreted in their original format.
     * <p>
     * To ensure a constant run time, this function shall format the input datetime object
     * into the first format in INPUT_FORMATTERS.
     * That way we can always convert the string back to its LocalDateTime form
     * in the first loop of the conversion function.
     *
     * @param dateTime The datetime object to format.
     * @return A string of the datetime in the input format.
     */
    public String toFormattedDateTimeInputString(LocalDateTime dateTime) {
        assert dateTime != null : "date/time object cannot be null";

        return dateTime.format(INPUT_FORMATTERS.get(0));
    }

}

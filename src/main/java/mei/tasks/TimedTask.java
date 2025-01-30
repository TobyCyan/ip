package mei.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TimedTask extends Task {
    /** The possible formats that the user could input their datetime. **/
    private static final String[] INPUT_FORMATS = new String[] {"d/MM/yyyy HHmm", "d-MM-yyyy HHmm", "yyyy/MM/d HHmm", "yyyy-MM-d HHmm"};

    /** The formatters for parsing any local date time data in a timed task. **/
    private static final List<DateTimeFormatter> INPUT_FORMATTERS = new ArrayList<>();

    /** The formatter for formatting any local date time data into the desired format. **/
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMMM d yyyy 'at' h:mm a");

    /**
     * The constructor.
     *
     * @param description The description of this task.
     */
    public TimedTask(String description) {
        super(description);
        for (String format : INPUT_FORMATS) {
            INPUT_FORMATTERS.add(DateTimeFormatter.ofPattern(format));
        }
    }

    /**
     * Converts the given datetime string into a LocalDateTime object.
     * The string should be given in the format specified to the formatters in INPUT_FORMATTERS.
     * The string is trimmed to remove leading and trailing spaces, then parsed into LocalDateTime object.
     * Tries to loop through all the available formatters and return when found one that matches the format of the input.
     * If none of the formats match, returns null for error handling.
     *
     * @param dateTime The datetime string to be parsed into a LocalDateTime object.
     * @return The LocalDateTime object of the given datetime string.
     */
    public static LocalDateTime convertDateTimeFormat(String dateTime) {
        for (DateTimeFormatter formatter : INPUT_FORMATTERS) {
            try {
                return LocalDateTime.parse(dateTime.trim(), formatter);
            } catch (DateTimeParseException ignore) {

            }
        }
        return null;
    }

    /**
     * Formats the datetime object into a string that can be read/ write in the output format.
     * Mainly used for displaying the task's datetime in a more readable format.
     *
     * @param dateTime The datetime object to format.
     * @return A string of the datetime in the output format.
     */
    public String toFormattedDateTimeOutputString(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek() + " " + dateTime.format(OUTPUT_FORMATTER);
    }

    /**
     * Formats the datetime object into a string that can be read/ write in the input format.
     * Mainly used for writing the task's datetime to the .txt file where task data are stored
     * so that the datetime can be retrieved and interpreted in their original format.
     * <p>
     * To ensure a constant run time, this function shall format the input datetime object into the first format in INPUT_FORMATTERS.
     * That way we can always convert the string back to its LocalDateTime form in the first loop of the conversion function.
     *
     * @param dateTime The datetime object to format.
     * @return A string of the datetime in the input format.
     */
    public String toFormattedDateTimeInputString(LocalDateTime dateTime) {
        return dateTime.format(INPUT_FORMATTERS.get(0));
    }

}

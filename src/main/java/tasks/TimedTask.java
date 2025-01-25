package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimedTask extends Task {
    /** The formatter for parsing any local date time data in a timed task. **/
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy HHmm");

    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMMM d 'at' h:mm a");

    /**
     * The constructor.
     *
     * @param description The description of this task.
     */
    public TimedTask(String description) {
        super(description);
    }

    public static LocalDateTime convertDateTimeFormat(String dateTime) {
        return LocalDateTime.parse(dateTime.trim(), INPUT_FORMATTER);
    }

    public String toFormattedDateTimeOutputString(LocalDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMATTER);
    }

    public String toFormattedDateTimeInputString(LocalDateTime dateTime) {
        return dateTime.format(INPUT_FORMATTER);
    }

}

package mei.exception;

/**
 * Represents the Mei exception that is thrown when a timed task given
 * does not provide a valid date/time format.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 */
public class DateTimeConversionException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Oh, forgot to tell you, I would prefer the dates and timings given in this format.",
        "day/month/year HoursMinutes, you could swap the / for - too!",
        "And feel free to swap the placements of the day and year.",
        "For example, 25/02/2025 1600, 2026-01-16 0800 are both valid date/times!"
    };

    public DateTimeConversionException() {
        super(ERROR_RESPONSES);
    }
}

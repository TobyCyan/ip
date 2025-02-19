package mei.exception;

/**
 * Represents the Mei exception that is thrown when the starting and ending dates of a timed task (e.g. Event)
 * are not in order. (i.e. The starting date is after the ending date.)
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 */
public class DatesNotInOrderException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Oh no! I think your dates are messed up!",
        "The start date can't be after the end date, silly!"
    };

    public DatesNotInOrderException() {
        super(ERROR_RESPONSES);
    }
}

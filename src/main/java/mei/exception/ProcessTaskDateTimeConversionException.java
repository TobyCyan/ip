package mei.exception;

/**
 * Represents the Mei exception that is thrown when there is a date/time conversion error while processing a task.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a process task-related exception.
 */
public class ProcessTaskDateTimeConversionException extends ProcessTaskException {
    private static final String[] ERROR_RESPONSES = new String[] {
            "Uh oh, there seems to be an invalid date/time in one of your tasks.",
            "I cannot process this task, so feel free to re-add it!"
    };

    public ProcessTaskDateTimeConversionException() {
        super(ERROR_RESPONSES);
    }
}
package mei.exception;

/**
 * Represents the Mei exception that is thrown when the new task given does not have a description.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 */
public class EmptyTaskDescriptionException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Remember to add a description to your tasks, okay?"
    };

    public EmptyTaskDescriptionException() {
        super(ERROR_RESPONSES);
    }

}

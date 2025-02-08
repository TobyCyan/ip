package mei.exception;

/**
 * Represents the Mei exception that is thrown when the user tries to access a task index that is invalid.
 * The task index is invalid if it falls outside the bounds (i.e. not an index held by any existing task.).
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 * The error response is given based on the string array retrieved from the RESPONSE_MAP in the Response Manager
 * with the key of the same name excluding the exception word at the end.
 */
public class TaskIndexOutOfBoundsException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Hmm..? This task number doesn't seem to be"
            + "on the list...", "Can you repeat with a valid one? :3"
    };

    public TaskIndexOutOfBoundsException() {
        super(ERROR_RESPONSES);
    }

}

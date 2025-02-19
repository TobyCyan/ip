package mei.exception;

/**
 * Represents the Mei exception that is thrown when the user tries to access a task index that is invalid.
 * The task index is invalid if it falls outside the bounds (i.e. not an index held by any existing task.).
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 */
public class TaskIndexOutOfBoundsException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Hmm..? This task number doesn't seem to be"
            + " on the list...", "Can you repeat with a valid one? :3",
        "Hint: Use numbers that appear on the list!"
    };

    public TaskIndexOutOfBoundsException() {
        super(ERROR_RESPONSES);
    }

}

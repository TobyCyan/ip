package mei.exception;

/**
 * Represents the Mei exception that is thrown when the deadline task given does not provide enough information to be interpreted.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 */
public class DeadlineNotEnoughInfoException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Hmm? I think you missed some information there...",
        "I would need to know the deadline so... do use /by to indicate it!"
    };

    public DeadlineNotEnoughInfoException() {
        super(ERROR_RESPONSES);
    }

}

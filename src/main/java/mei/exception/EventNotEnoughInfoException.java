package mei.exception;

/**
 * Represents the Mei exception that is thrown when the event task given does not provide enough information to be interpreted.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 * The error response is given based on the string array retrieved from the RESPONSE_MAP in the Response Manager
 * with the key of the same name excluding the exception word at the end.
 */
public class EventNotEnoughInfoException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Hmm? I think you missed some information there...",
        "I would need to know the start and end date/times so... do use /from and /to to indicate them!"
    };

    public EventNotEnoughInfoException() {
        super(ERROR_RESPONSES);
    }

}

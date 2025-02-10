package mei.exception;

/**
 * Represents the Mei exception that is thrown when the most recent reversed input is empty.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as an input-related exception.
 */
public class EmptyMostRecentReversedInputException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "It seems that there are nothing to undo!"
    };

    public EmptyMostRecentReversedInputException() {
        super(ERROR_RESPONSES);
    }
}

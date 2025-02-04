package mei.exception;

/**
 * Represents the Mei exception that is thrown when the new task given does not have a description.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 * The error response is given based on the string array retrieved from the RESPONSE_MAP in the Response Manager
 * with the key of the same name excluding the exception word at the end.
 */
public class EmptyTaskDescriptionException extends MeiException {

    public EmptyTaskDescriptionException(String[] errorResponses) {
        super(errorResponses);
    }

}

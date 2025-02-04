package mei.exception;

/**
 * Represents the Mei exception that is thrown when the user tries to add a new task with a type that is invalid.
 * The task type is invalid if it does not exist in the TASK_TYPES list defined within the Task Manager class.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 * The error response is given based on the string array retrieved from the RESPONSE_MAP in the Response Manager
 * with the key of the same name excluding the exception word at the end.
 */
public class UnknownTaskTypeException extends MeiException {

    public UnknownTaskTypeException(String[] errorResponses) {
        super(errorResponses);
    }

}

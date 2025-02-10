package mei.exception;

/**
 * Represents the Mei exception that is thrown when the user tries to add a new task with a type that is invalid.
 * The task type is invalid if it does not exist in the TASK_TYPES list defined within the Task Manager class.
 * echoErrorResponse should be called when this exception is caught.
 * This is classified as a task-related exception.
 */
public class UnknownTaskTypeException extends MeiException {
    private static final String[] ERROR_RESPONSES = new String[] {
        "Oops! I think you may have entered an unknown task type!"
            + "Please try again!", "The accepted tasks are todo, deadline, and event :))"
    };

    public UnknownTaskTypeException() {
        super(ERROR_RESPONSES);
    }

}

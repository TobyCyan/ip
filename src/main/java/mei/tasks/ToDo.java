package mei.tasks;

/**
 * Represents the todo task.
 * Consists of the description for this task.
 * This class contains methods to easily represent this task as a string.
 */
public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    /**
     * Represents the current task in a format to be displayed to the user.
     *
     * @return The string representation for displaying to the user.
     */
    public String toString() {
        return "[T]" + super.toString();
    }

}

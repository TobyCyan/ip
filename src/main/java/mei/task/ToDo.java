package mei.task;

/**
 * Represents the todo task.
 * Consists of the description for this task.
 * This class contains methods to easily represent this task as a string.
 */
public class ToDo extends Task {

    /**
     * Initializes a Todo task.
     * The task description is trimmed.
     * So that display and save formats will ignore leading and trailing spaces.
     *
     * @param description The description of this task.
     * @param addTaskCommand The command used to add this task.
     */
    public ToDo(String description, String addTaskCommand) {
        super(description, addTaskCommand);
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

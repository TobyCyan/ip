package tasks;

public class ToDo extends Task {

    /**
     * The constructor.
     *
     * @param description The description of this task.
     */
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

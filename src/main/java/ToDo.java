public class ToDo extends Task {

    /**
     * The constructor.
     *
     * @param description The description of this task.
     */
    public ToDo(String description) {
        super(description);
    }

    public String toString() {
        return "[T]" + super.toString();
    }

}

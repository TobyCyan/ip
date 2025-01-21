public class Task {
    private final String description;
    private boolean isTaskDone;

    /**
     * The constructor.
     * @param description The description of this task.
     */
    public Task(String description) {
        this.description = description;
        this.isTaskDone = false;
    }

    /**
     * Gets this task's status string.
     * @return The status string that is already wrapped in the desired format.
     */
    public String getTaskStatusString() {
        return isTaskDone ? "[X]" : "[ ]";
    }

    /**
     * Marks the status of this task as completed.
     */
    public void completeTask() {
        isTaskDone = true;
    }

    /**
     * Marks the status of this task as incomplete.
     */
    public void uncheckTask() {
        isTaskDone = false;
    }

    public String toString() {
        return getTaskStatusString() + " " + description;
    }
}

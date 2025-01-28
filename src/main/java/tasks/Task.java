package tasks;

public class Task {
    protected final String description;
    private boolean isTaskDone;

    /**
     * The constructor.
     * The task description is trimmed.
     * So that display and save formats will ignore leading and trailing spaces.
     *
     * @param description The description of this task.
     */
    public Task(String description) {
        this.description = description.trim();
        this.isTaskDone = false;
    }

    /**
     * Gets this task's status string.
     *
     * @return The status string that is already wrapped in the desired format.
     */
    public String getTaskStatusString() {
        return isTaskDone ? "[X]" : "[ ]";
    }

    /**
     * Represents the current task in a format to be written to the task data save file.
     * This representation helps the file reader to interpret this task when loading from the save file.
     *
     * @return The string representation for writing to the save file.
     */
    public String getTaskDataString() {
        return toRunTimeClassString() + "|" + getTaskStatusString() + "|" + description;
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

    /**
     * Returns the run-time class of this instance as a string.
     *
     * @return The run-time class of this instance as a string.
     */
    public String toRunTimeClassString() {
        return getClass().getSimpleName();
    }

    /**
     * Represents the current task in a format to be displayed to the user.
     * However, since this is the base class of all tasks, this method will not be directly called by a manager.
     *
     * @return The string representation for displaying to the user.
     */
    public String toString() {
        return getTaskStatusString() + " " + description;
    }
}

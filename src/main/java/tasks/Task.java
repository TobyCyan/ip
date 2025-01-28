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
     * Checks if this task's description contains the given keyword.
     * This is used for finding tasks using a keyword based on their descriptions.
     *
     * @param keyword The keyword used to check for a match.
     * @return true or false depending on whether the description consists of the given keyword.
     */
    public boolean isDescriptionContainsKeyword(String keyword) {
        return description.contains(keyword);
    }

    public String toRunTimeClassString() {
        return getClass().getSimpleName();
    }

    public String toString() {
        return getTaskStatusString() + " " + description;
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>(100);
    private static int index = 0;
    private static TaskManager instance = null;
    /** Regex to use for splitting the given user task input. */
    private final String taskStringSplitRegex = "(/from|/by|/to)";

    /**
     * The constructor.
     * Made private to prevent direct initialization.
     * Should call getInstance instead.
     */
    private TaskManager() {

    }

    /**
     * @@author TobyCyan-reused.
     * Reused from geeksforgeeks.org/singleton-class-java
     * with minor modifications.
     * Function to get the singleton instance of this class.
     * @return The single instance of Task Manager.
     */
    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    /**
     * Processes new tasks before returning them back to the response manager to prompt the user.
     * @param taskType The type of the task.
     * @param taskDescription The description of the task.
     * @return The processed task itself.
     */
    public Task processTask(String taskType, String taskDescription) {
        Task newTask = null;
        String[] taskDescriptionSplit = taskDescription.split(taskStringSplitRegex, 3);
        String description = taskDescriptionSplit[0];

        switch (taskType) {
        case "todo":
            newTask = new ToDo(description);
            break;

        case "deadline":
            String deadlineDateTime = taskDescriptionSplit[1];
            newTask = new Deadline(description, deadlineDateTime);
            break;

        case "event":
            String startDateTime = taskDescriptionSplit[1];
            String endDateTime = taskDescriptionSplit[2];
            newTask = new Event(description, startDateTime, endDateTime);
            break;

        }

        if (newTask == null) {
            return newTask;
        }

        addTask(newTask);
        return newTask;
    }

    /**
     * Adds the given task to the list of tasks.
     * Sets the status of the new task as not done (false).
     * Returns true if success.
     * TODO: To add an exception in case the task adding fails.
     * @param taskDescription The description of the task to be added.
     * @return true or false.
     */
    public boolean addTask(Task task) {
        tasks.add(task);
        index++;
        return true;
    }

    /**
     * Marks the given task as completed.
     * TODO: Should add error handling for invalid taskIndex (out of bounds index).
     * TODO: (Optional) Add a response when user tries to mark a task that is already marked.
     * @param taskIndex The index of the task to be marked as completed.
     * @return The completed task itself.
     */
    public Task markTask(int taskIndex) {
        Task taskToBeMarked = tasks.get(taskIndex - 1);
        taskToBeMarked.completeTask();
        return taskToBeMarked;
    }

    /**
     * Marks the given task as incomplete.
     * TODO: Should add error handling for invalid taskIndex (out of bounds index).
     * TODO: (Optional) Add a response when user tries to unmark a task that isn't marked.
     * @param taskIndex The index of the task to be marked as incomplete.
     * @return The unmarked task itself.
     */
    public Task unmarkTask(int taskIndex) {
        Task taskToBeUnmarked = tasks.get(taskIndex - 1);
        taskToBeUnmarked.uncheckTask();
        return taskToBeUnmarked;
    }

    /**
     * Formats the list of tasks as a nicely organized indexed list.
     * @return The list of valid tasks to be sent to the response manager to display.
     */
    public String[] getTaskStringsToDisplay() {
        // First task doesn't exist, can assume no task.
        // Prompt response manager to reply with no task.
        if (tasks.isEmpty()) {
            return null;
        }

        // Do some processing here before sending them back to Response Manager to print out the tasks.
        AtomicInteger i = new AtomicInteger();

        return Arrays.stream(tasks.toArray())
                .filter(Objects::nonNull)
                .map(task -> getTaskDisplayString(i.getAndIncrement() + 1, (Task) task))
                .toArray(String[]::new);
    }

    /**
     * Converts the task string into the desired format to be displayed to the user.
     * @param index The current task index.
     * @param task The task to be displayed.
     * @return A string of the desired task display format.
     */
    private String getTaskDisplayString(int index, Task task) {
        return index + ". " + task.toString();
    }

    /**
     * Gets the total number of tasks the user currently has.
     * @return The number of tasks.
     */
    public int getTotalTasks() {
        return index;
    }

}

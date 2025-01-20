import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManager {
    private final String[] tasks = new String[100];
    private static int index = 0;
    private static TaskManager instance = null;
    private static ResponseManager responseManager = null;

    /**
     * Constructor.
     * Made private to prevent direct initialization.
     * Should call getInstance instead.
     */
    private TaskManager() {

    }

    /**
     * Code adapted from geeksforgeeks.org/singleton-class-java
     * Function to get the singleton instance of this class.
     * @return The single instance of Task Manager.
     */
    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
            responseManager = ResponseManager.getInstance();
        }
        return instance;
    }

    /**
     * Adds the given task to the list of tasks.
     * Returns true if success.
     * TODO: To add an exception in case the task adding fails.
     * @param task The task to be added.
     * @return true or false.
     */
    public boolean addTask(String task) {
        tasks[index] = task;
        index++;
        return true;
    }

    /**
     * Displays the list of tasks as a nicely organized indexed list.
     * The list of tasks is processed before being sent to the response manager to echo the tasks.
     */
    public void displayTasks() {
        // First task doesn't exist, can assume no task.
        // Prompt response manager to reply with no task.
        if (tasks[0] == null) {
            responseManager.noTaskResponse();
            return;
        }

        // Do some processing here before sending them back to Response Manager to print out the tasks.
        AtomicInteger i = new AtomicInteger();
        String[] validTasks = Arrays.stream(tasks)
                .filter(Objects::nonNull)
                .map(task -> getTaskDisplayString(i.getAndIncrement() + 1, task))
                .toArray(String[]::new);

        responseManager.echoLines(validTasks);
    }

    /**
     * Converts the task string into the desired format to be displayed to the user.
     * @param index The current task index.
     * @param task The task to be displayed.
     * @return A string of the desired task display format.
     */
    private String getTaskDisplayString(int index, String task) {
        return index + ". " + task;
    }

    /**
     * Gets the total number of tasks the user currently has.
     * @return The number of tasks.
     */
    public int getTotalTasks() {
        return index;
    }
}

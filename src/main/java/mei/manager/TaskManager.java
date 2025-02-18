package mei.manager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import mei.exception.DateTimeConversionException;
import mei.exception.DatesNotInOrderException;
import mei.exception.DeadlineNotEnoughInfoException;
import mei.exception.EventNotEnoughInfoException;
import mei.exception.MeiException;
import mei.fileaccess.FileStorage;
import mei.task.Deadline;
import mei.task.Event;
import mei.task.Task;
import mei.task.ToDo;

/**
 * Represents the manager class that supports all task-related functionalities.
 * This class contains methods to add, delete, mark, unmark tasks.
 * A set of task types is also maintained so that this manager knows what task types are valid.
 * Hence, it is important to update this list when new tasks get added.
 */
public class TaskManager {
    /** The set of task types that are valid, be sure to update this when new task types are added. **/
    private static final HashSet<String> TASK_TYPES = new HashSet<>();
    private static Task mostRecentDeletedTask;

    private final List<Task> tasks;
    private final FileStorage fileStorage;

    /**
     * Initializes the valid task types and adds them to the set of task types.
     * Also loads the list of tasks and file storage instance to the fields.
     *
     * @param tasks The list of user tasks.
     * @param fileStorage The file storage instance.
     */
    public TaskManager(List<Task> tasks, FileStorage fileStorage) {
        // Task types.
        // Make sure to add the relevant task types when adding a new one.
        TASK_TYPES.add("todo");
        TASK_TYPES.add("deadline");
        TASK_TYPES.add("event");

        this.tasks = tasks;
        this.fileStorage = fileStorage;
    }

    /**
     * Processes new added tasks before returning them to the response manager to prompt the user.
     * Assumes that there are only 3 types of tasks to be considered: todo, deadline and event.
     *
     * @param addTaskCommand The original command used when the task to process is added.
     * @param taskType The type of the task.
     * @param taskDescriptionAsCommand The description of the task as a command.
     * @return The processed task itself, or null if the task type does not match any of the valid types.
     */
    public Task processAddTask(String addTaskCommand, String taskType, String taskDescriptionAsCommand)
            throws MeiException {
        switch (taskType) {
        case "todo":
            return addTodoTaskAndReturn(addTaskCommand, taskDescriptionAsCommand);

        case "deadline":
            return addDeadlineTaskAndReturn(addTaskCommand, taskDescriptionAsCommand);

        case "event":
            return addEventTaskAndReturn(addTaskCommand, taskDescriptionAsCommand);

        default:
            return null;
        }
    }

    private Task addTodoTaskAndReturn(String addTaskCommand, String description) {

        ToDo newTask = new ToDo(description, addTaskCommand);
        addTask(newTask);
        return newTask;
    }

    private Task addDeadlineTaskAndReturn(String addTaskCommand, String taskDescriptionAsCommand)
            throws DeadlineNotEnoughInfoException, DateTimeConversionException {
        String taskStringSplitRegex = "/by";
        String[] taskDescriptionSplit = taskDescriptionAsCommand.split(taskStringSplitRegex, 2);

        if (taskDescriptionSplit.length < 2) {
            throw new DeadlineNotEnoughInfoException();
        }

        String description = taskDescriptionSplit[0];
        String deadlineDateTime = taskDescriptionSplit[1];

        // A date/time conversion exception may be thrown here.
        Deadline newTask = new Deadline(description, deadlineDateTime, addTaskCommand);
        addTask(newTask);

        return newTask;
    }

    private Task addEventTaskAndReturn(String addTaskCommand, String taskDescriptionAsCommand)
            throws EventNotEnoughInfoException, DateTimeConversionException, DatesNotInOrderException {
        String taskStringSplitRegex = "(/from|/to)";
        String[] taskDescriptionSplit = taskDescriptionAsCommand.split(taskStringSplitRegex, 3);

        if (taskDescriptionSplit.length < 3) {
            throw new EventNotEnoughInfoException();
        }

        String description = taskDescriptionSplit[0];
        String startDateTime = taskDescriptionSplit[1];
        String endDateTime = taskDescriptionSplit[2];

        // A date/time conversion exception may be thrown here.
        Event newTask = new Event(description, startDateTime, endDateTime, addTaskCommand);
        addTask(newTask);

        return newTask;
    }

    /**
     * Adds the given task to the list of tasks.
     * Sets the status of the new task as not done (false).
     *
     * @param task The new task to be added.
     */
    public void addTask(Task task) {
        assert task != null : "task added should never be null";
        tasks.add(task);
        fileStorage.writeTask(task);
    }

    /**
     * Marks the given task as completed.
     *
     * @param taskIndex The index of the task to be marked as completed.
     * @return The completed task itself.
     */
    public Task markTask(int taskIndex) {
        assert isTaskIndexValid(taskIndex) : "task index to be marked should be valid";

        Task taskToBeMarked = tasks.get(taskIndex - 1);
        taskToBeMarked.completeTask();

        fileStorage.overwriteTask(taskIndex, taskToBeMarked.getTaskDataString());

        return taskToBeMarked;
    }

    /**
     * Marks the given task as incomplete.
     * Tells the file storage class to carry out the process.
     *
     * @param taskIndex The index of the task to be marked as incomplete.
     * @return The unmarked task itself to be prompted to the user.
     */
    public Task unmarkTask(int taskIndex) {
        assert isTaskIndexValid(taskIndex) : "task index to be unmarked should be valid";

        Task taskToBeUnmarked = tasks.get(taskIndex - 1);
        taskToBeUnmarked.uncheckTask();

        fileStorage.overwriteTask(taskIndex, taskToBeUnmarked.getTaskDataString());

        return taskToBeUnmarked;
    }

    /**
     * Deletes the task located at the given task index.
     * Tells the file storage class to carry out the process.
     * Also updates the most recently deleted task to be restored if undo is called.
     *
     * @param taskIndex The index where the deleted task is located at.
     * @return The deleted task itself to be prompted to the user.
     */
    public Task deleteTask(int taskIndex) {
        assert isTaskIndexValid(taskIndex) : "task index to be deleted should be valid";

        Task taskToBeDeleted = tasks.get(taskIndex - 1);
        tasks.remove(taskToBeDeleted);
        mostRecentDeletedTask = taskToBeDeleted;

        fileStorage.removeTask(taskIndex);

        return taskToBeDeleted;
    }

    /**
     * Finds the tasks based on the given keyword.
     * The keyword check is only done to the task description.
     * These tasks are converted into the desired display format and collected into an array.
     *
     * @param keyword The keyword used to find tasks.
     * @return The array of tasks in their displayed format.
     */
    public String[] findTasksToDisplay(String keyword) {
        AtomicInteger i = new AtomicInteger();

        return tasks.stream()
                .filter(task -> task.isDescriptionContainsKeyword(keyword))
                .map(task -> getTaskDisplayString(i.getAndIncrement() + 1, (Task) task))
                .toArray(String[]::new);
    }

    /**
     * Formats the list of tasks as a nicely organized indexed list.
     *
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
     *
     * @param index The current task index.
     * @param task The task to be displayed.
     * @return A string of the desired task display format.
     */
    public String getTaskDisplayString(int index, Task task) {
        return index + ". " + task.toString();
    }

    /**
     * Gets the total number of tasks the user currently has.
     *
     * @return The number of tasks.
     */
    public int getTotalTasks() {
        return tasks.size();
    }

    /**
     * Checks whether the task index used to process a task is valid.
     * The index is considered valid if there's a task of that index exists.
     *
     * @param taskIndex The task index to check.
     * @return true or false depending on whether the index is valid.
     */
    public boolean isTaskIndexValid(int taskIndex) {
        return taskIndex >= 1 && taskIndex <= getTotalTasks();
    }

    /**
     * Checks whether the given task type is valid.
     * The task manager will take a look at the set of valid task types and
     * determines whether the task type is in it or not.
     *
     * @param type The task type to check for existence.
     * @return true or false depending on whether the set of valid task type contains the given type.
     */
    public boolean isTaskTypeExist(String type) {
        return TASK_TYPES.contains(type);
    }

    public String getMostRecentDeletedTaskAddCommand() {
        return mostRecentDeletedTask.getAddTaskCommand();
    }

}

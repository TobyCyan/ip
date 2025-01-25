package manager;

import exception.DeadlineNotEnoughInfoException;
import exception.EventNotEnoughInfoException;
import exception.MeiException;
import fileaccess.FileRead;
import fileaccess.FileWrite;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private static TaskManager instance = null;
    /** Regex to use for splitting the given user task input. */
    private final String TASK_STRING_SPLIT_REGEX = "(/from|/by|/to)";

    /**
     * The constructor.
     * Made private to prevent direct initialization.
     * Should call getInstance instead.
     * Attempts to read the list of tasks from the .txt file and store it into tasks.
     */
    private TaskManager() {
        try {
            this.tasks = FileRead.readFromFile();
        } catch (Exception e) {
            System.out.println("Error reading from file path: " + e.getMessage());
        }
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
     * Processes new added tasks before returning them back to the response manager to prompt the user.
     * @param taskType The type of the task.
     * @param taskDescription The description of the task.
     * @return The processed task itself.
     */
    public Task processAddTask(String taskType, String taskDescription) throws MeiException {
        Task newTask = null;
        String[] taskDescriptionSplit = taskDescription.split(TASK_STRING_SPLIT_REGEX, 3);
        String description = taskDescriptionSplit[0];

        switch (taskType) {
        case "todo":
            newTask = new ToDo(description);
            break;

        case "deadline":
            // Task description split must be length 2.
            if (taskDescriptionSplit.length < 2) {
                throw new DeadlineNotEnoughInfoException(ResponseManager.getResponses("DeadlineNotEnoughInfo"));
            }

            String deadlineDateTime = taskDescriptionSplit[1];
            newTask = new Deadline(description, deadlineDateTime);
            break;

        case "event":
            // Task description split must be length 3.
            if (taskDescriptionSplit.length < 3) {
                throw new EventNotEnoughInfoException(ResponseManager.getResponses("EventNotEnoughInfo"));
            }

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
     * @param task The new task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
        try {
            FileWrite.writeTaskToFile(task);
        } catch (Exception e) {
            System.out.println("Error writing task to file path: " + e.getMessage());
        }
    }

    /**
     * Marks the given task as completed.
     * TODO: (Optional) Add a response when user tries to mark a task that is already marked.
     * @param taskIndex The index of the task to be marked as completed.
     * @return The completed task itself.
     */
    public Task markTask(int taskIndex) {
        Task taskToBeMarked = tasks.get(taskIndex - 1);
        taskToBeMarked.completeTask();
        try {
            FileWrite.overwriteTaskData(taskIndex, taskToBeMarked.getTaskDataString());
        } catch (Exception e) {
            System.out.println("Error overwriting task upon completion: " + e.getMessage());
        }
        return taskToBeMarked;
    }

    /**
     * Marks the given task as incomplete.
     * TODO: (Optional) Add a response when user tries to unmark a task that isn't marked.
     * @param taskIndex The index of the task to be marked as incomplete.
     * @return The unmarked task itself.
     */
    public Task unmarkTask(int taskIndex) {
        Task taskToBeUnmarked = tasks.get(taskIndex - 1);
        taskToBeUnmarked.uncheckTask();
        try {
            FileWrite.overwriteTaskData(taskIndex, taskToBeUnmarked.getTaskDataString());
        } catch (Exception e) {
            System.out.println("Error overwriting task upon incompletion: " + e.getMessage());
        }
        return taskToBeUnmarked;
    }

    public Task deleteTask(int taskIndex) {
        Task taskToBeDeleted = tasks.get(taskIndex - 1);
        tasks.remove(taskToBeDeleted);
        try {
            FileWrite.removeTaskData(taskIndex);
        } catch (Exception e) {
            System.out.println("Error deleting task from the .txt file: " + e.getMessage());
        }
        return taskToBeDeleted;
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
        return tasks.size();
    }

    /**
     * Checks whether the task index used to process a task is valid.
     * @param taskIndex The task index to check.
     * @return true or false.
     */
    public boolean isTaskIndexValid(int taskIndex) {
        return taskIndex >= 1 && taskIndex <= getTotalTasks();
    }

}

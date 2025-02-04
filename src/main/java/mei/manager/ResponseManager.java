package mei.manager;

import java.util.Arrays;
import java.util.HashMap;

import mei.javafx.MainWindow;
import mei.tasks.Task;



/**
 * This is the manager responsible for managing Mei's responses.
 * Any calls to make Mei speak or make a response should be done via here.
 */
public class ResponseManager {
    private static final HashMap<String, String[]> RESPONSE_MAP = new HashMap<>();
    private final TaskManager taskManager;

    /**
     * Initializes a response map which maps the response type to its array of responses.
     * The response map is given the final modifier as no modifications should be made upon initialization.
     */
    public ResponseManager(TaskManager taskManager) {
        // Task manager.
        this.taskManager = taskManager;

        // Basic greetings.
        RESPONSE_MAP.put("Greeting", new String[] {"Hello! My name is Mei!", "What can I do for you?"});
        RESPONSE_MAP.put("Exit", new String[] {"See you next time! :)"});

        // Listing user tasks.
        RESPONSE_MAP.put("ListTasks", new String[] {"Sure! Here are all your tasks!", "Enjoy :3"});
        RESPONSE_MAP.put("NoTask", new String[] {"I can't find any tasks for you :(", "Maybe start adding new tasks?"});

        // Listing found user tasks.
        RESPONSE_MAP.put("FindTasks", new String[] {"Alright! Here are the tasks that I found from your list:",
                                                    "Hope I didn't miss any!"});
        RESPONSE_MAP.put("FindTasksEmpty", new String[] {"Sorry! It seems like there isn't any tasks"
                                                            + " that matched the description :("});

        // Adding new task.
        RESPONSE_MAP.put("AddTask", new String[] {"Certainly! Your new task is on the way!"});
        RESPONSE_MAP.put("AddTaskSuccess", new String[] {"Task successfully added! Yay!", "Your added task is:\n",
                                                            "The total tasks you currently have is: "});

        // Marking and unmarking existing tasks.
        RESPONSE_MAP.put("MarkTask", new String[] {"You've completed this? That's amazing!",
                                                      "I've noted down your achievement, congratulations!"});
        RESPONSE_MAP.put("UnmarkTask", new String[] {"It's alright to take things easy.",
                                                        "I've unchecked this task for you to revisit next time!"});

        // Deleting tasks.
        RESPONSE_MAP.put("DeleteTask", new String[] {"Got it! I will erase this task from my list.",
            "The removed task is:\n", "The amount of tasks left for you is: "});

        // Exception responses.
        RESPONSE_MAP.put("UnknownUserInput", new String[] {"Come again? I don't quite get what you are saying."});
        RESPONSE_MAP.put("UnknownTaskType", new String[] {"Oops! I think you may have entered an unknown task type!"
                + "Please try again!", "The accepted tasks are todo, deadline, and event :))"});
        RESPONSE_MAP.put("EmptyTaskDescription", new String[] {"Remember to add a description to your tasks, okay?"});
        RESPONSE_MAP.put("TaskIndexOutOfBounds", new String[] {"Hmm..? This task number doesn't seem to be"
                + "on the list...", "Can you repeat with a valid one? :3"});
        RESPONSE_MAP.put("DeadlineNotEnoughInfo", new String[] {"Hmm? I think you missed some information there...",
            "I would need to know the deadline so... do use /by to indicate it!"});
        RESPONSE_MAP.put("EventNotEnoughInfo", new String[] {"Hmm? I think you missed some information there...",
            "I would need to know the start and end date/times so... do use /from and /to to indicate them!"});
    }

    /**
     * Returns back the input array to the main window to be set as Mei's responses to the user.
     *
     * @param inputs The input array to return back.
     */
    public static void setInputsAsApplicationResponse(String[] inputs) {
        MainWindow.setMeiResponses(inputs);
    }

    /**
     * Prompts to the user after a task is successfully added.
     * The corresponding array that contains the responses are fetched with the relevant keyword.
     * Goes through a series of process to append the newly added task to the end of the response array
     * to echo it back to the user.
     *
     * @param task The task successfully added to be echoed.
     */
    public void addTaskResponse(Task task) {
        String[] addTaskSuccessResponses = getResponses("AddTaskSuccess");
        int totalTasks = taskManager.getTotalTasks();

        // Index variables where information should be appended to.
        int totalTaskStringIndex = addTaskSuccessResponses.length - 1;
        int taskStringIndex = 1;

        // Append the newly updated information to the responses.
        addTaskSuccessResponses[taskStringIndex] += task.toString();
        addTaskSuccessResponses[totalTaskStringIndex] += totalTasks;

        // Echo the responses.
        setInputsAsApplicationResponse(addTaskSuccessResponses);
    }

    /**
     * Prompts to the user after task is deleted.
     * The corresponding array that contains the responses are fetched with the relevant keyword.
     * Goes through a series of process to append the deleted task to the end of the response array
     * to echo it back to the user.
     *
     * @param deletedTask The deleted task.
     */
    public void deleteTaskResponse(Task deletedTask) {
        String[] deleteTaskSuccessResponses = getResponses("DeleteTask");
        int totalTasks = taskManager.getTotalTasks();

        // Index variables where information should be appended to.
        int totalTaskStringIndex = deleteTaskSuccessResponses.length - 1;
        int taskStringIndex = 1;

        // Append the newly updated information to the responses.
        deleteTaskSuccessResponses[taskStringIndex] += deletedTask.toString();
        deleteTaskSuccessResponses[totalTaskStringIndex] += totalTasks;

        // Echo the responses.
        setInputsAsApplicationResponse(deleteTaskSuccessResponses);
    }

    /**
     * Prompts to the user their list of tasks.
     *
     * @param tasksToBeDisplayed The list of valid tasks to be displayed to the user.
     */
    public void listTasksResponse(String[] tasksToBeDisplayed) {
        String[] listTasksResponses = getResponses("ListTasks");
        listTasksResponses = concatResponses(listTasksResponses, tasksToBeDisplayed);
        setInputsAsApplicationResponse(listTasksResponses);
    }

    /**
     * Concatenates 2 arrays, first followed by the second.
     * If the arrays are empty, an empty string array is returned.
     * However, we expect both the first and second arrays are always non-empty.
     *
     * @param first The first array.
     * @param second The second array.
     * @return The resulting array after concatenation.
     */
    public String[] concatResponses(String[] first, String[] second) {
        int firstLength = first.length;
        int secondLength = second.length;
        String[] result = new String[firstLength + secondLength];

        System.arraycopy(first, 0, result, 0, firstLength);
        System.arraycopy(second, 0, result, firstLength, firstLength + secondLength - firstLength);

        return result;
    }

    /**
     * Lets the user know if there is no task to be found.
     */
    public void noTaskResponse() {
        setInputsAsApplicationResponse(getResponses("NoTask"));
    }

    /**
     * Prompts to the user that the given task has been marked as completed.
     *
     * @param markedTask The task to be marked as completed.
     */
    public void markTaskResponse(Task markedTask) {
        String[] markTaskResponses = appendTaskStringToResponseArrayAndReturn("MarkTask", markedTask.toString());
        setInputsAsApplicationResponse(markTaskResponses);
    }

    /**
     * Prompts to the user that the given task has been marked as incomplete.
     *
     * @param unmarkedTask The task to be marked as incomplete.
     */
    public void unmarkTaskResponse(Task unmarkedTask) {
        String[] unmarkTaskResponses = appendTaskStringToResponseArrayAndReturn("UnmarkTask", unmarkedTask.toString());
        setInputsAsApplicationResponse(unmarkTaskResponses);
    }

    /**
     * Prompts to the user their list of tasks found based on the keyword.
     * It is possible that the foundTasks is empty.
     *
     * @param foundTasks The list of found tasks to be displayed to the user.
     */
    public void findTasksResponse(String[] foundTasks) {
        String[] foundTasksResponses = getResponses("FindTasks");

        if (foundTasks.length == 0) {
            setInputsAsApplicationResponse(getResponses("FindTasksEmpty"));
            return;
        }

        foundTasksResponses = concatResponses(foundTasksResponses, foundTasks);
        setInputsAsApplicationResponse(foundTasksResponses);
    }


    /**
     * Appends a task as its string representation defined in its class to a response array
     * corresponding to the given array key.
     * This method also returns the final appended array.
     *
     * @param arrayKey The key to fetch the response array from the RESPONSE_MAP.
     * @param taskString The task represented as a string.
     * @return The resulting appended array.
     */
    public String[] appendTaskStringToResponseArrayAndReturn(String arrayKey, String taskString) {
        String[] taskResponses = getResponses(arrayKey);
        int unmarkTaskResponseLength = taskResponses.length + 1;
        taskResponses = Arrays.copyOf(taskResponses, unmarkTaskResponseLength);
        taskResponses[unmarkTaskResponseLength - 1] = taskString;
        return taskResponses;
    }

    /**
     * Get the array of responses from the response map.
     *
     * @param key The hash key of the responses.
     * @return The array of responses corresponding to the given key.
     */
    public static String[] getResponses(String key) {
        return RESPONSE_MAP.get(key);
    }

}

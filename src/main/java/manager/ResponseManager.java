package manager;

import exception.*;
import tasks.Task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This is the manager responsible for managing Mei's responses.
 * Any calls to make Mei speak or make a response should be done via here.
 */
public class ResponseManager {
    private static final HashMap<String, String[]> RESPONSE_MAP = new HashMap<>();
    private TaskManager taskManager;

    /**
     * The constructor.
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

        // Adding new task.
        RESPONSE_MAP.put("AddTask", new String[] {"Certainly! Your new task is on the way!"});
        RESPONSE_MAP.put("AddTaskSuccess", new String[] {"Task successfully added! Yay!", "Your added task is:\n", "The total tasks you currently have is: "});

        // Marking and unmarking existing tasks.
        RESPONSE_MAP.put("MarkTask", new String[] {"You've completed this? That's amazing!", "I've noted down your achievement, congratulations!"});
        RESPONSE_MAP.put("UnmarkTask", new String[] {"It's alright to take things easy.", "I've unchecked this task for you to revisit next time!"});

        // Deleting tasks.
        RESPONSE_MAP.put("DeleteTask", new String[] {"Got it! I will erase this task from my list.", "The removed task is:\n", "The amount of tasks left for you is: "});

        // Exception responses.
        RESPONSE_MAP.put("UnknownUserInput", new String[] {"Come again? I don't quite get what you are saying."});
        RESPONSE_MAP.put("UnknownTaskType", new String[] {"Oops! I think you may have entered an unknown task type! Please try again!", "The accepted tasks are todo, deadline, and event :))"});
        RESPONSE_MAP.put("EmptyTaskDescription", new String[] {"Remember to add a description to your tasks, okay?"});
        RESPONSE_MAP.put("TaskIndexOutOfBounds", new String[] {"Hmm..? This task number doesn't seem to be on the list...", "Can you repeat with a valid one? :3"});
        RESPONSE_MAP.put("DeadlineNotEnoughInfo", new String[] {"Hmm? I think you missed some information there...", "I would need to know the deadline so... do use /by to indicate it!"});
        RESPONSE_MAP.put("EventNotEnoughInfo", new String[] {"Hmm? I think you missed some information there...", "I would need to know the start and end date/times so... do use /from and /to to indicate them!"});
    }


    /**
     * A function that simply echos what is passed in.
     * This function ensures that every response echoed is wrapped in divider lines.
     * @param input The input string to echo.
     */
    public void echo(String input) {
        dividerLine();
        System.out.println(input);
        dividerLine();
    }

    /**
     * A function that echos everything in the given input array.
     * This function ensures that all the responses echoed are wrapped in the desired format.
     * @param inputs The input array to echo.
     */
    public static void echoLines(String[] inputs) {
        dividerLine();
        for (String input : inputs) {
            System.out.println(input);
        }
        dividerLine();
    }

    /**
     * Greets the user, pretty straight forward.
     */
    public void greetUser() {
        echoLines(getResponses("Greeting"));
    }

    /**
     * Bids farewell to the user and exits.
     * This should be the final response of the interaction.
     */
    public void exitChat() {
        echoLines(getResponses("Exit"));
    }

    /**
     * Prompts to the user after a task is successfully added.
     * Important to update the index variables where the information should be appended in the response array.
     * @param task The task successfully added to be echoed.
     */
    public void addTaskResponse(Task task) {
        String[] addTaskSuccessResponses = getResponses("AddTaskSuccess");
        int totalTasks = taskManager.getTotalTasks();

        // Index variables where information should be appended to.
        int totalTaskStringIndex = addTaskSuccessResponses.length - 1;
        int taskStringIndex = 1;

        // Cache the original string.
        String initialAddedTaskString = addTaskSuccessResponses[taskStringIndex];
        String initialTotalTaskString = addTaskSuccessResponses[totalTaskStringIndex];

        // Append the newly updated information to the responses.
        addTaskSuccessResponses[taskStringIndex] += task.toString();
        addTaskSuccessResponses[totalTaskStringIndex] += totalTasks;

        // Echo the responses.
        echoLines(addTaskSuccessResponses);

        // Reset the added task string back to default without the newly added task.
        addTaskSuccessResponses[taskStringIndex] = initialAddedTaskString;
        addTaskSuccessResponses[totalTaskStringIndex] = initialTotalTaskString;
    }

    /**
     * Prompts to the user after task is deleted.
     * @param deletedTask The deleted task.
     */
    public void deleteTaskResponse(Task deletedTask) {
        String[] deleteTaskSuccessResponses = getResponses("DeleteTask");
        int totalTasks = taskManager.getTotalTasks();

        // Index variables where information should be appended to.
        int totalTaskStringIndex = deleteTaskSuccessResponses.length - 1;
        int taskStringIndex = 1;

        // Cache the original string.
        String initialDeletedTaskString = deleteTaskSuccessResponses[taskStringIndex];
        String initialTotalTaskString = deleteTaskSuccessResponses[totalTaskStringIndex];

        // Append the newly updated information to the responses.
        deleteTaskSuccessResponses[taskStringIndex] += deletedTask.toString();
        deleteTaskSuccessResponses[totalTaskStringIndex] += totalTasks;

        // Echo the responses.
        echoLines(deleteTaskSuccessResponses);

        // Reset the added task string back to default without the newly added task.
        deleteTaskSuccessResponses[taskStringIndex] = initialDeletedTaskString;
        deleteTaskSuccessResponses[totalTaskStringIndex] = initialTotalTaskString;
    }

    /**
     * Prompts to the user their list of tasks.
     * @param tasksToBeDisplayed The list of valid tasks to be displayed to the user.
     */
    public void listTasksResponse(String[] tasksToBeDisplayed) {
        String[] listTasksResponses = getResponses("ListTasks");
        listTasksResponses = concatResponses(listTasksResponses, tasksToBeDisplayed);
        echoLines(listTasksResponses);
    }

    /**
     * Concatenates 2 arrays, first followed by the second.
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
        echoLines(getResponses("NoTask"));
    }

    /**
     * Prompts to the user that the given task has been marked as completed.
     * @param markedTask The task to be marked as completed.
     */
    public void markTaskResponse(Task markedTask) {
        String[] markTaskResponses = appendTaskStringToResponseArrayAndReturn("MarkTask", markedTask.toString());
        echoLines(markTaskResponses);
    }

    /**
     * Prompts to the user that the given task has been marked as incomplete.
     * @param unmarkedTask The task to be marked as incomplete.
     */
    public void unmarkTaskResponse(Task unmarkedTask) {
        String[] unmarkTaskResponses = appendTaskStringToResponseArrayAndReturn("UnmarkTask", unmarkedTask.toString());
        echoLines(unmarkTaskResponses);
    }

    public String[] appendTaskStringToResponseArrayAndReturn(String arrayKey, String taskString) {
        String[] taskResponses = getResponses(arrayKey);
        int unmarkTaskResponseLength = taskResponses.length + 1;
        taskResponses = Arrays.copyOf(taskResponses, unmarkTaskResponseLength);
        taskResponses[unmarkTaskResponseLength - 1] = taskString;
        return taskResponses;
    }

    /**
     * A divider line that groups the responses and makes everything neater.
     * Currently, we shall use a hard-coded method of calling this before and after every single response function calls.
     */
    private static void dividerLine() {
        System.out.println("_________________________________");
    }

    /**
     * Get the array of responses from the response map.
     * @param key The hash key of the responses.
     * @return The array of responses corresponding to the given key.
     */
    public static String[] getResponses(String key) {
        return RESPONSE_MAP.get(key);
    }

}

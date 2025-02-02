package mei;

import mei.fileaccess.FileStorage;
import mei.manager.InputManager;
import mei.manager.ResponseManager;
import mei.manager.TaskManager;

import java.util.Scanner;

/**
 * Represents the main class for the chatbot known as Mei.
 * Consists of all the managers that are passed into other managers to establish communication between the managers.
 * The chatbot application shall be run from the main method.
 */
public class Mei {
    private FileStorage fileStorage;
    private ResponseManager responseManager;
    private TaskManager taskManager;
    private InputManager inputManager;

    /**
     * Initializes the chatbot known as Mei.
     * Sets up the connections between managers.
     * Initializes the file storage with the given file path to save task data.
     * Also starts up by greeting the user first.
     *
     * @param filePath The file path to save task data.
     */
    public Mei(String filePath) {
        this.fileStorage = new FileStorage(filePath);
        this.taskManager = new TaskManager(fileStorage.readTasks(), fileStorage);
        // Initialize response manager to process user input and generate responses.
        this.responseManager = new ResponseManager(taskManager);
        this.inputManager = new InputManager(taskManager, responseManager);
    }

    /**
     * Sends the responses from Mei to the MainWindow after processing the user input via the managers.
     * The user input is redirected by the input manager to other managers,
     * which then sets the field that is used to display to the user as a response.
     *
     * @param userInput The user input to redirect and get a response out of.
     */
    public void redirectInputToSetResponses(String userInput) {
        inputManager.redirectInput(userInput);
    }

}

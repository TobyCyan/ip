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

    public Mei(String filePath) {
        this.fileStorage = new FileStorage(filePath);
        this.taskManager = new TaskManager(fileStorage.readTasks(), fileStorage);
        // Initialize response manager to process user input and generate responses.
        this.responseManager = new ResponseManager(taskManager);
        this.inputManager = new InputManager(taskManager, responseManager);
    }

    /**
     * Runs the chatbot known as Mei.
     * Starts of by greeting the user,
     * then take in input by the user and passes it to the Input Manager to be redirected to the relevant managers.
     * Finally, exits the conversation when the user says "bye".
     */
    public void run() {
        // First, greet the user.
        responseManager.greetUser();

        // Get user input.
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        // Process every user input until the user bids farewell to Mei.
        while (!userInput.equals("bye")) {
            inputManager.redirectInput(userInput);
            userInput = scanner.nextLine();
        }

        // Prompt Mei to bid farewell to the user and exit the chat.
        responseManager.exitChat();
    }

    /**
     * The main method to execute the chatbot.
     * Mei is instantiated with the file path for saving and reading task data.
     *
     * @param args The default arguments to execute the main method.
     */
    public static void main(String[] args) {
        new Mei("./taskdata/tasks.txt").run();
    }
}

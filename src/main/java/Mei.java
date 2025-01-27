import fileaccess.FileStorage;
import manager.InputManager;
import manager.ResponseManager;
import manager.TaskManager;
import tasks.Task;

import java.util.Scanner;

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

    public static void main(String[] args) {
        new Mei("./taskdata/tasks.txt").run();
    }
}

import java.util.Scanner;

public class Mei {
    public static void main(String[] args) {
        // Initialize response manager to process user input and generate responses.
        ResponseManager responseManager = ResponseManager.getInstance();

        // First, greet the user.
        responseManager.greetUser();

        // Get user input.
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        // Process every user input until the user bids farewell to Mei.
        while (!userInput.equals("bye")) {
            responseManager.redirectInput(userInput);
            userInput = scanner.nextLine();
        }

        // Prompt Mei to bid farewell to the user and exit the chat.
        responseManager.exitChat();
    }
}

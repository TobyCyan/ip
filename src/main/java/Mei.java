import java.util.Scanner;

public class Mei {
    public static void main(String[] args) {
        TaskManager taskManager = TaskManager.getInstance();
        ResponseManager responseManager = ResponseManager.getInstance();

        responseManager.greetUser();

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        while (!userInput.equals("bye")) {
            responseManager.redirectInput(userInput);
            userInput = scanner.nextLine();
        }
        responseManager.exitChat();
    }
}

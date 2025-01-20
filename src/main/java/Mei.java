import java.util.Scanner;

public class Mei {
    public static void main(String[] args) {
        ResponseManager responseManager = new ResponseManager();
        responseManager.greetUser();

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        while (!userInput.equals("bye")) {
            responseManager.echo(userInput);
            userInput = scanner.nextLine();
        }
        responseManager.exitChat();
    }
}

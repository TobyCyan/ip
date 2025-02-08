package mei.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mei.Mei;
import mei.manager.ResponseManager;

/**
 * Represents the main window class that contains all the components in the application.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private static String[] meiText;
    @FXML
    private VBox dialogContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userImg = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image meiImg = new Image(this.getClass().getResourceAsStream("/images/MeiChibi.png"));
    private Mei mei;

    /**
     * Initializes the main window.
     * Sets the vValue to be bound with the dialog container's height.
     * Also adds a greeting message to the user.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        addChildrenToDialogContainer(getMeiDialog(ResponseManager.getResponses("Greeting")));
    }

    /** Injects the Duke instance */
    public void setMei(Mei mei) {
        this.mei = mei;
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        // Gets user input and Mei's responses.
        String userText = userInput.getText();

        // Error handling when user input is just a bunch of spaces.
        if (userText.trim().isEmpty()) {
            userInput.clear();
            return;
        }

        assert !userText.isEmpty() : "user input should not be empty";

        if (userText.equals("bye")) {
            // Says bye to the user.
            DialogBox exitChatDialogBox = getMeiDialog(ResponseManager.getResponses("Exit"));
            addChildrenToDialogContainer(exitChatDialogBox);
            Platform.exit();
            return;
        }

        mei.redirectInputToSetResponses(userText);

        // Create dialog boxes for both the user and Mei.
        DialogBox userDialog = DialogBox.getUserDialog(userText, userImg);
        DialogBox meiDialog = getMeiDialog(meiText);

        // Add the new dialog boxes into the container.
        addChildrenToDialogContainer(userDialog, meiDialog);

        // Finally, clear the user input text box.
        userInput.clear();
    }

    /**
     * Gets the Mei dialog box component from the given text input.
     * This text input is displayed as Mei's response.
     *
     * @param meiText The text input as Mei's response.
     * @return The Mei dialog box component.
     */
    public DialogBox getMeiDialog(String[] meiText) {
        return DialogBox.getMeiDialog(meiText, meiImg);
    }

    /**
     * Adds any amount of dialog boxes into the dialog container to be displayed to the user.
     *
     * @param dialogBoxes The dialog boxes to be added to the container.
     */
    public void addChildrenToDialogContainer(DialogBox... dialogBoxes) {
        dialogContainer.getChildren().addAll(dialogBoxes);
    }

    /**
     * Sets the Mei responses that will be displayed to the user.
     *
     * @param responses The response array to be displayed.
     */
    public static void setMeiResponses(String[] responses) {
        meiText = responses;
    }

}

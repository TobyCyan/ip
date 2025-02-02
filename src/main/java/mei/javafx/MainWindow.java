package mei.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mei.Mei;

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
    private Image dukeImg = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));
    private Mei mei;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
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
        String userText = userInput.getText();
        mei.redirectInputToSetResponses(userText);

        DialogBox userDialog = DialogBox.getUserDialog(userText, userImg);
        DialogBox meiDialog = getMeiDialog(meiText);
        addChildrenToDialogContainer(userDialog, meiDialog);

        userInput.clear();
    }

    public DialogBox getMeiDialog(String[] meiText) {
        return DialogBox.getMeiDialog(meiText, dukeImg);
    }

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

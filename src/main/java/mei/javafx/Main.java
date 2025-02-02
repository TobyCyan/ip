package mei.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mei.Mei;

/**
 * Represents the main class for this application.
 */
public class Main extends Application {
    private Mei mei = new Mei("./taskdata/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> Platform.exit());
            fxmlLoader.<MainWindow>getController().setMei(mei);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

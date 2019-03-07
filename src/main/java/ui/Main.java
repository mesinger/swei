package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("SWEI");
        primaryStage.setScene(new Scene(root, 800, 480));
        primaryStage.setResizable(false);
        root.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

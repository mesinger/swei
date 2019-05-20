package ui;

import database.PicDatabaseAccess;
import image.IImageData;
import image.JPEGImageData;
import image.JPEGImageDataExtractor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import java.sql.Date;

public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("SWEI");
        primaryStage.setScene(new Scene(root, 800, 600));
        root.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

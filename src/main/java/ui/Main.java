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

import java.sql.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        PicDatabaseAccess db = new PicDatabaseAccess();

        if(db.open()){
            System.out.println("connected to db");

            db.setup();

            JPEGImageDataExtractor dataExtractor = new JPEGImageDataExtractor();
            IImageData data = dataExtractor.extractExifAndIPTC("img/IMG_0914.JPG");

            db.addImage(data);
        }
        else{
            System.out.println("error");
        }

        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("SWEI");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

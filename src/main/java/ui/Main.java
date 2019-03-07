package ui;

import database.PicDatabaseAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        PicDatabaseAccess db = new PicDatabaseAccess();

        if(db.open()){
            System.out.println("connected to db");

            db.setup();
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

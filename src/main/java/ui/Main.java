package ui;

import database.PicDatabaseAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.PhotographerModel;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.BasicConfigurator;

import java.sql.Date;

public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class);
    BasicConfigurator basicConfigurator;


    @Override
    public void start(Stage primaryStage) throws Exception{
        basicConfigurator.configure();
        logger.debug("Entering picdb ...");

        PicDatabaseAccess db = new PicDatabaseAccess();
        if(db.open()){

            db.setup();

            PhotographerModel model = new PhotographerModel();
            model.setFirstName("thomas");
            model.setSurName("brezina");
            model.setBirthDate(Date.valueOf("1997-03-10"));
            model.setNotes("notes...");

            db.addPhotographer(model);
        }

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

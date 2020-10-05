package fakeadmin;

import fakeadmin.log.Logger;
import fakeadmin.utils.SystemSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

public class Main extends Application {
    public static Stage mainStage;
    public static Scene mainScene;
    public static Controller controller;
    public static String POPPINS_REGULAR_NAME = "src/main/resources/fonts/poppins-regular.ttf", POPPINS_MEDIUM_NAME = "src/main/resources/fonts/poppins-medium.ttf", POPPINS_SEMIBOLD_NAME = "src/main/resources/fonts/poppins-semibold.ttf";
    public static Font POPPINS_REGULAR, POPPINS_MEDIUM, POPPINS_SEMIBOLD;
    public static Logger LOGGER = new Logger();
    @Override
    public void start(Stage primaryStage) throws Exception{
        LOGGER.progress("Starting fakeUpdate App");
        System.setProperty("prism.lcdtext", "false");
        SystemSettings.init();

        mainStage = primaryStage;
        POPPINS_REGULAR = Font.loadFont("file:" + POPPINS_REGULAR_NAME, 14);
        POPPINS_MEDIUM = Font.loadFont("file:" + POPPINS_MEDIUM_NAME, 14);
        POPPINS_SEMIBOLD = Font.loadFont("file:" + POPPINS_SEMIBOLD_NAME, 14);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/main.fxml"));
        Parent root = fxmlLoader.load();

        controller = (Controller) fxmlLoader.getController();
        mainStage.setTitle("FakeMatez");
        mainStage.setResizable(false);

        mainScene = new Scene(root, 400,570);
        mainScene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
        mainStage.setScene(mainScene);
        mainStage.setOnCloseRequest(close -> {
            LOGGER.log("Closing app");
            System.exit(0);
        });
        mainStage.show();
        LOGGER.success("Started");
    }

    /*ą
    ą = u0105
    ę = u0119
    ś = u015B
    ć = u0107
    ł = u0142
    ń = u0144
    ó = u00F3
    Ó = u00D3
    ż = u017C
     */

    /*
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("main.fxml"));
        controller = (Controller) fxmlLoader.getController();
        primaryStage.setTitle("FakeAdmin --- made by matez");

        primaryStage.setScene(new Scene(root, screenSize.width, screenSize.height));
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(new KeyCombination() {
            @Override
            public boolean match(KeyEvent event) {
                if(event.isControlDown() && event.isAltDown()){
                    if(event.getCode()== KeyCode.I){
                        return true;
                    }
                }
                return false;
            }
        });
        primaryStage.setFullScreen(true);
        primaryStage.show();
     */

    public static void main(String[] args) {
        launch(args);
    }
}

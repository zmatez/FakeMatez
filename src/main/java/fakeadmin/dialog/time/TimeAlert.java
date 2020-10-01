package fakeadmin.dialog.time;

import com.jfoenix.controls.JFXAlert;
import fakeadmin.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public class TimeAlert extends JFXAlert<Optional<TimeConfiguration>> {
    public TimeAlert(Window window, String information, TimeConfiguration startCfg){
        super(window);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/timedialog.fxml"));
            Parent root = loader.load();
            TimeController controller = loader.<TimeController>getController();
            controller.setInformation(information);
            controller.setStartCfg(startCfg);
            setContent(root);
            setHeight(270);

            onCloseRequestProperty().addListener(close -> {
                Main.LOGGER.debug("Closed TimeDialog");
                close();
            });

            onHidingProperty().addListener(close -> {
                Main.LOGGER.debug("Closed TimeDialog");
                close();
            });


            controller.initButtons(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TimeAlert(Window window, String information){
        this(window,information,new TimeConfiguration(0,0,0));
    }

}

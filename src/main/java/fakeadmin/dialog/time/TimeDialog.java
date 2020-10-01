package fakeadmin.dialog.time;

import fakeadmin.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;

import java.io.IOException;
import java.util.Optional;

public class TimeDialog extends Dialog<Optional<TimeConfiguration>> {
    public TimeDialog(String information){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/timedialog.fxml"));
            Parent root = loader.load();
            TimeController controller = loader.<TimeController>getController();
            controller.setInformation(information);
            getDialogPane().setContent(root);

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
}

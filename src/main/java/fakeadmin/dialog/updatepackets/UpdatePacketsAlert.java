package fakeadmin.dialog.updatepackets;

import com.jfoenix.controls.JFXAlert;
import fakeadmin.dialog.updatetexts.UpdateTextsConfiguration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public class UpdatePacketsAlert extends JFXAlert<Optional<UpdatePacketsConfiguration>> {
    public UpdatePacketsAlert(Window window, UpdatePacketsConfiguration startCfg){
        super(window);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/updatepacketsdialog.fxml"));
            Parent root = loader.load();
            UpdatePacketsController controller = loader.<UpdatePacketsController>getController();
            controller.setStartCfg(startCfg);
            setContent(root);

            controller.initButtons(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UpdatePacketsAlert(Window window){
        this(window,new UpdatePacketsConfiguration());
    }

}

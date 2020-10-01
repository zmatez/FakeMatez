package fakeadmin.dialog.updatetexts;

import com.jfoenix.controls.JFXAlert;
import fakeadmin.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public class UpdateTextsAlert extends JFXAlert<Optional<UpdateTextsConfiguration>> {
    public UpdateTextsAlert(Window window, UpdateTextsConfiguration startCfg){
        super(window);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/updatetextsdialog.fxml"));
            Parent root = loader.load();
            UpdateTextsController controller = loader.<UpdateTextsController>getController();
            controller.setStartCfg(startCfg);
            setContent(root);

            controller.initButtons(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UpdateTextsAlert(Window window){
        this(window,new UpdateTextsConfiguration("Przetwarzanie aktualizacji %s<br>%pbNie wy\u0142\u0105czaj komputera. Troch\u0119 to potrwa.",""));
    }

}

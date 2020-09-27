package fakeadmin;

import com.jfoenix.controls.JFXColorPicker;
import fakeadmin.utils.SystemSettings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    protected Pane root;

    //UPDATE
    @FXML
    protected JFXColorPicker updateColorPicker;

    @FXML
    protected void initialize(){
        updateColorPicker.setValue(Color.valueOf("0078d7"));
    }
}

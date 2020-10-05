package fakeadmin.fakes.fakeUpdate;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class FakeUpdateController {
    @FXML
    protected Pane root;

    @FXML
    protected void initialize(){

    }

    protected void init(Color backgroundColor){
        root.setBackground(new Background(new BackgroundFill(backgroundColor,new CornerRadii(0), new Insets(0))));
    }
}

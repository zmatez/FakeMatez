package fakeadmin;

import com.jfoenix.controls.*;
import fakeadmin.dialog.time.TimeConfiguration;
import fakeadmin.dialog.updatepackets.UpdatePacketsConfiguration;
import fakeadmin.dialog.updatetexts.UpdateTextsConfiguration;
import fakeadmin.fakes.fakeUpdate.FakeUpdate;
import fakeadmin.utils.AlertUtils;
import fakeadmin.utils.Wait;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    @FXML
    protected Pane root;
    @FXML
    protected JFXTabPane tabPane;

    @FXML
    protected JFXButton startButton;

    @FXML
    protected JFXButton planStartButton;

    //UPDATE
    @FXML
    protected Tab updateTab;
    @FXML
    protected ScrollPane updateScrollPane;
    @FXML
    protected JFXColorPicker updateColorPicker;
    @FXML
    protected Label updateTimeLabel;
    @FXML
    protected JFXButton updateTimeButton;
    @FXML
    protected JFXButton updateTextsButton;
    @FXML
    protected Label updateTextsLabel;
    @FXML
    protected JFXButton updatePacketsButton;
    @FXML
    protected Label updatePacketsLabel;
    @FXML
    protected JFXToggleButton updateNotifyButton;
    @FXML
    protected JFXTextField updatePercentageField;

    private TimeConfiguration updateTimeConfiguration;
    private UpdateTextsConfiguration updateTextsConfiguration;
    private UpdatePacketsConfiguration updatePacketsConfiguration;

    @FXML
    protected void initialize(){
        initUpdate();
        JFXScrollPane.smoothScrolling(updateScrollPane);
        startButton.setOnAction(event -> {
            AlertUtils.showConfirmAlert("Uwaga","Po klikni\u0119ciu nie ma odwrotu. Wybrana opcja zostanie uruchomiona, tego procesu nie da si\u0119 odwr\u00F3ci\u0107.", start -> {
                start();
            },cancel -> {});
        });

        planStartButton.setOnAction(event -> {
            AlertUtils.showConfirmAlert("Uwaga","Po klikni\u0119ciu nie ma odwrotu. Wybrana opcja zostanie uruchomiona po wybranym czasie, tego procesu nie da si\u0119 odwr\u00F3ci\u0107.", start -> {
                Optional<TimeConfiguration> result = AlertUtils.showTimeAlert("Podaj po jakim czasie program ma si\u0119 w\u0142\u0105czy\u0107.");
                if(result.isPresent()){
                    TimeConfiguration cfg = result.get();
                    planStart(cfg);
                }else{
                }
            },cancel -> {});
        });

    }

    private javax.swing.Timer planTimer;
    private void planStart(TimeConfiguration configuration){
        Platform.setImplicitExit(false);
        Main.mainStage.hide();
        Main.LOGGER.log("Planned start for " + configuration.getHours() + ":" + configuration.getMinutes() + ":" + configuration.getSeconds());

        if(configuration.getAllAsSeconds() <=0){
            start();
        }else {
            AtomicInteger wait = new AtomicInteger();
            planTimer = new javax.swing.Timer(1000, action -> {
                if (wait.get() == configuration.getAllAsSeconds()) {
                    start();
                    planTimer.stop();
                }
                wait.getAndIncrement();
            });
            planTimer.setRepeats(true);
            planTimer.start();
        }
    }

    private void start(){
        Platform.runLater(() -> {
            Platform.setImplicitExit(false);
            Main.mainStage.hide();
            Main.LOGGER.log("Starting fakes");

            Wait w = new Wait(500,true,a -> {
                Platform.runLater(() -> {
                    if(tabPane.getSelectionModel().isSelected(0)){
                        FakeUpdate.run(updateColorPicker.getValue(), updateTimeConfiguration ,updateTextsConfiguration,updatePacketsConfiguration,updateNotifyButton.isSelected(),Integer.parseInt(updatePercentageField.getText()),true);
                    }
                });
            });
        });

    }

    //UPDATE
    protected void initUpdate(){
        updateColorPicker.setValue(Color.valueOf("0078d7"));
        setUpdateTime(1,0,0);
        setUpdateTexts("Przetwarzanie aktualizacji %s<br>%pbNie wy\u0142\u0105czaj komputera. Troch\u0119 to potrwa.","");
        setUpdatePackets();
        updateTimeButton.setOnAction(event -> {
            Optional<TimeConfiguration> result = AlertUtils.showTimeAlert("Ustaw czas fake aktualizacji.", updateTimeConfiguration);
            if(result.isPresent()){
                TimeConfiguration cfg = result.get();
                setUpdateTimeConfiguration(cfg);
            }
        });

        updateTextsButton.setOnAction(event -> {
            Optional<UpdateTextsConfiguration> result = AlertUtils.showUpdateTextsAlert(updateTextsConfiguration);
            if(result.isPresent()){
                UpdateTextsConfiguration cfg = result.get();
                setUpdateTextsConfiguration(cfg);
            }
        });

        updatePacketsButton.setOnAction(event -> {
            if(updateTextsConfiguration.getMainText().contains("%p") || updateTextsConfiguration.getMainText().contains("pb") || updateTextsConfiguration.getBottomText().contains("%p") || updateTextsConfiguration.getBottomText().contains("pb")) {
                Optional<UpdatePacketsConfiguration> result = AlertUtils.showUpdatePacketsAlert(updatePacketsConfiguration);
                if (result.isPresent()) {
                    UpdatePacketsConfiguration cfg = result.get();
                    setUpdatePacketsConfiguration(cfg);
                }
            }else{
                AlertUtils.showInfoAlert("Pakiety nie b\u0119d\u0105 widoczne", "Dodaj %p lub %pb do tekst\u00F3w aktualizacji, je\u015Bli chcesz widzie\u0107 pakiety.",e -> {
                    updateTextsButton.fire();
                });
            }
        });

        updatePercentageField.setText("100");
        updatePercentageField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try{
                if(!newValue.isEmpty()){
                    int val = Integer.parseInt(newValue);
                    if(val <= 0){
                        updatePercentageField.setText("100");
                    }
                }
            }catch (NumberFormatException e){
                updatePercentageField.setText("100");
            }
        }));
    }

    protected void setUpdateTime(int hours, int minutes, int seconds){
        updateTimeConfiguration = new TimeConfiguration(hours,minutes,seconds);
        updateTimeLabel.setText("Aktualizacja b\u0119dzie si\u0119 wykonywa\u0107 " + updateTimeConfiguration.toFormattedString(true));
    }

    protected void setUpdateTimeConfiguration(TimeConfiguration cfg){
        if(cfg!=null) {
            updateTimeConfiguration = cfg;
            updateTimeLabel.setText("Aktualizacja b\u0119dzie si\u0119 wykonywa\u0107 " + updateTimeConfiguration.toFormattedString(true));
        }
    }

    protected void setUpdateTexts(String mainText, String bottomText){
        updateTextsConfiguration = new UpdateTextsConfiguration(mainText,bottomText);
        updateTextsLabel.setText(updateTextsConfiguration.toFormattedString());
    }

    protected void setUpdateTextsConfiguration(UpdateTextsConfiguration cfg){
        if(cfg!=null) {
            updateTextsConfiguration = cfg;
            updateTextsLabel.setText(updateTextsConfiguration.toFormattedString());
        }
    }

    protected void setUpdatePackets(String... packets){
        updatePacketsConfiguration = new UpdatePacketsConfiguration(packets);
        updatePacketsLabel.setText(updatePacketsConfiguration.toFormattedString(5));
    }

    protected void setUpdatePackets(ArrayList<String> packets){
        updatePacketsConfiguration = new UpdatePacketsConfiguration(packets);
        updatePacketsLabel.setText(updatePacketsConfiguration.toFormattedString(5));
    }

    protected void setUpdatePacketsConfiguration(UpdatePacketsConfiguration cfg){
        if(cfg!=null) {
            updatePacketsConfiguration = cfg;
            updatePacketsLabel.setText(updatePacketsConfiguration.toFormattedString(5));
        }
    }
}

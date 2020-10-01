package fakeadmin.dialog.time;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import fakeadmin.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public class TimeController {
    @FXML
    protected JFXButton saveButton;
    @FXML
    protected JFXButton cancelButton;

    @FXML
    protected JFXTextField hoursField;
    @FXML
    protected JFXTextField minutesField;
    @FXML
    protected JFXTextField secondsField;

    @FXML
    protected Label infoLabel;

    @FXML
    protected void initialize(){
        initNumField(hoursField);
        initNumField(minutesField);
        initNumField(secondsField);
    }

    protected void initButtons(Dialog<Optional<TimeConfiguration>> dialog){
        saveButton.setOnAction(event -> {
            int hours = 0, minutes = 0, seconds = 0;
            try{
                if(hoursField.getText().isEmpty()){

                }else {
                    hours = Integer.parseInt(hoursField.getText());
                }
                if(minutesField.getText().isEmpty()){

                }else {
                    minutes = Integer.parseInt(minutesField.getText());
                }
                if(secondsField.getText().isEmpty()){

                }else {
                    seconds = Integer.parseInt(secondsField.getText());
                }

                dialog.setResult(Optional.of(new TimeConfiguration(hours,minutes,seconds)));
                dialog.close();
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("There was an error!");
                alert.setHeaderText("Unable to parse one of TextFields");
                alert.setContentText(e.getLocalizedMessage());

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("The exception stacktrace was:");

                JFXTextArea textArea = new JFXTextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0, 1);

                alert.getDialogPane().setExpandableContent(expContent);

                alert.showAndWait();
            }

        });

        cancelButton.setOnAction(event -> {
            dialog.setResult(Optional.empty());
            dialog.close();
        });
    }

    private void initNumField(JFXTextField field){
        IntegerValidator validator = new IntegerValidator();
        validator.setMessage("Warto\u015B\u0107 musi by\u0107 liczb\u0105");
        field.getValidators().add(validator);

        field.textProperty().addListener((change, oldVal, newVal) -> {
            field.validate();

            if((hoursField.getActiveValidator()==null || !hoursField.getActiveValidator().getHasErrors()) && (minutesField.getActiveValidator()==null || !minutesField.getActiveValidator().getHasErrors()) && (secondsField.getActiveValidator()==null || !secondsField.getActiveValidator().getHasErrors())){
                saveButton.setDisable(false);
            }else{
                saveButton.setDisable(true);
            }
        });

    }

    protected void setInformation(String info){
        infoLabel.setText(info);
    }

    protected void setStartCfg(TimeConfiguration cfg){
        hoursField.setText(cfg.getHours() + "");
        minutesField.setText(cfg.getMinutes() + "");
        secondsField.setText(cfg.getSeconds() + "");
    }
}

package fakeadmin.utils;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import fakeadmin.Main;
import fakeadmin.dialog.time.TimeAlert;
import fakeadmin.dialog.time.TimeConfiguration;
import fakeadmin.dialog.updatepackets.UpdatePacketsAlert;
import fakeadmin.dialog.updatepackets.UpdatePacketsConfiguration;
import fakeadmin.dialog.updatetexts.UpdateTextsAlert;
import fakeadmin.dialog.updatetexts.UpdateTextsConfiguration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import java.util.Optional;

public class AlertUtils {
    public static void showConfirmAlert(String header, String body, EventHandler<ActionEvent> startAction, EventHandler<ActionEvent> cancelAction){
        JFXAlert alert = new JFXAlert(Main.mainStage);
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        Label headingLabel = new Label(header);
        headingLabel.setStyle("-fx-font-family: 'Poppins SemiBold'; -fx-font-size: 18;");
        layout.setHeading(headingLabel);
        Label bodyLabel = new Label(body);
        bodyLabel.setStyle("-fx-font-family: 'Poppins Regular'; -fx-font-size: 15;");
        layout.setBody(bodyLabel);

        JFXButton closeButton = new JFXButton("Anuluj");
        closeButton.setStyle("-fx-background-color: #923dcf; -fx-text-fill: white; -fx-font-smoothing-type: gray; -fx-font-family: 'Poppins Medium'; -fx-font-size: 15");
        closeButton.setOnAction(e -> {
            alert.hideWithAnimation();
            cancelAction.handle(e);
        });
        JFXButton startButton = new JFXButton("Dalej");
        startButton.setStyle("-fx-background-color: #923dcf; -fx-text-fill: white; -fx-font-smoothing-type: gray; -fx-font-family: 'Poppins Medium'; -fx-font-size: 15");
        startButton.setOnAction(e -> {
            alert.hideWithAnimation();
            alert.close();
            startAction.handle(e);
        });
        layout.setActions(closeButton,startButton);
        alert.setContent(layout);
        alert.showAndWait();
    }
    public static void showInfoAlert(String header, String body, EventHandler<ActionEvent> action){
        JFXAlert alert = new JFXAlert(Main.mainStage);
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        Label headingLabel = new Label(header);
        headingLabel.setStyle("-fx-font-family: 'Poppins SemiBold'; -fx-font-size: 18;");
        layout.setHeading(headingLabel);
        Label bodyLabel = new Label(body);
        bodyLabel.setStyle("-fx-font-family: 'Poppins Regular'; -fx-font-size: 15;");
        layout.setBody(bodyLabel);

        JFXButton confirmButton = new JFXButton("OK");
        confirmButton.setStyle("-fx-background-color: #923dcf; -fx-text-fill: white; -fx-font-smoothing-type: gray; -fx-font-family: 'Poppins Medium'; -fx-font-size: 15");
        confirmButton.setOnAction(event3 -> {
            alert.hideWithAnimation();
            action.handle(event3);
        });
        layout.setActions(confirmButton);
        alert.setContent(layout);
        alert.showAndWait();
    }

    public static Optional<String> showTextDialog(String header, String body, String text, EventHandler<ActionEvent> cancel, EventHandler<ActionEvent> action){
        JFXAlert<Optional<String>> alert = new JFXAlert<>(Main.mainStage);
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        Label headingLabel = new Label(header);
        headingLabel.setStyle("-fx-font-family: 'Poppins SemiBold'; -fx-font-size: 18;");
        layout.setHeading(headingLabel);
        Label bodyLabel = new Label(body);
        bodyLabel.setStyle("-fx-font-family: 'Poppins Regular'; -fx-font-size: 15;");

        JFXTextField field = new JFXTextField(text);
        field.setFocusColor(Color.valueOf("#822da1"));

        layout.setBody(bodyLabel,field);

        JFXButton cancelButton = new JFXButton("Anuluj");
        cancelButton.setStyle("-fx-background-color: #923dcf; -fx-text-fill: white; -fx-font-smoothing-type: gray; -fx-font-family: 'Poppins Medium'; -fx-font-size: 15");
        cancelButton.setOnAction(e -> {
            alert.setResult(Optional.empty());
            alert.hideWithAnimation();
            cancel.handle(e);
        });

        JFXButton confirmButton = new JFXButton("Zapisz");
        confirmButton.setStyle("-fx-background-color: #923dcf; -fx-text-fill: white; -fx-font-smoothing-type: gray; -fx-font-family: 'Poppins Medium'; -fx-font-size: 15");
        confirmButton.setOnAction(e -> {
            alert.setResult(Optional.of(field.getText()));
            alert.hideWithAnimation();
            action.handle(e);
        });

        layout.setActions(cancelButton,confirmButton);
        alert.setContent(layout);
        alert.showAndWait();
        return alert.getResult();
    }

    public static boolean showBooleanConfirmAlert(String header, String body, EventHandler<ActionEvent> cancel, EventHandler<ActionEvent> action){
        JFXAlert<Boolean> alert = new JFXAlert<>(Main.mainStage);
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        Label headingLabel = new Label(header);
        headingLabel.setStyle("-fx-font-family: 'Poppins SemiBold'; -fx-font-size: 18;");
        layout.setHeading(headingLabel);
        Label bodyLabel = new Label(body);
        bodyLabel.setStyle("-fx-font-family: 'Poppins Regular'; -fx-font-size: 15;");

        layout.setBody(bodyLabel);

        JFXButton cancelButton = new JFXButton("Nie");
        cancelButton.setStyle("-fx-background-color: #923dcf; -fx-text-fill: white; -fx-font-smoothing-type: gray; -fx-font-family: 'Poppins Medium'; -fx-font-size: 15");
        cancelButton.setOnAction(e -> {
            alert.setResult(false);
            alert.hideWithAnimation();
            cancel.handle(e);
        });

        JFXButton confirmButton = new JFXButton("Tak");
        confirmButton.setStyle("-fx-background-color: #923dcf; -fx-text-fill: white; -fx-font-smoothing-type: gray; -fx-font-family: 'Poppins Medium'; -fx-font-size: 15");
        confirmButton.setOnAction(e -> {
            alert.setResult(true);
            alert.hideWithAnimation();
            action.handle(e);
        });

        layout.setActions(cancelButton,confirmButton);
        alert.setContent(layout);
        alert.showAndWait();
        return alert.getResult();
    }

    public static Optional<TimeConfiguration> showTimeAlert(String info){
        TimeAlert alert = new TimeAlert(Main.mainStage,info);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        alert.setWidth(Main.mainStage.getWidth());
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.showAndWait();
        return (Object)alert.getResult() instanceof ButtonType ? Optional.empty() : alert.getResult();
    }

    public static Optional<TimeConfiguration> showTimeAlert(String info,TimeConfiguration cfg){
        TimeAlert alert = new TimeAlert(Main.mainStage,info,cfg);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        alert.setWidth(Main.mainStage.getWidth());
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.showAndWait();
        return (Object)alert.getResult() instanceof ButtonType ? Optional.empty() : alert.getResult();
    }

    public static Optional<UpdateTextsConfiguration> showUpdateTextsAlert(){
        UpdateTextsAlert alert = new UpdateTextsAlert(Main.mainStage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        alert.setWidth(Main.mainStage.getWidth());
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.showAndWait();
        return (Object)alert.getResult() instanceof ButtonType ? Optional.empty() : alert.getResult();
    }

    public static Optional<UpdateTextsConfiguration> showUpdateTextsAlert(UpdateTextsConfiguration cfg){
        UpdateTextsAlert alert = new UpdateTextsAlert(Main.mainStage,cfg);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        alert.setWidth(Main.mainStage.getWidth());
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.showAndWait();
        return (Object)alert.getResult() instanceof ButtonType ? Optional.empty() : alert.getResult();
    }

    public static Optional<UpdatePacketsConfiguration> showUpdatePacketsAlert(){
        UpdatePacketsAlert alert = new UpdatePacketsAlert(Main.mainStage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        alert.setWidth(Main.mainStage.getWidth());
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.showAndWait();
        return (Object)alert.getResult() instanceof ButtonType ? Optional.empty() : alert.getResult();
    }

    public static Optional<UpdatePacketsConfiguration> showUpdatePacketsAlert(UpdatePacketsConfiguration cfg){
        UpdatePacketsAlert alert = new UpdatePacketsAlert(Main.mainStage,cfg);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setOverlayClose(false);
        alert.setWidth(Main.mainStage.getWidth());
        alert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        alert.showAndWait();
        return (Object)alert.getResult() instanceof ButtonType ? Optional.empty() : alert.getResult();
    }
}

package fakeadmin.fakes.fakeUpdate;

import fakeadmin.Controller;
import fakeadmin.Main;
import fakeadmin.dialog.updatepackets.UpdatePacketsConfiguration;
import fakeadmin.dialog.updatetexts.UpdateTextsConfiguration;
import fakeadmin.utils.Wait;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;

public class FakeUpdate {
    public static void run(Color backgroundColor, UpdateTextsConfiguration updateTextsConfiguration, UpdatePacketsConfiguration updatePacketsConfiguration, boolean notification, int maxPercentage){
        Main.LOGGER.log("Running FakeUpdatę");
        displayNotification(notification, e1 -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(FakeUpdate.class.getResource("/layout/restartlayout.fxml"));
                Parent root = fxmlLoader.load();

                Main.mainStage.setTitle("Windows Update");
                Main.mainStage.setResizable(false);

                Main.mainScene = new Scene(root, 400, 570);
                Main.mainScene.getStylesheets().add(FakeUpdate.class.getResource("/css/stylesheet.css").toExternalForm());
                Main.mainStage.setScene(Main.mainScene);
                Main.mainStage.show();
            }catch (IOException e){

            }
        });
    }

    private static void displayNotification(boolean shouldDisplay, EventHandler<ActionEvent> nextOperationHandler){
        if(shouldDisplay) {
            try {
                SystemTray tray = SystemTray.getSystemTray();

                Image image = Toolkit.getDefaultToolkit().createImage(FakeUpdate.class.getResource("/images/windows-update-icon.png"));

                TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip("System tray icon demo");
                tray.add(trayIcon);

                trayIcon.displayMessage("Wymagana aktualizacja", "Za chwilę komputer zostanie zaaktualizowany.", TrayIcon.MessageType.INFO);
                Wait w = new Wait(10000,true,a -> nextOperationHandler.handle(new ActionEvent()));
                trayIcon.addActionListener(e -> {
                    w.cancel();
                    nextOperationHandler.handle(new ActionEvent());
                });
            } catch (AWTException e) {

            }
        }else{
            nextOperationHandler.handle(new ActionEvent());
        }
    }
}

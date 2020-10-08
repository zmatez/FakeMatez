package fakeadmin.fakes.fakeUpdate;

import fakeadmin.Main;
import fakeadmin.dialog.time.TimeConfiguration;
import fakeadmin.dialog.updatepackets.UpdatePacketsConfiguration;
import fakeadmin.dialog.updatetexts.UpdateTextsConfiguration;
import fakeadmin.utils.Utils;
import fakeadmin.utils.Wait;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;

public class FakeUpdate {
    public static void run(Color backgroundColor, TimeConfiguration updateTimeConfiguration, UpdateTextsConfiguration updateTextsConfiguration, UpdatePacketsConfiguration updatePacketsConfiguration, boolean notification, int maxPercentage, boolean shouldLaunchAndWait) {
        Main.LOGGER.log("Running FakeUpdate");
        displayNotification(notification, e1 -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(FakeUpdate.class.getResource("/layout/restartlayout.fxml"));
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double w = screenSize.getWidth();
                double h = screenSize.getHeight();
                Parent root = fxmlLoader.load();
                FakeUpdateController controller = (FakeUpdateController) fxmlLoader.getController();

                Main.mainStage.setResizable(false);

                Main.mainScene = new Scene(root, w, h);
                Main.mainScene.getStylesheets().add(FakeUpdate.class.getResource("/css/stylesheet.css").toExternalForm());
                Main.mainStage.setScene(Main.mainScene);
                Main.mainStage.centerOnScreen();
                Main.mainStage.setFullScreenExitHint("");
                Main.mainStage.setFullScreenExitKeyCombination(new KeyCombination() {
                    @Override
                    public boolean match(KeyEvent event) {
                        return false;
                    }
                });

                Utils.addQuitHandler();

                Main.mainStage.setFullScreen(true);
                controller.start();
                Main.mainStage.show();
                controller.init(backgroundColor);

                new Wait(5000, true, a -> {
                    Platform.runLater(() -> {
                        controller.update(updateTimeConfiguration,updateTextsConfiguration,updatePacketsConfiguration,maxPercentage);
                    });
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void displayNotification(boolean shouldDisplay, EventHandler<ActionEvent> nextOperationHandler) {
        if (shouldDisplay) {
            try {
                SystemTray tray = SystemTray.getSystemTray();

                Image image = Toolkit.getDefaultToolkit().createImage(FakeUpdate.class.getResource("/images/windows-update-icon.png"));

                TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip("System tray icon demo");
                tray.add(trayIcon);

                trayIcon.displayMessage("Wymagana aktualizacja", "Za chwilę komputer zostanie zaaktualizowany.", TrayIcon.MessageType.INFO);
                Wait w = new Wait(7000, true, a -> {
                    Main.LOGGER.debug("Notification hidden");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            nextOperationHandler.handle(new ActionEvent());
                        }
                    });
                    //nextOperationHandler.handle(new ActionEvent());
                });
                trayIcon.addActionListener(e -> {
                    w.cancel();
                    nextOperationHandler.handle(new ActionEvent());
                });
            } catch (AWTException e) {

            }
        } else {
            nextOperationHandler.handle(new ActionEvent());
        }
    }
}

package fakeadmin.fakes.fakeUpdate;

import fakeadmin.Main;
import fakeadmin.animations.VisibilityTransition;
import fakeadmin.dialog.time.TimeConfiguration;
import fakeadmin.dialog.updatepackets.UpdatePacketsConfiguration;
import fakeadmin.dialog.updatetexts.UpdateTextsConfiguration;
import fakeadmin.utils.Utils;
import fakeadmin.utils.Wait;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeUpdateController {
    @FXML
    protected Pane root;
    @FXML
    protected Pane vbox;

    @FXML
    protected ImageView imageView;

    @FXML
    protected Label textLabel;

    protected Pane transitionPane;

    private double width, height;
    private Image screenshot;

    @FXML
    protected void initialize() {

    }

    protected void start() {
        screenshot = Utils.takeScreenshot();
        width = root.getWidth();
        height = root.getHeight();
        transitionPane = new Pane();
        transitionPane.setPrefSize(width, height);
        transitionPane.setBackground(new Background(new BackgroundImage(screenshot, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        transitionPane.toFront();
        root.getChildren().add(0, transitionPane);
    }

    protected void init(Color backgroundColor) {
        vbox.setPrefSize(width, height);
        root.setBackground(new Background(new BackgroundFill(backgroundColor, new CornerRadii(0), new Insets(0))));
        Image i = new Image(FakeUpdateController.class.getResource("/images/windows-progress-ring.gif").toExternalForm());
        imageView.setImage(i);
        textLabel.setText("Uruchamianie ponownie");
        transitionPane.toFront();
        VisibilityTransition transition = new VisibilityTransition(transitionPane, 250, 0, false, true);
    }

    private javax.swing.Timer updateTimer;
    private javax.swing.Timer packetTimer;

    protected void update(TimeConfiguration updateTimeConfiguration, UpdateTextsConfiguration updateTextsConfiguration, UpdatePacketsConfiguration updatePacketsConfiguration, int maxPercentage) {
        Main.LOGGER.debug("Update time: " + updateTimeConfiguration.getAllAsSeconds());
        if (updateTimeConfiguration.getAllAsSeconds() == 0) {
            textLabel.setText("Błąd aktualizacji.");
            return;
        }
        final int timePerPercent = (int) Math.floor((float) updateTimeConfiguration.getAllAsSeconds() / maxPercentage) * 1000;
        Main.LOGGER.debug("Per percent: " + timePerPercent + "ms");


        AtomicInteger percent = new AtomicInteger();
        AtomicInteger packet = new AtomicInteger();
        AtomicInteger randomnessToWait = new AtomicInteger(0);

        updateTimer = new Timer(timePerPercent, a -> {
            Platform.runLater(() -> {
                updatePercents(percent,packet,randomnessToWait,maxPercentage,updateTextsConfiguration,updatePacketsConfiguration);
            });
        });
        updateTimer.setRepeats(true);
        updateTimer.start();



        if (!updatePacketsConfiguration.getPackets().isEmpty()) {
            final int timePerPacket = (int) Math.floor((float) updateTimeConfiguration.getAllAsSeconds() / updatePacketsConfiguration.getPackets().size()) * 1000;
            packetTimer = new Timer(timePerPacket, a -> {
                Platform.runLater(() -> {
                    updatePackets(packet,updatePacketsConfiguration,true);
                });
            });
            packetTimer.setRepeats(true);
            packetTimer.start();
        }
        updatePercents(percent,packet,randomnessToWait,maxPercentage,updateTextsConfiguration,updatePacketsConfiguration);
    }

    private void updatePercents(AtomicInteger percent, AtomicInteger packet, AtomicInteger randomnessToWait, int maxPercentage, UpdateTextsConfiguration updateTextsConfiguration, UpdatePacketsConfiguration updatePacketsConfiguration){
        if(randomnessToWait.get()<=0) {
            if (percent.get() >= maxPercentage) {
                updateTimer.stop();
                restartAfterUpdate();
            } else {
                boolean alreadySet = false;
                String mainText = updateTextsConfiguration.getMainText().replace("<br>", "\n").replace("%s", percent + "%");
                if (updatePacketsConfiguration.getPackets().isEmpty()) {
                    mainText = mainText.replace("%pb", "").replace("%p", "");
                }else{
                    textLabel.setText(mainText);
                    updatePackets(packet,updatePacketsConfiguration,false);
                    alreadySet = true;
                }
                if(!alreadySet) {
                    textLabel.setText(mainText);
                }

                int randomAdd = Utils.rint(0, 1) == 0 ? 1 : Utils.rint(2, 7);
                if (percent.get() + randomAdd > maxPercentage) {
                    randomAdd = maxPercentage - percent.get();
                }
                randomnessToWait.set(randomAdd);
                percent.getAndAdd(randomAdd);
            }
        }else{
            randomnessToWait.getAndDecrement();
        }
    }

    private void updatePackets(AtomicInteger packet, UpdatePacketsConfiguration updatePacketsConfiguration, boolean increment){
        String mainText = textLabel.getText();
        if (updatePacketsConfiguration.getPackets().size() > packet.get()) {
            String currentPacket = updatePacketsConfiguration.getPackets().get(packet.get());
            mainText = mainText.replace("%pb", currentPacket + "\n").replace("%p", currentPacket);
        }

        textLabel.setText(mainText);

        if(increment) {
            packet.getAndIncrement();
        }
    }

    private void restartAfterUpdate() {
        if (packetTimer != null) {
            packetTimer.stop();
        }
        textLabel.setText("Uruchamianie ponownie");
        new Wait(5000, true, a -> {
            Platform.runLater(() -> {

            });
        });
    }
}

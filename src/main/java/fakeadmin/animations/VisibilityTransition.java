package fakeadmin.animations;

import fakeadmin.utils.Wait;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisibilityTransition {
    private Timer t;

    public VisibilityTransition(Node node, int msDelay, int msWait, boolean makeVisible, boolean remove) {
        if (makeVisible) {
            node.setOpacity(0);
        } else {
            node.setOpacity(1);
        }

        node.setVisible(true);

        t = new Timer(msWait, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (makeVisible) {
                    node.setVisible(true);
                    FadeTransition trans = new FadeTransition(Duration.millis(msDelay), node);
                    trans.setFromValue(0.0);
                    trans.setToValue(1.0);

                    // Play the Animation
                    trans.play();
                } else {
                    FadeTransition trans = new FadeTransition(Duration.millis(msDelay), node);
                    trans.setFromValue(1.0);
                    trans.setToValue(0.0);

                    // Play the Animation
                    trans.play();
                }
            }
        });
        t.setRepeats(false);
        t.start();
        if (!makeVisible)
            new Wait(msWait + msDelay + 1, true, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    node.setVisible(false);
                    if (remove) {
                        try {
                            ((Pane) node.getParent()).getChildren().remove(node);
                        } catch (Exception ex) {
                        }
                    }
                }
            });
    }
}
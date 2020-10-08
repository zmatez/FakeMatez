package fakeadmin.animations;

import com.jfoenix.transitions.JFXFillTransition;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class FillTransition {
    private int fDuration;
    private Region fRegion;
    private Color fNewColor,fOldColor;
    private JFXFillTransition transition;

    public FillTransition(int duration, Region region, Color newColor, Color oldColor){
        fDuration = duration;
        fRegion = region;
        fNewColor = newColor;
        fOldColor = oldColor;
        fill();
    }

    private void fill(){
        transition = new JFXFillTransition();
        transition.setDuration(new Duration(fDuration));
        transition.setRegion(fRegion);
        transition.setFromValue(fOldColor);
        transition.setToValue(fNewColor);
        transition.play();
    }

    public void stop(){
        transition.stop();
    }

    public JFXFillTransition getTransition(){
        return transition;
    }
}

package fakeadmin.utils;

import fakeadmin.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Utils {

    public static Image takeScreenshot() {
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);

            return SwingFXUtils.toFXImage(capture, null);
        } catch (Exception e) {
            return null;
        }
    }

    public static int rint(int min, int max) {
        if (min == max) {
            return min;
        }

        if (min >= max) {
            return max;
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String escapeCode = "";
    public static void addQuitHandler(){
        Main.mainScene.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if(key.isControlDown()){
                if(!escapeCode.isEmpty()){
                    Main.LOGGER.debug(escapeCode);
                    String character = escapeCode.substring(escapeCode.length() - 1);
                    if(!character.equals(key.getText())){
                        escapeCode += key.getText();
                    }
                    if(escapeCode.toLowerCase().equals("quit")){
                        Main.close();
                    }
                }else{
                    escapeCode += key.getText();
                }
            }else{
                escapeCode = "";
            }
        });
    }
}

package fakeadmin.utils;

import fakeadmin.Main;

import java.lang.reflect.InvocationTargetException;

public class SystemSettings {
    public static String MAIN_THEME_COLOR;

    public static void init() {
        Main.LOGGER.progress("Reading Windows Registry");
        try {
            MAIN_THEME_COLOR = readValue(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\DWM", "AccentColor");


        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Main.LOGGER.success("Windows Registry has been read");
    }

    public static String readString(int hkey, String key, String valueName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Main.LOGGER.debug("Reading from registry: " + (hkey == WinRegistry.HKEY_CURRENT_USER ? "HKEY_CURRENT_USER" : (hkey == WinRegistry.HKEY_LOCAL_MACHINE ? "HKEY_LOCAL_MACHINE" : "NONE")) + "\\" + key + " -> " + valueName);
        String output = WinRegistry.readString(hkey,key,valueName);
        Main.LOGGER.debug("Returned \"" + output + "\"");
        return output;
    }

    public static String readValue(int hkey, String key, String valueName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String s = hkey == WinRegistry.HKEY_CURRENT_USER ? "HKEY_CURRENT_USER" : (hkey == WinRegistry.HKEY_LOCAL_MACHINE ? "HKEY_LOCAL_MACHINE" : "NONE");
        Main.LOGGER.debug("Reading from registry: " + s + "\\" + key + " -> " + valueName);
        String output = ReadRegistry.readRegistry(s + "\\" + key, valueName);
        Main.LOGGER.debug("Returned \"" + output + "\"");
        return output;
    }
}

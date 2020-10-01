package fakeadmin.dialog.updatepackets;

import fakeadmin.dialog.updatetexts.UpdateTextsConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdatePacketsConfiguration {
    private ArrayList<String> packets;
    public UpdatePacketsConfiguration(String... packets){
        this.packets = new ArrayList<>(Arrays.asList(packets));
    }

    public UpdatePacketsConfiguration(ArrayList<String> packets){
        this.packets = packets;
    }

    public ArrayList<String> getPackets() {
        return packets;
    }

    @Override
    public String toString() {
        return "UpdatePacketsConfiguration{" +
                "packets=" + packets +
                '}';
    }

    public String toFormattedString(int maxVal) {
        int i = 0;
        String out = "";
        if(packets.isEmpty()){
            out = "Brak pakiet\u00F3w";
        }else {
            for (String packet : packets) {
                if (i < maxVal) {
                    out += packet + "; ";
                }
                i++;
            }
            if (packets.size() > maxVal) {
                out += "...";
            }
        }
        return out;
    }
}

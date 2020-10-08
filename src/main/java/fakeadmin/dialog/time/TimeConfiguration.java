package fakeadmin.dialog.time;

import fakeadmin.Main;

public class TimeConfiguration {
    private int hours;
    private int minutes;
    private int seconds;
    public TimeConfiguration(int hours, int minutes, int seconds){
        if(hours<0){
            this.hours=0;
        }else {
            this.hours = hours;
        }
        if(seconds<0){
            this.seconds=0;
        }else {
            if(seconds>=60){
                this.minutes += seconds / 60;
                this.seconds = seconds % 60;
            }else{
                this.seconds=seconds;
            }
        }
        if(minutes<0){
            this.minutes=0;
        }else {
            if(minutes>=60){
                this.hours += minutes / 60;
                this.minutes = minutes % 60;
            }else{
                this.minutes=minutes;
            }
        }
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getAllAsSeconds(){
        return (hours * 3600) + (minutes * 60) + seconds;
    }

    @Override
    public String toString() {
        return "TimeConfiguration{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                ", seconds=" + seconds +
                '}';
    }

    public String toFormattedString(boolean toWork) {
        String h = "", m = "", s = "";

        if(hours!=0){
            h = getValidSuffix("godzin",hours,toWork) + " ";
        }
        if(minutes!=0){
            m = getValidSuffix("minut",minutes,toWork) + " ";
        }
        if(seconds!=0){
            s = getValidSuffix("sekund",seconds,toWork);
        }
        return h + (!h.isEmpty() && (s.isEmpty() && !m.isEmpty()) ? "i " : (m.isEmpty() ? "" : h.isEmpty() ? "" : ", ")) + m + ((!h.isEmpty() || !m.isEmpty()) && !s.isEmpty() ? "i " : (s.isEmpty() ? "" : ", ")) + s;
    }

    private String getValidSuffix(String prefix, int time, boolean toWork){
        String ae = toWork ? "\u0119" : "a";
        String lastChar = (time + "").substring((time + "").length() - 1);
        String last2Char = "";
        if((time+"").length() >= 2){
            last2Char = (time + "").substring((time + "").length() - 2,(time + "").length() - 1);
        }
        if(time==1){
            return time + " " + prefix + ae;
        }else if((time > 1 && time < 5) || ((lastChar.equals("2") || lastChar.equals("3") || lastChar.equals("4")) && !last2Char.equals("1") ) ){
            return time + " " + prefix + "y";
        }else{
            return time + " " + prefix;
        }
    }
}

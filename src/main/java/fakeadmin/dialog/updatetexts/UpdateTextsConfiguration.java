package fakeadmin.dialog.updatetexts;

public class UpdateTextsConfiguration {
    private String mainText = "";
    private String bottomText = "";

    public UpdateTextsConfiguration(String mainText, String bottomText){
        this.mainText=mainText;
        this.bottomText=bottomText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public String getMainText() {
        return mainText;
    }

    @Override
    public String toString() {
        return "UpdateTextsConfiguration{" +
                "mainText='" + mainText + '\'' +
                ", bottomText='" + bottomText + '\'' +
                '}';
    }

    public String toFormattedString() {
        return "Tekst g\u0142\u00F3wny: " + (mainText.length() > 35 ? (mainText.substring(0,32) + "...") : mainText) + "\nTekst dolny: " + (bottomText.isEmpty() ? "brak" : (bottomText.length() > 35 ? (bottomText.substring(0,32) + "...") : bottomText));
    }
}

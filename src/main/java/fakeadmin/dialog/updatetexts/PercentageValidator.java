package fakeadmin.dialog.updatetexts;

import com.jfoenix.validation.base.ValidatorBase;
import fakeadmin.Main;
import javafx.scene.control.TextInputControl;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.validation.Validator;
import java.io.IOException;

public class PercentageValidator extends ValidatorBase {

    public PercentageValidator() {
        setMessage("Value must contain only one \"%s\" word");
    }

    public PercentageValidator(String message) {
        super(message);
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText();
        int count = text.length() - text.replaceAll("%s","").length();
        if(count==2){
            hasErrors.set(false);
        }else{
            hasErrors.set(true);
        }
    }
}

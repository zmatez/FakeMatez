package fakeadmin.dialog.updatetexts;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTooltip;
import fakeadmin.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import javax.swing.text.IconView;
import java.util.Optional;

public class UpdateTextsController {
    @FXML
    protected JFXButton saveButton;
    @FXML
    protected JFXButton cancelButton;

    @FXML
    protected JFXTextField mainTextField;
    @FXML
    protected JFXTextField bottomTextField;
    @FXML
    protected Label icon;

    @FXML
    protected void initialize(){
        Tooltip tooltip = new Tooltip("%s - wy\u015Bwietla % aktualizacji (tylko w g\u0142\u00F3wnym)\n%p - wy\u015Bwietla pakiet (o ile jaki\u015B istnieje)\n%pb - wy\u015Bwietla pakiet (o ile jaki\u015B istnieje, je\u015Bli tak dodaje <br> na koniec)\n<br> - przej\u015Bcie do nowej linii");
        icon.setTooltip(tooltip);
        initField(mainTextField);
    }

    protected void initButtons(Dialog<Optional<UpdateTextsConfiguration>> dialog){
        saveButton.setOnAction(event -> {
            if((mainTextField.getActiveValidator()==null || !mainTextField.getActiveValidator().getHasErrors())){
                dialog.setResult(Optional.of(new UpdateTextsConfiguration(mainTextField.getText(), bottomTextField.getText())));
            }else{
                AlertUtils.showInfoAlert("Niew\u0142a\u015Bciwe dane w polu \"Tekst g\u0142\u00F3wny\"","Tekst musi zawiera\u0107 jeden wyraz \"%s\" (wy\u015Bwietla % aktualizacji)",a -> {});
            }
        });

        cancelButton.setOnAction(event -> {
            dialog.setResult(Optional.empty());
            dialog.close();
        });
    }

    private void initField(JFXTextField field){
        PercentageValidator validator = new PercentageValidator();
        validator.setMessage("Tekst musi zawiera\u0107 jeden wyraz \"%s\" (wy\u015Bwietla % aktualizacji)");
        field.getValidators().add(validator);

        field.textProperty().addListener((change, oldVal, newVal) -> {
            field.validate();

            if((mainTextField.getActiveValidator()==null || !mainTextField.getActiveValidator().getHasErrors())){
                saveButton.setDisable(false);
            }else{
                saveButton.setDisable(true);
            }
        });

    }

    protected void setStartCfg(UpdateTextsConfiguration cfg){
        mainTextField.setText(cfg.getMainText());
        bottomTextField.setText(cfg.getBottomText());
    }
}

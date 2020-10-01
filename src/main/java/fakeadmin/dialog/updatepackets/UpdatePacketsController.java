package fakeadmin.dialog.updatepackets;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import fakeadmin.utils.AlertUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;

import java.util.Optional;

public class UpdatePacketsController {
    @FXML
    protected JFXButton saveButton;
    @FXML
    protected JFXButton cancelButton;

    @FXML
    protected JFXButton addButton;
    @FXML
    protected JFXButton removeButton;
    @FXML
    protected JFXButton editButton;

    @FXML
    protected JFXListView<String> packetView;

    @FXML
    protected void initialize() {

    }

    protected void initButtons(Dialog<Optional<UpdatePacketsConfiguration>> dialog) {
        addButton.setOnAction(event -> {
            Optional<String> packetOptional = AlertUtils.showTextDialog("Dodaj pakiet", "", "", e -> {
            }, e -> {
            });
            if (packetOptional.isPresent()) {
                String packet = packetOptional.get();
                packetView.getItems().add(packet);
            }
        });

        removeButton.setOnAction(event -> {
            ObservableList<String> list = packetView.getSelectionModel().getSelectedItems();
            if (list.isEmpty()) {
                AlertUtils.showInfoAlert("Nie zaznaczono pakietu", "Kliknij na pakiet aby go zaznaczy\u0107 i dopiero wtedy kliknij przycisk.", e -> {});
            } else {
                if (AlertUtils.showBooleanConfirmAlert("Potwierdzenie usuni\u0119cia", "Czy napewno chcesz usun\u0105\u0107 wybrany pakiet?", e -> {}, e -> {})) {
                    String packet = list.get(0);
                    packetView.getItems().remove(packet);
                }
            }
        });

        editButton.setOnAction(event -> {
            ObservableList<String> list = packetView.getSelectionModel().getSelectedItems();
            if (list.isEmpty()) {
                AlertUtils.showInfoAlert("Nie zaznaczono pakietu", "Kliknij na pakiet aby go zaznaczy\u0107 i dopiero wtedy kliknij przycisk.", e -> { });
            } else {
                String oldPacket = list.get(0);
                Optional<String> packetOptional = AlertUtils.showTextDialog("Edytuj pakiet", "", oldPacket, e -> { }, e -> { });
                if (packetOptional.isPresent()) {
                    String packet = packetOptional.get();
                    int index = packetView.getItems().indexOf(oldPacket);
                    packetView.getItems().remove(oldPacket);
                    packetView.getItems().add(index,packet);
                    packetView.getSelectionModel().select(index);
                }
            }
        });

        saveButton.setOnAction(event -> {
            if (packetView.getItems().isEmpty()) {
                dialog.setResult(Optional.empty());
            } else {
                dialog.setResult(Optional.of(new UpdatePacketsConfiguration(packetView.getItems().toArray(new String[]{}))));
            }
        });

        cancelButton.setOnAction(event -> {
            dialog.setResult(Optional.empty());
            dialog.close();
        });
    }

    protected void setStartCfg(UpdatePacketsConfiguration cfg) {
        for (String packet : cfg.getPackets()) {
            packetView.getItems().add(packet);
        }
    }
}

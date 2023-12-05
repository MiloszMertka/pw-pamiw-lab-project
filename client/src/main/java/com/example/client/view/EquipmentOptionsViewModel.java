package com.example.client.view;

import com.example.client.Views;
import com.example.client.model.EquipmentOption;
import com.example.client.service.EquipmentOptionService;
import com.google.inject.Inject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EquipmentOptionsViewModel implements Initializable {

    private static final int ROWS_PER_PAGE = 10;
    private final EquipmentOptionService equipmentOptionService;
    private final ObservableList<EquipmentOption> equipmentOptions = FXCollections.observableArrayList();

    @FXML
    private TableView<EquipmentOption> table;

    @FXML
    private Pagination pagination;

    @Inject
    public EquipmentOptionsViewModel(EquipmentOptionService equipmentOptionService) {
        this.equipmentOptionService = equipmentOptionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTableColumns();
        equipmentOptions.addAll(equipmentOptionService.getAllEquipmentOptions());
        configurePagination();
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.APP_VIEW.loadScene(stage);
    }

    @FXML
    private void onCreateNewEquipmentOptionButtonClick(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.EQUIPMENT_OPTION_FORM_VIEW.loadScene(stage);
    }

    private void createTableColumns() {
        createDeleteTableColumn();
        createEditTableColumn();
        TableColumn<EquipmentOption, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        table.getColumns().add(nameColumn);
    }

    private void createDeleteTableColumn() {
        TableColumn<EquipmentOption, EquipmentOption> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        deleteColumn.setCellFactory(param -> createDeleteButtonCell());
        table.getColumns().add(deleteColumn);
    }

    private void createEditTableColumn() {
        TableColumn<EquipmentOption, EquipmentOption> editColumn = new TableColumn<>("Edit");
        editColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        editColumn.setCellFactory(param -> createEditButtonCell());
        table.getColumns().add(editColumn);
    }

    private TableCell<EquipmentOption, EquipmentOption> createDeleteButtonCell() {
        return new TableCell<>() {
            private final Button deleteButton = new Button("Delete equipment");

            @Override
            protected void updateItem(EquipmentOption equipmentOption, boolean empty) {
                super.updateItem(equipmentOption, empty);

                if (equipmentOption == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> onDeleteEquipmentOptionButtonClick(equipmentOption));
            }
        };
    }

    private TableCell<EquipmentOption, EquipmentOption> createEditButtonCell() {
        return new TableCell<>() {
            private final Button editButton = new Button("Edit equipment");

            @Override
            protected void updateItem(EquipmentOption equipmentOption, boolean empty) {
                super.updateItem(equipmentOption, empty);

                if (equipmentOption == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(editButton);
                editButton.setOnAction(event -> onEditEquipmentOptionButtonClick(event, equipmentOption));
            }
        };
    }

    private void onDeleteEquipmentOptionButtonClick(EquipmentOption equipmentOption) {
        equipmentOptionService.deleteEquipmentOption(equipmentOption.id());
        equipmentOptions.remove(equipmentOption);
        configurePagination();
    }

    private void onEditEquipmentOptionButtonClick(ActionEvent event, EquipmentOption equipmentOption) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.EQUIPMENT_OPTION_FORM_VIEW.loadScene(stage, equipmentOption);
    }

    private void configurePagination() {
        final var pageCount = (int) Math.ceil((double) equipmentOptions.size() / ROWS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(Integer pageIndex) {
        final var fromIndex = pageIndex * ROWS_PER_PAGE;
        final var toIndex = Math.min(fromIndex + ROWS_PER_PAGE, equipmentOptions.size());
        table.setItems(FXCollections.observableArrayList(equipmentOptions.subList(fromIndex, toIndex)));
        final var vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(table);
        return vBox;
    }

}

package com.example.client.view;

import com.example.client.Views;
import com.example.client.model.Engine;
import com.example.client.service.EngineService;
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

public class EnginesViewModel implements Initializable {

    private static final int ROWS_PER_PAGE = 10;
    private final EngineService engineService;
    private final ObservableList<Engine> engines = FXCollections.observableArrayList();

    @FXML
    private TableView<Engine> table;

    @FXML
    private Pagination pagination;

    @Inject
    public EnginesViewModel(EngineService engineService) {
        this.engineService = engineService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTableColumns();
        engines.addAll(engineService.getAllEngines());
        configurePagination();
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.APP_VIEW.loadScene(stage);
    }

    @FXML
    private void onCreateNewEngineButtonClick(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.ENGINE_FORM_VIEW.loadScene(stage);
    }

    private void createTableColumns() {
        createDeleteTableColumn();
        createEditTableColumn();
        TableColumn<Engine, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        TableColumn<Engine, String> horsePowerColumn = new TableColumn<>("Horse Power");
        horsePowerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().horsePower().toString()));
        table.getColumns().addAll(nameColumn, horsePowerColumn);
    }

    private void createDeleteTableColumn() {
        TableColumn<Engine, Engine> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        deleteColumn.setCellFactory(param -> createDeleteButtonCell());
        table.getColumns().add(deleteColumn);
    }

    private void createEditTableColumn() {
        TableColumn<Engine, Engine> editColumn = new TableColumn<>("Edit");
        editColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        editColumn.setCellFactory(param -> createEditButtonCell());
        table.getColumns().add(editColumn);
    }

    private TableCell<Engine, Engine> createDeleteButtonCell() {
        return new TableCell<>() {
            private final Button deleteButton = new Button("Delete engine");

            @Override
            protected void updateItem(Engine engine, boolean empty) {
                super.updateItem(engine, empty);

                if (engine == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> onDeleteEngineButtonClick(engine));
            }
        };
    }

    private TableCell<Engine, Engine> createEditButtonCell() {
        return new TableCell<>() {
            private final Button editButton = new Button("Edit engine");

            @Override
            protected void updateItem(Engine engine, boolean empty) {
                super.updateItem(engine, empty);

                if (engine == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(editButton);
                editButton.setOnAction(event -> onEditEngineButtonClick(event, engine));
            }
        };
    }

    private void onDeleteEngineButtonClick(Engine engine) {
        engineService.deleteEngine(engine.id());
        engines.remove(engine);
        configurePagination();
    }

    private void onEditEngineButtonClick(ActionEvent event, Engine engine) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.ENGINE_FORM_VIEW.loadScene(stage, engine);
    }

    private void configurePagination() {
        final var pageCount = (int) Math.ceil((double) engines.size() / ROWS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(Integer pageIndex) {
        final var fromIndex = pageIndex * ROWS_PER_PAGE;
        final var toIndex = Math.min(fromIndex + ROWS_PER_PAGE, engines.size());
        table.setItems(FXCollections.observableArrayList(engines.subList(fromIndex, toIndex)));
        final var vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(table);
        return vBox;
    }

}

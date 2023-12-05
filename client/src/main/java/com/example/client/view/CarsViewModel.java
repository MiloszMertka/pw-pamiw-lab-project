package com.example.client.view;

import com.example.client.Views;
import com.example.client.model.Car;
import com.example.client.model.EquipmentOption;
import com.example.client.service.CarService;
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

public class CarsViewModel implements Initializable {

    private static final int ROWS_PER_PAGE = 10;
    private final CarService carService;
    private final ObservableList<Car> cars = FXCollections.observableArrayList();

    @FXML
    private TableView<Car> table;

    @FXML
    private Pagination pagination;

    @Inject
    public CarsViewModel(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTableColumns();
        cars.addAll(carService.getAllCars());
        configurePagination();
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.APP_VIEW.loadScene(stage);
    }

    @FXML
    private void onCreateNewCarButtonClick(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.CAR_FORM_VIEW.loadScene(stage);
    }

    private void createTableColumns() {
        createDeleteTableColumn();
        createEditTableColumn();
        TableColumn<Car, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        TableColumn<Car, String> colorColumn = new TableColumn<>("Color");
        colorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().color()));
        TableColumn<Car, String> engineColumn = new TableColumn<>("Engine");
        engineColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().engine().name() + " " + cellData.getValue().engine().horsePower() + " HP"));
        TableColumn<Car, String> equipmentOptionsColumn = new TableColumn<>("Equipment");
        equipmentOptionsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.join(", ", cellData.getValue().equipmentOptions().stream().map(EquipmentOption::name).toList())));
        table.getColumns().addAll(nameColumn, colorColumn, engineColumn, equipmentOptionsColumn);
    }

    private void createDeleteTableColumn() {
        TableColumn<Car, Car> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        deleteColumn.setCellFactory(param -> createDeleteButtonCell());
        table.getColumns().add(deleteColumn);
    }

    private void createEditTableColumn() {
        TableColumn<Car, Car> editColumn = new TableColumn<>("Edit");
        editColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        editColumn.setCellFactory(param -> createEditButtonCell());
        table.getColumns().add(editColumn);
    }

    private TableCell<Car, Car> createDeleteButtonCell() {
        return new TableCell<>() {
            private final Button deleteButton = new Button("Delete car");

            @Override
            protected void updateItem(Car car, boolean empty) {
                super.updateItem(car, empty);

                if (car == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> onDeleteCarButtonClick(car));
            }
        };
    }

    private TableCell<Car, Car> createEditButtonCell() {
        return new TableCell<>() {
            private final Button editButton = new Button("Edit car");

            @Override
            protected void updateItem(Car car, boolean empty) {
                super.updateItem(car, empty);

                if (car == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(editButton);
                editButton.setOnAction(event -> onEditCarButtonClick(event, car));
            }
        };
    }

    private void onDeleteCarButtonClick(Car car) {
        carService.deleteCar(car.id());
        cars.remove(car);
        configurePagination();
    }

    private void onEditCarButtonClick(ActionEvent event, Car car) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.CAR_FORM_VIEW.loadScene(stage, car);
    }

    private void configurePagination() {
        final var pageCount = (int) Math.ceil((double) cars.size() / ROWS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(Integer pageIndex) {
        final var fromIndex = pageIndex * ROWS_PER_PAGE;
        final var toIndex = Math.min(fromIndex + ROWS_PER_PAGE, cars.size());
        table.setItems(FXCollections.observableArrayList(cars.subList(fromIndex, toIndex)));
        final var vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(table);
        return vBox;
    }

}

package com.example.client.view;

import com.example.client.Views;
import com.example.client.service.WeatherService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WeatherViewModel {

    private static final String TEMPERATURE_UNIT = " st. C";
    private final WeatherService weatherService;

    @FXML
    private TextField cityField;

    @FXML
    private Label currentTemperatureLabel;

    @FXML
    private Label pastTemperatureLabel;

    @FXML
    private Label pastDayTemperatureLabel;

    @FXML
    private Label next12HoursTemperatureLabel;

    @FXML
    private Label nextDayTemperatureLabel;

    @FXML
    private Label next5DaysTemperatureLabel;

    @Inject
    public WeatherViewModel(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @FXML
    private void onSearchButtonClick() {
        final var city = cityField.getText();
        final var temperatures = getTemperaturesForCity(city);
        setTemperatureLabels(temperatures);
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent actionEvent) {
        final var source = (Node) actionEvent.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.APP_VIEW.loadScene(stage);
    }

    private TemperatureViewModel getTemperaturesForCity(String city) {
        final var location = weatherService.getLocationByCity(city);
        final var currentTemperature = weatherService.getTemperatureByLocation(location);
        final var pastTemperature = weatherService.getPastTemperatureByLocation(location);
        final var pastDayTemperature = weatherService.getPastDayTemperatureByLocation(location);
        final var next12HoursTemperature = weatherService.getNext12HoursTemperatureByLocation(location);
        final var nextDayTemperature = weatherService.getNextDayTemperatureByLocation(location);
        final var next5DaysTemperature = weatherService.getNext5DaysTemperatureByLocation(location);
        return new TemperatureViewModel(currentTemperature.value() + TEMPERATURE_UNIT,
                pastTemperature.value() + TEMPERATURE_UNIT,
                pastDayTemperature.value() + TEMPERATURE_UNIT,
                next12HoursTemperature.value() + TEMPERATURE_UNIT,
                nextDayTemperature.value() + TEMPERATURE_UNIT,
                next5DaysTemperature.value() + TEMPERATURE_UNIT);
    }

    private void setTemperatureLabels(TemperatureViewModel temperatures) {
        currentTemperatureLabel.setText(temperatures.currentTemperature());
        pastTemperatureLabel.setText(temperatures.pastTemperature());
        pastDayTemperatureLabel.setText(temperatures.pastDayTemperature());
        next12HoursTemperatureLabel.setText(temperatures.next12HoursTemperature());
        nextDayTemperatureLabel.setText(temperatures.nextDayTemperature());
        next5DaysTemperatureLabel.setText(temperatures.next5DaysTemperature());
    }

}
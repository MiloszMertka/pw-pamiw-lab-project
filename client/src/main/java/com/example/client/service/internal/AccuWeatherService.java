package com.example.client.service.internal;

import com.example.client.model.Location;
import com.example.client.model.Temperature;
import com.example.client.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class AccuWeatherService implements WeatherService {

    private static final String BASE_URL = "http://dataservice.accuweather.com";
    private static final String API_KEY = "AAL3XyBGuXrBPAWLQyZHDg8tSGrrallb";
    private static final String LANGUAGE = "pl";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Location getLocationByCity(String city) {
        final var url = BASE_URL + "/locations/v1/cities/autocomplete?apikey=" + API_KEY + "&q=" + city + "&language=" + LANGUAGE;
        final var json = getJsonResponseFromApi(url);
        final var locationKey = json.get(0).get("Key").asText();
        return new Location(locationKey);
    }

    @Override
    public Temperature getTemperatureByLocation(Location location) {
        final var url = BASE_URL + "/currentconditions/v1/" + location.key() + "?apikey=" + API_KEY + "&language=" + LANGUAGE;
        final var json = getJsonResponseFromApi(url);
        final var value = json.get(0).get("Temperature").get("Metric").get("Value").asDouble();
        return new Temperature(value);
    }

    @Override
    public Temperature getPastTemperatureByLocation(Location location) {
        final var url = BASE_URL + "/currentconditions/v1/" + location.key() + "/historical?apikey=" + API_KEY + "&language=" + LANGUAGE;
        final var json = getJsonResponseFromApi(url);
        final var value = json.get(5).get("Temperature").get("Metric").get("Value").asDouble();
        return new Temperature(value);
    }

    @Override
    public Temperature getPastDayTemperatureByLocation(Location location) {
        final var url = BASE_URL + "/currentconditions/v1/" + location.key() + "/historical/24?apikey=" + API_KEY + "&language=" + LANGUAGE;
        final var json = getJsonResponseFromApi(url);
        final var value = json.get(23).get("Temperature").get("Metric").get("Value").asDouble();
        return new Temperature(value);
    }

    @Override
    public Temperature getNext12HoursTemperatureByLocation(Location location) {
        final var url = BASE_URL + "/forecasts/v1/hourly/12hour/" + location.key() + "?apikey=" + API_KEY + "&language=" + LANGUAGE + "&metric=true";
        final var json = getJsonResponseFromApi(url);
        final var value = json.get(11).get("Temperature").get("Value").asDouble();
        return new Temperature(value);
    }

    @Override
    public Temperature getNextDayTemperatureByLocation(Location location) {
        final var url = BASE_URL + "/forecasts/v1/daily/1day/" + location.key() + "?apikey=" + API_KEY + "&language=" + LANGUAGE + "&metric=true";
        final var json = getJsonResponseFromApi(url);
        final var temperatureJson = json.get("DailyForecasts").get(0).get("Temperature");
        final var minimumTemperature = temperatureJson.get("Minimum").get("Value").asDouble();
        final var maximumTemperature = temperatureJson.get("Maximum").get("Value").asDouble();
        final var averageTemperature = (minimumTemperature + maximumTemperature) / 2;
        return new Temperature(averageTemperature);
    }

    @Override
    public Temperature getNext5DaysTemperatureByLocation(Location location) {
        final var url = BASE_URL + "/forecasts/v1/daily/5day/" + location.key() + "?apikey=" + API_KEY + "&language=" + LANGUAGE + "&metric=true";
        final var json = getJsonResponseFromApi(url);
        final var temperatureJson = json.get("DailyForecasts").get(4).get("Temperature");
        final var minimumTemperature = temperatureJson.get("Minimum").get("Value").asDouble();
        final var maximumTemperature = temperatureJson.get("Maximum").get("Value").asDouble();
        final var averageTemperature = (minimumTemperature + maximumTemperature) / 2;
        return new Temperature(averageTemperature);
    }

    private JsonNode getJsonResponseFromApi(String url) {
        try {
            final var uri = new URI(url);
            final var getLocationKeyRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            final var response = httpClient.send(getLocationKeyRequest, BodyHandlers.ofString());
            final var body = response.body();
            return objectMapper.readTree(body);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}

package com.example.client.service;

import com.example.client.model.Location;
import com.example.client.model.Temperature;

public interface WeatherService {

    Location getLocationByCity(String city);

    Temperature getTemperatureByLocation(Location location);

    Temperature getPastTemperatureByLocation(Location location);

    Temperature getPastDayTemperatureByLocation(Location location);

    Temperature getNext12HoursTemperatureByLocation(Location location);

    Temperature getNextDayTemperatureByLocation(Location location);

    Temperature getNext5DaysTemperatureByLocation(Location location);

}

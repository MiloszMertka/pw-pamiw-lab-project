package com.example.client;

import com.example.client.service.*;
import com.example.client.service.internal.*;
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(WeatherService.class).to(AccuWeatherService.class);
        bind(CarService.class).to(CarServiceImpl.class);
        bind(EngineService.class).to(EngineServiceImpl.class);
        bind(EquipmentOptionService.class).to(EquipmentOptionServiceImpl.class);
        bind(AuthService.class).to(AuthServiceImpl.class);
        bind(AuthStateService.class).to(AuthStateServiceImpl.class);
    }

}

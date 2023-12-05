package com.example.client.service.internal;

import com.example.client.Endpoints;
import com.example.client.model.Car;
import com.example.client.service.AuthStateService;
import com.example.client.service.CarService;
import com.google.inject.Inject;

import java.util.List;

public class CarServiceImpl implements CarService {

    private static final String CARS_ENDPOINT = Endpoints.CARS.getEndpoint();
    private final CrudService<Car> crudService;

    @Inject
    public CarServiceImpl(AuthStateService authStateService) {
        crudService = new CrudService<>(authStateService.getJwtToken());
    }

    @Override
    public List<Car> getAllCars() {
        return crudService.getAll(CARS_ENDPOINT, Car.class);
    }

    @Override
    public void createCar(Car car) {
        crudService.create(CARS_ENDPOINT, car);
    }

    @Override
    public void updateCar(Long id, Car car) {
        crudService.update(CARS_ENDPOINT + "/" + id, car);
    }

    @Override
    public void deleteCar(Long id) {
        crudService.delete(CARS_ENDPOINT + "/" + id);
    }

}

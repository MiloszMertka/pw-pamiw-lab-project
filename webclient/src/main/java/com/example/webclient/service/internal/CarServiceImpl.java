package com.example.webclient.service.internal;

import com.example.webclient.model.Car;
import com.example.webclient.service.CarService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CrudService<Car> crudService;

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.endpoints.cars}")
    private String carsEndpoint;

    @PostConstruct
    private void init() {
        crudService = new CrudService<>(baseUrl);
    }

    @Override
    public List<Car> getAllCars() {
        return crudService.getAll(carsEndpoint, Car.class);
    }

    @Override
    public void createCar(Car car) {
        crudService.create(carsEndpoint, car);
    }

    @Override
    public void updateCar(Long id, Car car) {
        crudService.update(carsEndpoint + "/" + id, car);
    }

    @Override
    public void deleteCar(Long id) {
        crudService.delete(carsEndpoint + "/" + id);
    }

}

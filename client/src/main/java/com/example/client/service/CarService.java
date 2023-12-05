package com.example.client.service;

import com.example.client.model.Car;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();

    void createCar(Car car);

    void updateCar(Long id, Car car);

    void deleteCar(Long id);

}

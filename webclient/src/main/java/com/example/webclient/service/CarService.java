package com.example.webclient.service;

import com.example.webclient.model.Car;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();

    void createCar(Car car);

    void updateCar(Long id, Car car);

    void deleteCar(Long id);

}

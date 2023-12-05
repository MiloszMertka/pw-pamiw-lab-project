package com.example.api.service.internal;

import com.example.api.dto.CarDto;
import com.example.api.mapper.CarMapper;
import com.example.api.model.Car;
import com.example.api.repository.CarRepository;
import com.example.api.service.CarUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
class CarService implements CarUseCases {

    private static final String CAR_NOT_FOUND_ERROR_MESSAGE = "Car not found";
    private static final String CAR_ALREADY_EXISTS_ERROR_MESSAGE = "Car already exists";
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarDto> getAllCars() {
        final var cars = carRepository.findAll();
        return cars.stream()
                .map(carMapper::mapCarToCarDto)
                .toList();
    }

    @Override
    public CarDto getCar(Long id) {
        final var car = getCarById(id);
        return carMapper.mapCarToCarDto(car);
    }

    @Override
    public void createCar(CarDto carDto) {
        final var car = carMapper.mapCarDtoToCar(carDto);
        checkIfCarAlreadyExists(car);
        carRepository.save(car);
    }

    @Override
    public void updateCar(Long id, CarDto carDto) {
        final var car = getCarById(id);
        carMapper.updateCarFromCarDto(car, carDto);
        carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        final var car = getCarById(id);
        carRepository.delete(car);
    }

    private Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(CAR_NOT_FOUND_ERROR_MESSAGE));
    }

    private void checkIfCarAlreadyExists(Car car) {
        if (carRepository.existsByName(car.getName())) {
            throw new IllegalStateException(CAR_ALREADY_EXISTS_ERROR_MESSAGE);
        }
    }

}

package com.example.api.mapper;

import com.example.api.dto.CarDto;
import com.example.api.model.Car;

public interface CarMapper {

    CarDto mapCarToCarDto(Car car);

    Car mapCarDtoToCar(CarDto carDto);

    void updateCarFromCarDto(Car car, CarDto carDto);

}

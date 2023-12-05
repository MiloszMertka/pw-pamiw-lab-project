package com.example.webclient.controller;

import com.example.webclient.model.Car;
import com.example.webclient.service.CarService;
import com.example.webclient.service.EngineService;
import com.example.webclient.service.EquipmentOptionService;
import com.example.webclient.view.CarForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final EngineService engineService;
    private final EquipmentOptionService equipmentOptionService;

    @GetMapping
    public String carList(Model model) {
        final var cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "car-list";
    }

    @GetMapping("/create")
    public String createCarForm(Model model) {
        final var engines = engineService.getAllEngines();
        final var equipmentOptions = equipmentOptionService.getAllEquipmentOptions();
        model.addAttribute("engines", engines);
        model.addAttribute("equipmentOptions", equipmentOptions);
        model.addAttribute("carForm", new CarForm());
        return "car-form";
    }

    @GetMapping("/edit/{id}")
    public String editCarForm(Model model, @PathVariable Long id) {
        final var engines = engineService.getAllEngines();
        final var equipmentOptions = equipmentOptionService.getAllEquipmentOptions();
        final var car = carService.getAllCars().stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("engines", engines);
        model.addAttribute("equipmentOptions", equipmentOptions);
        model.addAttribute("carForm", new CarForm(car));
        return "car-form";
    }

    @PostMapping("/create")
    public String createCar(@ModelAttribute("carForm") CarForm carForm) {
        final var car = createCarFromCarForm(carForm);
        carService.createCar(car);
        return "redirect:/cars";
    }

    @PutMapping("/update/{id}")
    public String updateCar(@PathVariable Long id, @ModelAttribute("carForm") CarForm carForm) {
        final var car = createCarFromCarForm(carForm);
        car.setId(id);
        carService.updateCar(id, car);
        return "redirect:/cars";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/cars";
    }

    private Car createCarFromCarForm(CarForm carForm) {
        final var engine = engineService.getAllEngines().stream().filter(e -> e.getId().equals(carForm.getEngineId())).findFirst().orElseThrow();
        final var equipmentOptions = equipmentOptionService.getAllEquipmentOptions().stream().filter(e -> carForm.getEquipmentOptionsIds().contains(e.getId())).toList();
        Car car = new Car();
        car.setName(carForm.getName());
        car.setColor(carForm.getColor());
        car.setEngine(engine);
        car.setEquipmentOptions(equipmentOptions);
        return car;
    }

}

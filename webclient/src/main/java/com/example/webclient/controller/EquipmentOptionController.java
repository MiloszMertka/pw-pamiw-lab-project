package com.example.webclient.controller;

import com.example.webclient.model.EquipmentOption;
import com.example.webclient.service.EquipmentOptionService;
import com.example.webclient.view.EquipmentOptionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipment-options")
@RequiredArgsConstructor
public class EquipmentOptionController {

    private final EquipmentOptionService equipmentOptionService;

    @GetMapping
    public String equipmentOptionList(Model model) {
        final var equipmentOptions = equipmentOptionService.getAllEquipmentOptions();
        model.addAttribute("equipmentOptions", equipmentOptions);
        return "equipment-option-list";
    }

    @GetMapping("/create")
    public String createEquipmentOptionForm(Model model) {
        model.addAttribute("equipmentOptionForm", new EquipmentOptionForm());
        return "equipment-option-form";
    }

    @GetMapping("/edit/{id}")
    public String editEquipmentOptionForm(Model model, @PathVariable Long id) {
        final var equipmentOption = equipmentOptionService.getAllEquipmentOptions().stream().filter(e -> e.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("equipmentOptionForm", new EquipmentOptionForm(equipmentOption));
        return "equipment-option-form";
    }

    @PostMapping("/create")
    public String createEquipmentOption(@ModelAttribute("equipmentOptionForm") EquipmentOptionForm equipmentOptionForm) {
        final var equipmentOption = createEquipmentOptionFromEquipmentOptionForm(equipmentOptionForm);
        equipmentOptionService.createEquipmentOption(equipmentOption);
        return "redirect:/equipment-options";
    }

    @PutMapping("/update/{id}")
    public String updateEquipmentOption(@PathVariable Long id, @ModelAttribute("equipmentOptionForm") EquipmentOptionForm equipmentOptionForm) {
        final var equipmentOption = createEquipmentOptionFromEquipmentOptionForm(equipmentOptionForm);
        equipmentOption.setId(id);
        equipmentOptionService.updateEquipmentOption(id, equipmentOption);
        return "redirect:/equipment-options";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEquipmentOption(@PathVariable Long id) {
        equipmentOptionService.deleteEquipmentOption(id);
        return "redirect:/equipment-options";
    }

    private EquipmentOption createEquipmentOptionFromEquipmentOptionForm(EquipmentOptionForm equipmentOptionForm) {
        final var equipmentOption = new EquipmentOption();
        equipmentOption.setName(equipmentOptionForm.getName());
        return equipmentOption;
    }

}

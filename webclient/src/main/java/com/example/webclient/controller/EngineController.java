package com.example.webclient.controller;

import com.example.webclient.model.Engine;
import com.example.webclient.service.EngineService;
import com.example.webclient.view.EngineForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/engines")
@RequiredArgsConstructor
public class EngineController {

    private final EngineService engineService;

    @GetMapping
    public String engineList(Model model) {
        final var engines = engineService.getAllEngines();
        model.addAttribute("engines", engines);
        return "engine-list";
    }

    @GetMapping("/create")
    public String createEngineForm(Model model) {
        model.addAttribute("engineForm", new EngineForm());
        return "engine-form";
    }

    @GetMapping("/edit/{id}")
    public String editEngineForm(Model model, @PathVariable Long id) {
        final var engine = engineService.getAllEngines().stream().filter(e -> e.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("engineForm", new EngineForm(engine));
        return "engine-form";
    }

    @PostMapping("/create")
    public String createEngine(@ModelAttribute("engineForm") EngineForm engineForm) {
        final var engine = createEngineFromEngineForm(engineForm);
        engineService.createEngine(engine);
        return "redirect:/engines";
    }

    @PutMapping("/update/{id}")
    public String updateEngine(@PathVariable Long id, @ModelAttribute("engineForm") EngineForm engineForm) {
        final var engine = createEngineFromEngineForm(engineForm);
        engine.setId(id);
        engineService.updateEngine(id, engine);
        return "redirect:/engines";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEngine(@PathVariable Long id) {
        engineService.deleteEngine(id);
        return "redirect:/engines";
    }

    private Engine createEngineFromEngineForm(EngineForm engineForm) {
        final var engine = new Engine();
        engine.setName(engineForm.getName());
        engine.setHorsePower(engineForm.getHorsePower());
        return engine;
    }

}

package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

/**
 * @author: Egor Bekhterev
 * @date: 25.03.2023
 * @project: job4j_accidents
 */

@Controller
@ThreadSafe
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model) {
        var optionalAccident = accidentService.save(accident);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("message", "An accident with this ID already exists.");
            return "errors/404";
        }
        return "redirect:/index";
    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable int id) {
        var optionalAccident = accidentService.findById(id);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("message", "No accident with the given ID is found.");
            return "errors/404";
        }
        model.addAttribute("accident", optionalAccident.get());
        return "accidents/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, Model model) {
        var isUpdated = accidentService.update(accident);
        if (!isUpdated) {
            model.addAttribute(
                    "message", "Error while updating information about the accident.");
            return "errors/404";
        }
        return "redirect:/index";
    }
}

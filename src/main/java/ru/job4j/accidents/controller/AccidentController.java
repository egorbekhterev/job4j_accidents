package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model, @RequestParam("rIds") List<Integer> ids) {
        var optionalType = accidentService.findTypeById(accident.getType().getId());
        if (optionalType.isEmpty()) {
            model.addAttribute("message", "No accident type with the given ID is found.");
            return "errors/404";
        }
        accident.setType(optionalType.get());

        accident.setRules(ids.stream().map(accidentService::findRuleById).filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toSet()));

        var optionalAccident = accidentService.save(accident);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("message", "An accident with this ID already exists.");
            return "errors/404";
        }
        return "redirect:/index";
    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable int id) {
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        var optionalAccident = accidentService.findById(id);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("message", "No accident with the given ID is found.");
            return "errors/404";
        }
        model.addAttribute("accident", optionalAccident.get());
        return "accidents/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, Model model, @RequestParam("rIds") List<Integer> ids) {
        var optionalType = accidentService.findTypeById(accident.getType().getId());
        if (optionalType.isEmpty()) {
            model.addAttribute("message", "No accident type with the given ID is found.");
            return "errors/404";
        }
        accident.setType(optionalType.get());

        accident.setRules(ids.stream().map(accidentService::findRuleById).filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toSet()));

        var isUpdated = accidentService.update(accident);
        if (!isUpdated) {
            model.addAttribute(
                    "message", "Error while updating information about the accident.");
            return "errors/404";
        }
        return "redirect:/index";
    }
}

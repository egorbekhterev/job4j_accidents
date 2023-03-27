package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.jdbc.AccidentJdbcService;

/**
 * @author: Egor Bekhterev
 * @date: 24.03.2023
 * @project: job4j_accidents
 */
@Controller
@ThreadSafe
@AllArgsConstructor
public class IndexController {

    private final AccidentJdbcService accidentJdbcService;

    @GetMapping({"/", "index"})
    public String index(Model model) {
        model.addAttribute("user", "Egor Bekhterev");
        model.addAttribute("accidents", accidentJdbcService.findAll());
        return "index";
    }
}

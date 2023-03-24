package ru.job4j.accidents.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Egor Bekhterev
 * @date: 24.03.2023
 * @project: job4j_accidents
 */
@Controller
@ThreadSafe
public class IndexController {

    @GetMapping({"/", "index"})
    public String index(Model model) {
        model.addAttribute("user", "Egor Bekhterev");
        return "index";
    }
}

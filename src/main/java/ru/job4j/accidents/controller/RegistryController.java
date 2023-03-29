package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

/**
 * @author: Egor Bekhterev
 * @date: 30.03.2023
 * @project: job4j_accidents
 */
@ThreadSafe
@Controller
@AllArgsConstructor
public class RegistryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryController.class.getName());

    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        var rsl = "redirect:/reg?error=true";
        try {
            users.save(user);
            rsl = "redirect:/login";
        } catch (Exception e) {
            LOGGER.error("Error in the Save(User user) method", e);
        }
        return rsl;
    }

    @GetMapping("/reg")
    public String regPage(@RequestParam(value = "error", required = false) String error, Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "This username already exists.";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "users/reg";
    }
}

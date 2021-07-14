package com.product_categories.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Hello MVC controller.
 */
@Controller
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "name") String name,
                        @RequestParam(value = "surname", required = false, defaultValue = "surname") String surname,
                        Model model) {
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        return "hello";
    }
}
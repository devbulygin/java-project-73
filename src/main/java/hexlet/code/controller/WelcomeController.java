package hexlet.code.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class WelcomeController {

    @GetMapping(path = "/")
    public String root() {
        return "Welcome to Spring";
    }
}

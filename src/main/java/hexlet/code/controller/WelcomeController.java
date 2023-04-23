package hexlet.code.controller;

import com.rollbar.notifier.Rollbar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public final class WelcomeController {

    @Autowired
    private Rollbar rollbar;


    @GetMapping(path = "/")


    public RedirectView root() {
        rollbar.debug("Here is some debug message");
        return new RedirectView("/api/users");
    }
}

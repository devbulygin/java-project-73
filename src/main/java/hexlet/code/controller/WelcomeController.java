package hexlet.code.controller;


import com.rollbar.notifier.Rollbar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class WelcomeController {

    @Autowired
    private Rollbar rollbar;


    @GetMapping(path = "/")
        public String message() {

        return "welcome to Spring";
    }


    @GetMapping(path = "/rollbar")
    public String rollbarSend() {
        rollbar.debug("Here is some debug message");
        return "Rollbar message send";
    }
}

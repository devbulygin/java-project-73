package hexlet.code.config.rollbar;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.spring.webmvc.RollbarSpringConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration()
@EnableWebMvc
@ComponentScan({

// UPDATE TO YOUR PROJECT PACKAGE
    "hexlet.code"

})
public class RollbarConfig {

    /**
     * Register a Rollbar bean to configure App with Rollbar.
     */
    @Value("${rollbar_token:}")
    private String rollbarToken;

    @Value("${spring.profiles.active:}")
    private String activeProfile;
    @Bean
    public Rollbar rollbar() {

        // Your ACCESS TOKEN is: 8f419a66ead6484d8e52e715a57a5511
        // Make sure to keep this secure
        return new Rollbar(getRollbarConfigs(rollbarToken));
    }

    private Config getRollbarConfigs(String accessToken) {

        // Reference ConfigBuilder.java for all the properties you can set for Rollbar
        return RollbarSpringConfigBuilder.withAccessToken(accessToken)
                .environment("development")
                .enabled(activeProfile == "prod")
                .build();
    }
}

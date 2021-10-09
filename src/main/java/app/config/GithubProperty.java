package app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "user.github")
@Component
public class GithubProperty {

    private String clientId;

    private String clientSecret;

    private String tokenUrl;

    private String redirectUrl;

    private String userInfoUrl;

    private String baseUrl;

}

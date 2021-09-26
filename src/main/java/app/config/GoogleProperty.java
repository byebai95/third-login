package app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "user.google")
@Component
@Data
public class GoogleProperty {

    private String clientId;

    private String clientSecret;

    private String grantType;

    private String redirectUrl;

    private String tokenUrl;

    private String userInfoUrl;
}

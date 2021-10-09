package app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "user.facebook")
@Component
public class FacebookProperty {

    private String clientId;

    private String clientSecret;

    private String tokenUrl;

    private String redirectUrl;

    //检查的口令
    private String state;

    private String userInfoUrl;

    private String baseUrl;

}


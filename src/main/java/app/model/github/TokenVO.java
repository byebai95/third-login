package app.model.github;

import lombok.Data;

@Data
public class TokenVO {

    private String accessToken;

    private String scope;
}

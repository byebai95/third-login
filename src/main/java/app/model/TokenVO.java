package app.model;

import lombok.Data;

@Data
public class TokenVO {

    private String accessToken;

    private String scope;
}

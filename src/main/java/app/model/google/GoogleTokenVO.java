package app.model.google;

import lombok.Data;

/**
 * https://developers.google.com/identity/protocols/oauth2/native-app?hl=RU#step1-code-verifier
 */
@Data
public class GoogleTokenVO {

    //授权的请求令牌
    private String accessToken;

    //访问令牌剩余生命周期
    private Integer expiresIn;

    //授权范围
    private String scope;

    //返回的令牌类型
    private String tokenType;

    //
    private String idToken;
}

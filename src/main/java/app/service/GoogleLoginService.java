package app.service;

import app.config.GoogleProperty;
import app.model.google.GoogleTokenVO;
import app.model.google.GoogleUserInfoVO;
import app.util.HttpRequestUtils;
import app.util.HttpRequestVO;
import app.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@Component
public class GoogleLoginService {

    private final GoogleProperty googleProperty;

    public String thirdLoginByGoogle(HttpServletRequest request) {
        String code = request.getParameter("code");
        log.info("code = {}", code);
        String accessToken = getGoogleToken(code);
        log.info("获取到 token:{}", accessToken);
        String username = getUserName(accessToken);
        return username;
    }

    public String getGoogleUserName() {
        return "";
    }

    public String getGoogleToken(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", googleProperty.getClientId());
        params.put("client_secret", googleProperty.getClientSecret());
        params.put("code", code);
        params.put("grant_type", googleProperty.getGrantType());
        params.put("redirect_uri", googleProperty.getRedirectUrl());

        Map<String, Object> header = new HashMap<>();
        header.put("Accept", "application/json");

        HttpRequestVO requestToken = new HttpRequestVO();
        requestToken.createBodyRequestByMap(googleProperty.getTokenUrl(), params, header);
        String response = HttpRequestUtils.postRequest(requestToken);
        log.info("google 根据 code 获取到 access_token:{}", response);
        GoogleTokenVO tokenVO = JsonUtils.json2obj(response, GoogleTokenVO.class);
        return tokenVO.getAccessToken();
    }

    public String getUserName(String accessToken){
        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", "Bearer "+accessToken);

        HttpRequestVO requestUserInfo = new HttpRequestVO();
        requestUserInfo.createQueryRequestByMap(googleProperty.getUserInfoUrl(),null,header);
        String response = HttpRequestUtils.getRequest(requestUserInfo);
        log.info("获取到用户信息：{}",response);
        GoogleUserInfoVO userInfoVO = JsonUtils.json2obj(response, GoogleUserInfoVO.class);
        return userInfoVO.getName();
    }
}
